package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
@TableName("user")
public class User {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 用户名 */
    private String username;
    
    /** 密码（加密存储） */
    private String password;
    
    /** 昵称 */
    private String nickname;
    
    /** 头像 */
    private String avatar;
    
    /** 性别：0-未知，1-男，2-女 */
    private Integer gender;
    
    /** 个人简介 */
    private String bio;
    
    /** 手机号 */
    private String phone;
    
    /** 邮箱 */
    private String email;
    
    /** 状态：0-禁用，1-正常 */
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}
