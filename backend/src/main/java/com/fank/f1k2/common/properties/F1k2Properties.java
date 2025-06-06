package com.fank.f1k2.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "febs")
public class F1k2Properties {

    private ShiroProperties shiro = new ShiroProperties();

    private boolean openAopLog = true;

}
