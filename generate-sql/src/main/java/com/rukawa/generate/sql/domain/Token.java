package com.rukawa.generate.sql.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Token {

    private LocalDateTime tokenTime;

    private Integer expireTime;

    private User user;

}
