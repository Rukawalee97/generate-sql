package com.rukawa.generate.sql.dto;

import com.rukawa.sql.param.SQLParam;
import lombok.Data;

import java.util.Map;

@Data
public class GenerateDto {

    private String tableName;

    private Map<String, Object> entity;

    private SQLParam param;

}
