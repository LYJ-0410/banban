package com.zhiban.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiban.common.Result;
import com.zhiban.entity.Comment;
import com.zhiban.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;
    
    /**
     * 发表评论
     */
    @PostMapping
    public Result<Long> addComment(Authentication authentication, 
                                    @RequestBody Map<String, Object> params) {
        Long userId = (Long) authentication.getPrincipal();
        Long postId = Long.valueOf(params.get("postId").toString());
        String content = (String) params.get("content");
        Long replyUserId = params.get("replyUserId") != null ? 
            Long.valueOf(params.get("replyUserId").toString()) : null;
        
        Long commentId = commentService.addComment(postId, userId, content, replyUserId);
        return Result.success(commentId);
    }
    
    /**
     * 查询评论列表
     */
    @GetMapping("/post/{postId}")
    public Result<Page<Comment>> getComments(@PathVariable Long postId,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        Page<Comment> result = commentService.getCommentsByPostId(postId, page, size);
        return Result.success(result);
    }
    
    /**
     * 删除评论
     */
    @DeleteMapping("/{commentId}")
    public Result<?> deleteComment(Authentication authentication, 
                                    @PathVariable Long commentId) {
        Long userId = (Long) authentication.getPrincipal();
        commentService.deleteComment(commentId, userId);
        return Result.success();
    }
}
