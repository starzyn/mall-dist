package com.codezzz.user.controller;

import com.codezzz.core.model.dto.RespDTO;
import com.codezzz.core.util.ValidationUtils;
import com.codezzz.user.controller.vo.LoginForm;
import com.codezzz.user.service.impl.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * @author codezzz
 * @Description: 登录接口
 * @date 2021/8/20 16:13
 */

@RestController
@RequestMapping("${service-prefix}/login")
@RequiredArgsConstructor
@Api(tags = "登陆接口")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation("登陆")
    @PostMapping
    /**
     * @description 登陆
     * 
     * @param pub 用户名/手机号
     * @param value 密码/验证码
     * @param type 登录类型
     * @param clientId 客户端ID
     * @return com.codezzz.mallcore.model.dto.RespDTO<java.lang.String>
     * @throws 
     * @author zhan9yn
     * @date 2021/9/27 2:38 下午
    */
    public RespDTO<String> login(@ApiParam("用户名/手机") @RequestParam("pub") String pub,
                                 @ApiParam("密码/验证码") @RequestParam("value") String value,
                                 @ApiParam("登陆类型") @RequestParam("type") String type,
                                 @ApiParam("客户端ID") @RequestParam("clientId") String clientId) {
        LoginForm loginForm = LoginForm.builder()
                .pub(pub)
                .value(value)
                .type(type)
                .clientId(clientId).build();
        ValidationUtils.validate(loginForm);
        return RespDTO.onSuc(loginService.login(loginForm));
    }
}
