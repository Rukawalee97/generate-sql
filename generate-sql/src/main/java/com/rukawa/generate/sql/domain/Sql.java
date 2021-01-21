package com.rukawa.generate.sql.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sql {

    private Long sqlId;

    private String sqlContent;

    private LocalDateTime createTime;
}
