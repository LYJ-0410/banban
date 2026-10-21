package com.zhiban.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiban.common.BusinessException;
import com.zhiban.common.PageResult;
import com.zhiban.dto.PostRequest;
import com.zhiban.dto.PostVO;
import com.zhiban.entity.Post;
import com.zhiban.entity.PostLike;
import com.zhiban.mapper.PostLikeMapper;
import com.zhiban.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostMapper postMapper;
    private final PostLikeMapper postLikeMapper;
    
    /**
     * 发布动态
     */
    public Long createPost(Long userId, PostRequest request) {
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new BusinessException("内容不能为空");
        }
        
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(request.getContent());
        post.setImages(request.getImages() != null ? JSONUtil.toJsonStr(request.getImages()) : "[]");
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setStatus(1);
        postMapper.insert(post);
        
        return post.getId();
    }
    
    /**
     * 动态广场（分页）
     */
    public PageResult<PostVO> getPostList(Long userId, int page, int size) {
        long offset = (long)(page - 1) * size;
        List<PostVO> records = postMapper.selectPostList(userId, offset, size);
        
        // 查询总数
        long total = postMapper.selectCount(
            new LambdaQueryWrapper<Post>().eq(Post::getStatus, 1)
        );
        
        return PageResult.of(records, total, page, size);
    }
    
    /**
     * 动态详情
     */
    public PostVO getPostDetail(Long postId, Long userId) {
        PostVO post = postMapper.selectPostDetail(postId, userId);
        if (post == null) {
            throw new BusinessException("动态不存在");
        }
        return post;
    }
    
    /**
     * 点赞/取消点赞
     */
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        // 检查动态是否存在
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException("动态不存在");
        }
        
        // 检查是否已点赞
        PostLike existLike = postLikeMapper.selectOne(
            new LambdaQueryWrapper<PostLike>()
                .eq(PostLike::getPostId, postId)
                .eq(PostLike::getUserId, userId)
        );
        
        if (existLike != null) {
            // 取消点赞
            postLikeMapper.deleteById(existLike.getId());
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            // 点赞
            PostLike like = new PostLike();
            like.setPostId(postId);
            like.setUserId(userId);
            postLikeMapper.insert(like);
            post.setLikeCount(post.getLikeCount() + 1);
        }
        
        postMapper.updateById(post);
    }
    
    /**
     * 删除动态
     */
    public void deletePost(Long postId, Long userId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException("动态不存在");
        }
        if (!post.getUserId().equals(userId)) {
            throw new BusinessException("无权删除他人动态");
        }
        postMapper.deleteById(postId);
    }
}
