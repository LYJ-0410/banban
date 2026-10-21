package com.zhiban.dto;

import lombok.Data;
import java.util.List;

/**
 * 发布动态请求
 */
@Data
public class PostRequest {
    
    private String content;
    
    private List<String> images;
}
