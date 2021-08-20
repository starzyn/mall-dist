package com.codezzz.malluser.controller;

import com.codezzz.mallcore.model.dto.RespDTO;
import com.codezzz.malluser.constant.UserConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/20 16:13
 */

@RestController
@Api(tags = "登陆接口")
@RequestMapping("${service-prefix}/login")
@RequiredArgsConstructor
public class LoginController {

    private final RedisTemplate redisTemplate;

    @ApiOperation("登陆")
    @PostMapping
    public RespDTO<String> login(@RequestParam("pub") @ApiParam("用户名/手机号") String pub,
                                 @RequestParam("value") @ApiParam("密码/验证码") String value,
                                 @RequestParam("type") @ApiParam("登陆类型") String type,
                                 @RequestParam("clientId") @ApiParam("客户端ID") String clientId) {

        if (Objects.equals(type, UserConstant.PWD_MODE)) {

        } else if (Objects.equals(type, UserConstant.CODE_MODE)) {

        }

        return RespDTO.onSuc();
    }
}
