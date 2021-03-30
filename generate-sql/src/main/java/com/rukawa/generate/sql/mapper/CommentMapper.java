package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.Comment;
import com.rukawa.generate.sql.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentDto> selectComments(Comment comment);

    int insertComment(Comment comment);

}
