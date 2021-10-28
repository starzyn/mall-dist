package com.codezzz.core.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : zhan9yn
 * @version : 1.0
 * @description : Flyway 配置类
 * @date : 2021/10/28 2:32 下午
 */
@Configuration
public class FlyWayConfig {

    private final static String PREFIX = "/sql/";

    @Bean
    public Flyway flyway () {
        Flyway flyway = Flyway.configure().locations(PREFIX ).load();
        return flyway;
    }
}
