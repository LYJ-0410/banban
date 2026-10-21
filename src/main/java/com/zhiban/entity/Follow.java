package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 关注表
 */
@Data
@TableName("follow")
public class Follow {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 粉丝用户ID */
    private Long followerId;
    
    /** 被关注用户ID */
    private Long followingId;
    
    private LocalDateTime createTime;
}
