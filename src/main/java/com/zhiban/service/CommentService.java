package com.zhiban.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiban.common.BusinessException;
import com.zhiban.entity.Comment;
import com.zhiban.entity.Post;
import com.zhiban.mapper.CommentMapper;
import com.zhiban.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    
    /**
     * 发表评论
     */
    @Transactional
    public Long addComment(Long postId, Long userId, String content, Long replyUserId) {
        // 检查动态
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException("动态不存在");
        }
        
        // 保存评论
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setReplyUserId(replyUserId);
        comment.setLikeCount(0);
        commentMapper.insert(comment);
        
        // 更新动态评论数
        post.setCommentCount(post.getCommentCount() + 1);
        postMapper.updateById(post);
        
        return comment.getId();
    }
    
    /**
     * 查询动态的评论列表
     */
    public Page<Comment> getCommentsByPostId(Long postId, int page, int size) {
        Page<Comment> pageParam = new Page<>(page, size);
        return commentMapper.selectPage(pageParam,
            new LambdaQueryWrapper<Comment>()
                .eq(Comment::getPostId, postId)
                .orderByDesc(Comment::getCreateTime)
        );
    }
    
    /**
     * 删除评论
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new BusinessException("无权删除他人评论");
        }
        
        commentMapper.deleteById(commentId);
        
        // 更新动态评论数
        Post post = postMapper.selectById(comment.getPostId());
        if (post != null) {
            post.setCommentCount(Math.max(0, post.getCommentCount() - 1));
            postMapper.updateById(post);
        }
    }
}
