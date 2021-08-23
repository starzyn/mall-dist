package com.codezzz.malluser.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.mallcore.mapper.UserMapper;
import com.codezzz.mallcore.model.entity.User;
import com.codezzz.malluser.service.UserService;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 16:05
 */

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
