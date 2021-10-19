package com.codezzz.malluser.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class LoginForm {
    @ApiModelProperty("用户名/手机号")
    @NotEmpty(message = "用户名/手机号不能为空")
    @Length(min = 8, max = 16)
    private String pub;

    @ApiModelProperty("密码/验证码")
    @Length(min = 8, max = 16)
    @NotEmpty(message = "密码/验证码不能为空")
    private String value;

    @ApiModelProperty("登陆类型")
    @NotEmpty
    private String type;

    @ApiModelProperty("客户端ID")
    @NotEmpty
    private String clientId;
}
