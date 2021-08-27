package com.codezzz.mallcore.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (user)实体类
 *
 * @author zhangyn
 * @since 2021-08-20 11:51:57
 * @description pojo
 */
@Data
@Accessors(chain = true)
@TableName("user")
@Builder
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
	private String id;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * nickname
     */
    private String nickname;
    /**
     * type
     */
    private String type;
    /**
     * createdAt
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createdAt;
    /**
     * status
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * phoneNumber
     */
    private String phoneNumber;

}