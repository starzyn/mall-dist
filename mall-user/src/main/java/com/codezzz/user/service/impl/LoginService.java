package com.codezzz.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codezzz.core.exception.ErrorCode;
import com.codezzz.core.exception.MallException;
import com.codezzz.core.model.entity.User;
import com.codezzz.user.constant.UserConstant;
import com.codezzz.user.controller.vo.LoginForm;
import com.codezzz.user.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 13:56
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final StringRedisTemplate redisTemplate;

    private final UserService userService;

    /** 
     * @description: 登陆接口业务层
     * @param: loginForm 
     * @return: java.lang.String 
     * @author zhan9yn
     * @date: 2021/10/18 16:49
     */
    public String login(LoginForm loginForm) {
        User user;
        if (Objects.equals(UserConstant.CODE_MODE, loginForm.getType())) {
            String phone = loginForm.getPub();
            checkPhone(phone);
            String code = redisTemplate.opsForValue().get(UserConstant.CODE_PREFIX + phone);
            if (!Objects.equals(code, loginForm.getValue())) {
                throw new MallException(ErrorCode.PARAM_ERROR);
            }
            user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhoneNumber, phone));
            if (Objects.isNull(user)) {
                user = User.builder()
                        .nickname(generateNickName())
                        //todo 密码未加盐
                        .password(generatePWD())
                        .username(generateNickName())
                        .phoneNumber(phone).build();
                userService.save(user);
            }
            return TokenUtil.createToken(UserConstant.USER_KEY, user.getId());
        } else if (Objects.equals(UserConstant.PWD_MODE, loginForm.getType())) {
            user = userService.getOne(new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, loginForm.getPub())
                    .eq(User::getPassword, loginForm.getValue()));
            if (Objects.isNull(user)) {
                user = User.builder()
                    .nickname(generateNickName())
                        //todo
                    .password(generatePWD())
                    .username(loginForm.getPub())
                    .password(loginForm.getValue())
                    .build();
                userService.save(user);
            }
            return TokenUtil.createToken(UserConstant.USER_KEY, user.getId());
        }
        return null;
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    public void sendCode(String phone) {
        String code = String.valueOf(RandomUtils.nextLong(10000, 99999));
        redisTemplate.opsForValue().set(UserConstant.CODE_PREFIX + phone, code);
        log.info("验证码发送成功：{}", code);
    }


    private String generateNickName() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);

    }

    private String generatePWD() {
        return String.valueOf(RandomUtils.nextLong(10000000L, 99999999L));
    }

    private void checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        if (!isMatch) {
            throw new MallException(ErrorCode.PARAM_ERROR);
        }
    }

}
