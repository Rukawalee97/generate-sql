package com.rukawa.generate.sql.service;

import com.github.pagehelper.PageHelper;
import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.domain.Page;
import com.rukawa.generate.sql.domain.Skill;
import com.rukawa.generate.sql.mapper.SkillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Skill querySkillByPrimaryKey(Integer skillId) {
        return skillMapper.selectSkillByPrimary(skillId);
    }

    public List<Skill> querySkills(Skill skill) {
        return skillMapper.selectSkills(skill);
    }

    public List<Skill> querySkillTopics(Skill skill, Page page) {
        if(BeanUtil.isEmpty(page)) {
            return new ArrayList<>();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return skillMapper.selectSkillTopics(skill);
    }

    public int deleteSkill(Integer skillId) {
        return skillMapper.deleteSkillByPrimary(skillId);
    }

}
