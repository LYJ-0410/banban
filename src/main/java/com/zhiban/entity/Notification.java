package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 通知消息表
 */
@Data
@TableName("notification")
public class Notification {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 接收用户ID */
    private Long userId;
    
    /** 发送用户ID */
    private Long fromUserId;
    
    /** 类型：like-点赞，comment-评论，follow-关注 */
    private String type;
    
    /** 关联ID（动态ID或用户ID） */
    private Long targetId;
    
    /** 消息内容 */
    private String content;
    
    /** 是否已读：0-未读，1-已读 */
    private Integer readStatus;
    
    private LocalDateTime createTime;
}
