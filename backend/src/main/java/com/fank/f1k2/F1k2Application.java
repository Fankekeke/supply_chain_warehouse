package com.fank.f1k2;

import com.fank.f1k2.common.config.AliAiConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import(AliAiConfig.class)
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class F1k2Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(F1k2Application.class)
                .run(args);
    }
}
