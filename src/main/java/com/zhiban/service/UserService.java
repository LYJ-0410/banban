package com.zhiban.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiban.common.BusinessException;
import com.zhiban.dto.LoginRequest;
import com.zhiban.dto.LoginResponse;
import com.zhiban.dto.RegisterRequest;
import com.zhiban.entity.User;
import com.zhiban.mapper.UserMapper;
import com.zhiban.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    /**
     * 用户注册
     */
    public Long register(RegisterRequest request) {
        // 检查用户名是否已存在
        User existUser = userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setAvatar("/uploads/avatar/default.png");
        user.setStatus(1);
        userMapper.insert(user);
        
        return user.getId();
    }
    
    /**
     * 用户登录
     */
    public LoginResponse login(LoginRequest request) {
        // 查找用户
        User user = userMapper.selectOne(
            new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 检查状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        
        // 生成 Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        // 返回结果
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        return response;
    }
    
    /**
     * 获取用户信息
     */
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    /**
     * 更新用户信息
     */
    public void updateUser(User user) {
        userMapper.updateById(user);
    }
}
