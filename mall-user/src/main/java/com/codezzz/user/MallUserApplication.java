package com.codezzz.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.codezzz")
@MapperScan("com.codezzz.core.mapper")
public class MallUserApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MallUserApplication.class);
        SpringApplication.run(MallUserApplication.class, args);
    }

}
