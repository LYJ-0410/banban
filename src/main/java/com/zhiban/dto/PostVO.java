package com.zhiban.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 动态VO（视图对象，包含发布者信息）
 */
@Data
public class PostVO {
    
    private Long id;
    private String content;
    private String images;
    private Integer likeCount;
    private Integer commentCount;
    private LocalDateTime createTime;
    
    // 发布者信息
    private Long userId;
    private String nickname;
    private String avatar;
    
    // 当前用户是否点赞
    private Boolean liked;
}
