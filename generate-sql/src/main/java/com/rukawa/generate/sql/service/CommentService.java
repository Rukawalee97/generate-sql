package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.domain.Comment;
import com.rukawa.generate.sql.dto.CommentDto;
import com.rukawa.generate.sql.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<CommentDto> queryComments(Comment comment) {
        if (BeanUtil.isEmpty(comment)) {
            return new ArrayList<>();
        }
        return commentMapper.selectComments(comment);
    }

    public int uploadComment(Comment comment) {
        if (BeanUtil.isEmpty(comment)) {
            return 0;
        }
        return commentMapper.insertComment(comment);
    }

}
