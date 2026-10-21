package com.zhiban.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiban.common.BusinessException;
import com.zhiban.entity.Follow;
import com.zhiban.entity.User;
import com.zhiban.mapper.FollowMapper;
import com.zhiban.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    
    private final FollowMapper followMapper;
    private final UserMapper userMapper;
    
    /**
     * 关注/取消关注
     */
    public void toggleFollow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new BusinessException("不能关注自己");
        }
        
        // 检查对方是否存在
        User targetUser = userMapper.selectById(followingId);
        if (targetUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 检查是否已关注
        Follow exist = followMapper.selectOne(
            new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, followerId)
                .eq(Follow::getFollowingId, followingId)
        );
        
        if (exist != null) {
            // 取消关注
            followMapper.deleteById(exist.getId());
        } else {
            // 关注
            Follow follow = new Follow();
            follow.setFollowerId(followerId);
            follow.setFollowingId(followingId);
            followMapper.insert(follow);
        }
    }
    
    /**
     * 查询我的关注列表
     */
    public List<User> getFollowingList(Long userId) {
        List<Follow> follows = followMapper.selectList(
            new LambdaQueryWrapper<Follow>().eq(Follow::getFollowerId, userId)
        );
        
        List<Long> followingIds = follows.stream()
            .map(Follow::getFollowingId)
            .collect(Collectors.toList());
        
        if (followingIds.isEmpty()) {
            return List.of();
        }
        
        return userMapper.selectBatchIds(followingIds);
    }
    
    /**
     * 查询我的粉丝列表
     */
    public List<User> getFollowerList(Long userId) {
        List<Follow> follows = followMapper.selectList(
            new LambdaQueryWrapper<Follow>().eq(Follow::getFollowingId, userId)
        );
        
        List<Long> followerIds = follows.stream()
            .map(Follow::getFollowerId)
            .collect(Collectors.toList());
        
        if (followerIds.isEmpty()) {
            return List.of();
        }
        
        return userMapper.selectBatchIds(followerIds);
    }
    
    /**
     * 是否已关注
     */
    public boolean isFollowing(Long followerId, Long followingId) {
        return followMapper.selectCount(
            new LambdaQueryWrapper<Follow>()
                .eq(Follow::getFollowerId, followerId)
                .eq(Follow::getFollowingId, followingId)
        ) > 0;
    }
    
    /**
     * 关注数
     */
    public long getFollowingCount(Long userId) {
        return followMapper.selectCount(
            new LambdaQueryWrapper<Follow>().eq(Follow::getFollowerId, userId)
        );
    }
    
    /**
     * 粉丝数
     */
    public long getFollowerCount(Long userId) {
        return followMapper.selectCount(
            new LambdaQueryWrapper<Follow>().eq(Follow::getFollowingId, userId)
        );
    }
}
