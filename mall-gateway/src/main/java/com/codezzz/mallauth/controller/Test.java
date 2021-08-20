package com.codezzz.mallauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/17 20:47
 */

@RestController
public class Test {
    @GetMapping("/test")
    public String test() {
        return "测试";
    }
}
