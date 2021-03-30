package com.rukawa.generate.sql.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Integer commentId;

    private Integer skillId;

    private Integer userId;

    private String commentContent;

    private LocalDateTime commentTime;

}
