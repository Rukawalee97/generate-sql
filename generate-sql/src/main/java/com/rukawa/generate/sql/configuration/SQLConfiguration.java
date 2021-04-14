package com.rukawa.generate.sql.configuration;

import com.rukawa.sql.configuration.SQLBuilderConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sql")
public class SQLConfiguration {

    private SQLBuilderConfiguration properties;

}
