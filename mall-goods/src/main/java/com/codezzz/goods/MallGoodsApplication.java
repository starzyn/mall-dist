package com.codezzz.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.codezzz")
@MapperScan("com.codezzz.core.mapper")
public class MallGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }

}
