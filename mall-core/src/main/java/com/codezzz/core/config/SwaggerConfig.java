package com.codezzz.core.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/17 14:48
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.basePackage}")
    private String basePackage = "com.codezzz";

    private ApiInfo initApiInfo() {
        ApiInfo apiInfo = new ApiInfo("分布式商城 API",//大标题
                "分布式商城",//简单的描述
                "0.1",//版本
                "服务条款",
                "zhan9yn",//作者
                "The Apache License, Version 2.0",//链接显示文字
                "http://www.baidu.com"//网站链接
        );

        return apiInfo;
    }


    @Bean
    public Docket restfulApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(initApiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    /**
     * 设置过滤规则
     * 这里的过滤规则支持正则匹配
     * @return
     */
    private Predicate<String> doFilteringRules() {
        return or(
                regex("/hello.*"),
                regex("/vehicles.*")
        );
    }

}
