package com.zhiban.controller;

import com.zhiban.common.Result;
import com.zhiban.entity.User;
import com.zhiban.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    
    private final FollowService followService;
    
    /**
     * 关注/取消关注
     */
    @PostMapping("/{userId}")
    public Result<?> toggleFollow(Authentication authentication, 
                                   @PathVariable Long userId) {
        Long followerId = (Long) authentication.getPrincipal();
        followService.toggleFollow(followerId, userId);
        return Result.success();
    }
    
    /**
     * 我的关注列表
     */
    @GetMapping("/following")
    public Result<List<User>> getFollowingList(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(followService.getFollowingList(userId));
    }
    
    /**
     * 我的粉丝列表
     */
    @GetMapping("/followers")
    public Result<List<User>> getFollowerList(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(followService.getFollowerList(userId));
    }
    
    /**
     * 查询关注关系统计
     */
    @GetMapping("/stats/{userId}")
    public Result<Map<String, Object>> getFollowStats(@PathVariable Long userId,
                                                       Authentication authentication) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("followingCount", followService.getFollowingCount(userId));
        stats.put("followerCount", followService.getFollowerCount(userId));
        
        if (authentication != null) {
            Long currentUserId = (Long) authentication.getPrincipal();
            stats.put("isFollowing", followService.isFollowing(currentUserId, userId));
        }
        
        return Result.success(stats);
    }
}
