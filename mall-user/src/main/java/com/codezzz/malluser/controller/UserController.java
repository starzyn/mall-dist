package com.codezzz.malluser.controller;

import com.codezzz.mallcore.model.dto.RespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codezzz
 * @version 0.0.1
 * @Description:
 * @date 2021/10/18 17:01
 */

@RestController
@Api(tags = "用户相关接口")
public class UserController {

    @ApiOperation("查看用户信息")
    @GetMapping("/userinfo")
    /**
     * @description 查看用户信息
     * @param id 用户ID
     * @return com.codezzz.mallcore.model.dto.RespDTO<?>
     * @throws
     * @author zhan9yn
     * @date 2021/10/19 10:45 下午
    */
    public RespDTO<?> userInfo (@ApiParam("用户ID") @RequestParam("id") String id) {
        return null;
    }


}
