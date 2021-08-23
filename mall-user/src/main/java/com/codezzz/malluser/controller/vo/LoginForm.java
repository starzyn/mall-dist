package com.codezzz.malluser.controller.vo;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 13:58
 */

@Data
@Builder
public class LoginForm {
    @NotEmpty(message = "用户名/手机号不能为空")
    private String pub;

    @NotEmpty(message = "密码/验证码不能为空")
    private String value;

    @NotEmpty
    private String type;

    @NotEmpty
    private String clientId;
}
