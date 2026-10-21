package com.zhiban.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiban.entity.Post;
import com.zhiban.dto.PostVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PostMapper extends BaseMapper<Post> {
    
    /**
     * 查询动态列表（包含发布者信息）
     */
    List<PostVO> selectPostList(@Param("userId") Long userId, 
                                 @Param("offset") long offset, 
                                 @Param("size") long size);
    
    /**
     * 查询单条动态详情
     */
    PostVO selectPostDetail(@Param("postId") Long postId, @Param("userId") Long userId);
}
