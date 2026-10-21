package com.zhiban.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 登录响应
 */
@Data
public class LoginResponse {
    
    private String token;
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
}
