package com.codezzz.mallcore.model.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
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
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
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
    private Date createdAt;
    /**
     * status
     */
    private Integer status;
    /**
     * phoneNumber
     */
    private String phoneNumber;

}