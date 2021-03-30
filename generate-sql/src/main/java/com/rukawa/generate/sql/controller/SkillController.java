package com.rukawa.generate.sql.controller;

import com.rukawa.generate.sql.domain.Skill;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("skill")
    public Result uploadSkill(@RequestBody Skill skill) {
        Result result;
        if (skillService.UpdateSkill(skill) > 0) {
            result = Result.success(true);
            result.setMessage("新增知识点成功！");
        } else {
            result = Result.fail(false);
            result.setMessage("新增知识点失败！");
        }
        return result;
    }

    @PutMapping("/skill")
    public Result updateSkill(@RequestBody Skill skill) {
        Result result;
        if (skillService.UpdateSkill(skill) > 0) {
            result = Result.success(true);
            result.setMessage("更新知识点成功！");
        } else {
            result = Result.fail(false);
            result.setMessage("更新知识点失败！");
        }
        return result;
    }

    @DeleteMapping("/skill/{skillId}")
    public Result deleteSkill(@PathVariable Integer skillId) {
        int resultCount = skillService.deleteSkill(skillId);
        if (resultCount > 0) {
            return Result.success(true);
        }
        Result result = Result.fail(false);
        result.setMessage("删除知识点发生异常！");
        return result;
    }

    @GetMapping("/skill/{skillId}")
    public Result querySkill(@PathVariable Integer skillId) {
        return Result.success(skillService.querySkillByPrimaryKey(skillId));
    }

    @GetMapping("/skills/{skillType}")
    public Result querySkills(@PathVariable String skillType) {
        Skill skill = new Skill();
        skill.setSkillType(skillType);
        return Result.success(skillService.querySkills(skill));
    }

    @GetMapping("/skill/topic/{skillType}")
    public Result querySkillTopics(@PathVariable String skillType) {
        Skill skill = new Skill();
        skill.setSkillType(skillType);
        return Result.success(skillService.querySkillTopics(skill));
    }

}
