package com.codezzz.malluser.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/20 16:13
 */

@RestController
@Api(tags = "test")
public class TestController {
    @ApiOperation("1111")
    @GetMapping("/test")
    public String get() {
        return "test";
    }
}
