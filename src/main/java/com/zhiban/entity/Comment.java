package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论表
 */
@Data
@TableName("comment")
public class Comment {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 动态ID */
    private Long postId;
    
    /** 评论者ID */
    private Long userId;
    
    /** 被回复用户ID（可选） */
    private Long replyUserId;
    
    /** 评论内容 */
    private String content;
    
    /** 点赞数 */
    private Integer likeCount;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableLogic
    private Integer deleted;
}
