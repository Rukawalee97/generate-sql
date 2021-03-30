package com.rukawa.generate.sql.controller;

import com.rukawa.generate.sql.domain.Comment;
import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.CommentService;
import com.rukawa.generate.sql.service.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TokenCacheService tokenCacheService;

    @PostMapping("/comments")
    public Result getComments(@RequestBody Comment comment) {
        return Result.success(commentService.queryComments(comment));
    }

    @PostMapping("/comment")
    public Result uploadComment(@RequestBody Comment comment, @RequestHeader("Access-Token") String accessToken) {
        Result result;
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            comment.setUserId(user.getUserId());
            comment.setCommentTime(LocalDateTime.now());
            int affectRows = commentService.uploadComment(comment);
            if (affectRows > 0) {
                result = Result.success(true);
            } else {
                result = Result.fail(false);
                result.setMessage("评论失败！");
            }
        } else {
            result = Result.fail(false);
            result.setMessage("您还没有登录！");
        }
        return result;
    }

}
