package com.codezzz.malluser.controller.vo;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 8, max = 16)
    private String pub;

    @Length(min = 8, max = 16)
    @NotEmpty(message = "密码/验证码不能为空")
    private String value;

    @NotEmpty
    private String type;

    @NotEmpty
    private String clientId;
}
