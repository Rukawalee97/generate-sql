package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.configuration.SQLConfiguration;
import com.rukawa.generate.sql.dto.GenerateDto;
import com.rukawa.sql.configuration.SQLBuilderConfiguration;
import com.rukawa.sql.factory.SQLBuilderFactory;
import com.rukawa.sql.interfaces.ISQLBuilder;
import com.rukawa.sql.param.SQLParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class GenerateService {

    @Autowired
    private SQLConfiguration sqlConfiguration;

    public Map<String, String> generateSql(GenerateDto generateDto) {
        Map<String, String> resultMap = new HashMap<>();
        SQLParam sqlParam = generateDto.getParam();
        if (BeanUtil.isEmpty(generateDto)) {
            return resultMap;
        }
        if (Objects.isNull(sqlParam) && !Objects.isNull(generateDto.getEntity())) {
            Map<String, Object> entity = generateDto.getEntity();
            sqlParam = new SQLParam();
            sqlParam.setExecutionFields(entity.keySet());
            sqlParam.setConditionFields(entity.keySet());
        }
        Map<String, Class> executorMap = new HashMap<>();
        executorMap.put("jdbc", Driver.class);
        executorMap.put("jdbcTemplate", JdbcTemplate.class);
        SQLBuilderConfiguration properties = sqlConfiguration.getProperties();
        String tableName = generateDto.getTableName();
        SQLParam finalSqlParam = sqlParam;
        executorMap.entrySet().forEach(entry -> {
            SQLBuilderConfiguration sqlBuilderConfiguration = BeanUtil.updateObject(new SQLBuilderConfiguration(), properties);
            sqlBuilderConfiguration.setSqlBuilderExecutorClass(entry.getValue());
            ISQLBuilder sqlBuilder = SQLBuilderFactory.createSQLBuilder(sqlBuilderConfiguration, tableName);
            StringBuilder builder = new StringBuilder("<pre class='ql-syntax' spellcheck='false'>");
            builder.append("# Select SQL \r\n")
                    .append(sqlBuilder.buildSelectSQLWithParam(finalSqlParam))
                    .append("\r\n")
                    .append("\r\n")
                    .append("# Insert SQL \r\n")
                    .append(sqlBuilder.buildInsertSQL(finalSqlParam.getExecutionFields()))
                    .append("\r\n")
                    .append("\r\n")
                    .append("# Update SQL \r\n")
                    .append(sqlBuilder.buildUpdateSQLWithParam(finalSqlParam))
                    .append("\r\n")
                    .append("\r\n")
                    .append("# Delete SQL \r\n")
                    .append(sqlBuilder.buildDeleteSQLWithParam(finalSqlParam))
                    .append("\r\n</pre>");
            resultMap.put(entry.getKey(), builder.toString());
        });
        return resultMap;
    }

}
