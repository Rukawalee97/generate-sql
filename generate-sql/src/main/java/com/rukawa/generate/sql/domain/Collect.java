package com.rukawa.generate.sql.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Collect {

    private Integer collectId;

    private Integer userId;

    private Integer skillId;

    private LocalDateTime collectTime;

}
