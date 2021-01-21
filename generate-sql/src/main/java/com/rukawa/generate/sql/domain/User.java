package com.rukawa.generate.sql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;

    private String username;

    private String userHead;

    private String userIntroduction;

    private String password;

    private Integer roleId;

    private LocalDateTime createTime;
}
