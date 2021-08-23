package com.codezzz.malluser.service;

import com.codezzz.malluser.controller.vo.LoginForm;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 13:56
 */

public interface LoginService {
    String login(LoginForm loginForm);
}
