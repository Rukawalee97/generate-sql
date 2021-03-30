package com.rukawa.generate.sql.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String userHead;

    private String username;

    private String commentContent;

    private LocalDateTime commentTime;

}
