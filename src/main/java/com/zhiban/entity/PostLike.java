package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 点赞表
 */
@Data
@TableName("post_like")
public class PostLike {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 动态ID */
    private Long postId;
    
    /** 用户ID */
    private Long userId;
    
    private LocalDateTime createTime;
}
