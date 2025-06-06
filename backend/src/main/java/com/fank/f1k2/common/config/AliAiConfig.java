package com.fank.f1k2.common.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliAiConfig {

    @Bean
    public Generation generation() {
        return new Generation();
    }
}
