package com.codezzz.mallcore.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.time.LocalDateTime;
import java.io.Serializable;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 *
 * @author zhangyn
 * @since 2021-10-25
 */
@Data
@ApiModel(description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = ValidateUpdate.class)
    @Null(groups = ValidateCreate.class)
    private String id;

    private String username;

    private String password;

    private String nickname;

    private String type;

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createdAt;

    private Integer status;

    private String phoneNumber;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "特权等级")
    private String vipLevel;

}
