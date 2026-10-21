package com.zhiban.controller;

import com.zhiban.common.PageResult;
import com.zhiban.common.Result;
import com.zhiban.dto.PostRequest;
import com.zhiban.dto.PostVO;
import com.zhiban.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    
    /**
     * 发布动态
     */
    @PostMapping
    public Result<Long> createPost(Authentication authentication, 
                                    @RequestBody PostRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        Long postId = postService.createPost(userId, request);
        return Result.success(postId);
    }
    
    /**
     * 动态广场（分页）
     */
    @GetMapping("/list")
    public Result<PageResult<PostVO>> getPostList(
            Authentication authentication,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Long userId = authentication != null ? (Long) authentication.getPrincipal() : null;
        PageResult<PostVO> result = postService.getPostList(userId, page, size);
        return Result.success(result);
    }
    
    /**
     * 动态详情
     */
    @GetMapping("/{postId}")
    public Result<PostVO> getPostDetail(Authentication authentication, 
                                         @PathVariable Long postId) {
        Long userId = authentication != null ? (Long) authentication.getPrincipal() : null;
        PostVO post = postService.getPostDetail(postId, userId);
        return Result.success(post);
    }
    
    /**
     * 点赞/取消点赞
     */
    @PostMapping("/{postId}/like")
    public Result<?> toggleLike(Authentication authentication, 
                                @PathVariable Long postId) {
        Long userId = (Long) authentication.getPrincipal();
        postService.toggleLike(postId, userId);
        return Result.success();
    }
    
    /**
     * 删除动态
     */
    @DeleteMapping("/{postId}")
    public Result<?> deletePost(Authentication authentication, 
                                @PathVariable Long postId) {
        Long userId = (Long) authentication.getPrincipal();
        postService.deletePost(postId, userId);
        return Result.success();
    }
}
