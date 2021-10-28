package com.codezzz.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.core.mapper.UserMapper;
import com.codezzz.core.model.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 16:05
 */

@Service
public class UserService extends ServiceImpl<UserMapper, User>{
}
