package com.codezzz.user.constant;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/20 20:18
 */
public interface UserConstant {

    /**
     * 密码登陆模式
     */
    String PWD_MODE = "password";

    /**
     * 验证码登陆模式
     */

    String CODE_MODE = "code";

    /**
     * redis 验证码前缀
     */
    String CODE_PREFIX = "STRING:LOGINCODE:";

    /**
     * token存储key值
     */
    String USER_KEY = "uid";

    String STATUS_NORMAL = "NORMAL";
}
