package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.Skill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkillMapper {

    List<Skill> selectSkills(Skill skill);

    int updateSkillByPrimaryKey(Skill skill);

    Skill selectSkillByPrimary(Integer skillId);

    int insertSkill(Skill skill);

    int deleteSkillByPrimary(Integer skillId);

}
