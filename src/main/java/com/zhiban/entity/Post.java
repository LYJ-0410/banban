package com.zhiban.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 动态表
 */
@Data
@TableName("post")
public class Post {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 发布者ID */
    private Long userId;
    
    /** 内容 */
    private String content;
    
    /** 图片（JSON数组） */
    private String images;
    
    /** 点赞数 */
    private Integer likeCount;
    
    /** 评论数 */
    private Integer commentCount;
    
    /** 状态：0-草稿，1-已发布，2-已删除 */
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}
