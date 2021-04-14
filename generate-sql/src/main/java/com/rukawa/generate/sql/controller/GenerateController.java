package com.rukawa.generate.sql.controller;

import com.alibaba.fastjson.JSONObject;
import com.rukawa.generate.sql.dto.GenerateDto;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.GenerateService;
import com.rukawa.sql.interfaces.impl.MySQLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/generate")
public class GenerateController {

    @Autowired
    private GenerateService generateService;

    @PostMapping
    public Result generateSql(@RequestBody String json) {
        Map<String, String> jsonMap = JSONObject.parseObject(json, Map.class);
        Map<String, String> resultMap;
        try {
            GenerateDto generateDto = JSONObject.parseObject(jsonMap.get("json"), GenerateDto.class);
            resultMap = generateService.generateSql(generateDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.success(resultMap);
    }

}
