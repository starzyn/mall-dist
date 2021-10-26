package com.codezzz.gateway.constant;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/18 20:45
 */

public interface AuthConstant {
    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息前缀
     */
    String USER_PREFIX = "User ";

}
