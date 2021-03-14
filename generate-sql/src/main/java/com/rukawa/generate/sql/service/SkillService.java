package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.common.util.RegexUtil;
import com.rukawa.generate.sql.domain.Skill;
import com.rukawa.generate.sql.mapper.SkillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class SkillService {

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private Base64Service base64Service;

    @Autowired
    private FileService fileService;

    public int UpdateSkill(Skill skill) {
        if (BeanUtil.isEmpty(skill)) {
            return 0;
        }
        String skillContent = skill.getSkillContent();
        StringBuffer skillContentBuilder = new StringBuffer(skillContent);
        Map<String, String> base64Map = RegexUtil.parseBase64ImgMap(skillContent);
        for (Map.Entry<String, String> entry : base64Map.entrySet()) {
            InputStream inputStream = base64Service.parseBase64ToStream(entry.getKey());
            String relFile = fileService.transferFile(inputStream, entry.getValue());
            int offset = -1;
            while ((offset = skillContentBuilder.indexOf(entry.getKey())) != -1) {
                skillContentBuilder.replace(offset, offset + entry.getKey().length(), "/images/" + relFile);
            }
        }
        skill.setSkillContent(skillContentBuilder.toString());
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

    public List<Skill> querySkillTopics(Skill skill) {
        return skillMapper.selectSkillTopics(skill);
    }

    public int deleteSkill(Integer skillId) {
        return skillMapper.deleteSkillByPrimary(skillId);
    }

}
