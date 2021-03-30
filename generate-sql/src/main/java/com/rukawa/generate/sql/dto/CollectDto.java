package com.rukawa.generate.sql.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CollectDto {

    private Integer collectId;

    private Integer skillId;

    private String skillTopic;

    private LocalDateTime collectTime;

}
