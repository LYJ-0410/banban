package com.zhiban.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiban.common.Result;
import com.zhiban.dto.CommentRequest;
import com.zhiban.entity.Comment;
import com.zhiban.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;
    
    @PostMapping
    public Result<Long> addComment(Authentication authentication, 
                                    @Valid @RequestBody CommentRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        Long commentId = commentService.addComment(
            request.getPostId(), 
            userId, 
            request.getContent(), 
            request.getReplyUserId(), 
            request.getAnonymous()
        );
        return Result.success(commentId);
    }
    
    @GetMapping("/post/{postId}")
    public Result<Page<Comment>> getComments(@PathVariable Long postId,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        Page<Comment> result = commentService.getCommentsByPostId(postId, page, size);
        return Result.success(result);
    }
    
    @DeleteMapping("/{commentId}")
    public Result<?> deleteComment(Authentication authentication, 
                                    @PathVariable Long commentId) {
        Long userId = (Long) authentication.getPrincipal();
        commentService.deleteComment(commentId, userId);
        return Result.success();
    }
}
