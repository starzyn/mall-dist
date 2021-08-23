package com.codezzz.malluser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codezzz.mallcore.exception.ErrorCode;
import com.codezzz.mallcore.exception.MallException;
import com.codezzz.mallcore.model.entity.User;
import com.codezzz.malluser.constant.UserConstant;
import com.codezzz.malluser.controller.vo.LoginForm;
import com.codezzz.malluser.service.LoginService;
import com.codezzz.malluser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 13:56
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RedisTemplate redisTemplate;

    private final UserService userService;

    @Override
    public String login(LoginForm loginForm) {
        if (Objects.equals(UserConstant.CODE_MODE, loginForm.getType())) {
            String phone = loginForm.getPub();
            String code = redisTemplate.opsForValue().get(UserConstant.CODE_PREFIX + phone).toString();
            if (!Objects.equals(code, loginForm.getValue())) {
                throw new MallException(ErrorCode.PARAM_ERROR);
            }
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, phone));
            if (Objects.isNull(user)) {
                throw new MallException(ErrorCode.USER_NOT_FOUND);
            }

            OAuth2ResourceServerProperties.Jwt jwt = new OAuth2ResourceServerProperties.Jwt();


        } else if (Objects.equals(UserConstant.PWD_MODE, loginForm.getType())) {

        }
        return null;
    }


}
