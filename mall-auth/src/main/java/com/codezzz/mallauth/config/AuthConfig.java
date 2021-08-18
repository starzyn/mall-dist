package com.codezzz.mallauth.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/17 20:24
 */

public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(auth -> auth.anyRequest().authenticated());
    }
}
