package com.zhiban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhiban.entity.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<Comment> {
    
    Page<Comment> selectCommentPage(@Param("page") Page<Comment> page, @Param("postId") Long postId);
}
