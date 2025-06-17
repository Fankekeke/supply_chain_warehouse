package com.fank.f1k2.common.config;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Slf4j
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(applicationName)
                .select()
                // 添加@Api注解才显示
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.fank.f1k2"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * swagger-api接口描述信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("悲伤的橘子树 API接口文档")
                .description("悲伤的橘子树 API接口文档")
                .contact(
                        new Contact(
                                "悲伤的橘子树",
                                "https://berserker287.github.io/",
                                "fan1ke2ke@gmail.com"
                        )
                )
                .version("1.0.0")
                .build();
    }
}