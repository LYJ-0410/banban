package com.zhiban.controller;

import com.zhiban.common.Result;
import com.zhiban.dto.LoginRequest;
import com.zhiban.dto.LoginResponse;
import com.zhiban.dto.RegisterRequest;
import com.zhiban.entity.User;
import com.zhiban.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<Long> register(@Valid @RequestBody RegisterRequest request) {
        Long userId = userService.register(request);
        return Result.success(userId);
    }
    
    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return Result.success(response);
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    public Result<User> getCurrentUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserById(userId);
        user.setPassword(null); // 隐藏密码
        return Result.success(user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/me")
    public Result<?> updateCurrentUser(Authentication authentication, @RequestBody User user) {
        Long userId = (Long) authentication.getPrincipal();
        user.setId(userId);
        user.setUsername(null); // 不允许修改用户名
        user.setPassword(null); // 不允许通过此接口修改密码
        userService.updateUser(user);
        return Result.success();
    }
}
