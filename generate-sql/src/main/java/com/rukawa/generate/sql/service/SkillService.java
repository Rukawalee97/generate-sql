package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.domain.Skill;
import com.rukawa.generate.sql.mapper.SkillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;

    public int UpdateSkill(Skill skill) {
        if (BeanUtil.isEmpty(skill)) {
            return 0;
        }
        if (!BeanUtil.isEmpty(skill.getSkillId())) {
            return skillMapper.updateSkillByPrimaryKey(skill);
        }
        return skillMapper.insertSkill(skill);
    }

    public List<Skill> querySkills(Skill skill) {
        return skillMapper.selectSkills(skill);
    }

    public int deleteSkill(Integer skillId) {
        return skillMapper.deleteSkillByPrimary(skillId);
    }

}
