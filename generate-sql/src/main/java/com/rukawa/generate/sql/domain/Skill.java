package com.rukawa.generate.sql.domain;

import lombok.Data;

@Data
public class Skill {

    private Integer skillId;

    private String skillTopic;

    private String skillContent;

    private String simpleSkillContent;

    private String skillType;

}
