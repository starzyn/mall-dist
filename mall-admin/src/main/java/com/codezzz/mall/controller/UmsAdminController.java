package com.codezzz.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codezzz.mall.common.api.CommonResult;
import com.codezzz.mall.common.domain.UserDto;
import com.codezzz.mall.common.entity.UmsAdmin;
import com.codezzz.mall.common.entity.UmsRole;
import com.codezzz.mall.dto.UmsAdminLoginParam;
import com.codezzz.mall.dto.UmsAdminParam;
import com.codezzz.mall.service.impl.UmsAdminService;
import com.codezzz.mall.service.impl.UmsMenuService;
import com.codezzz.mall.service.impl.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */
@RestController
@Api(tags = "后台用户管理相关接口")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UmsAdminController {

    private final UmsAdminService adminService;

    private final UmsRoleService roleService;

    private final UmsMenuService menuService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        return adminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo() {
        UmsAdmin umsAdmin = adminService.getCurrentAdmin();
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", menuService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    @ResponseBody
    public CommonResult logout() {
        //todo
        //清理token
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonResult<IPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        return CommonResult.success(adminService.page(page, Wrappers.lambdaQuery(new UmsAdmin()).like(UmsAdmin::getUsername, keyword)));
    }
//
//    @ApiOperation("获取指定用户信息")
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
//        UmsAdmin admin = adminService.getItem(id);
//        return CommonResult.success(admin);
//    }
//
//    @ApiOperation("修改指定用户信息")
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
//        int count = adminService.update(id, admin);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }
//
//    @ApiOperation("修改指定用户密码")
//    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult updatePassword(@RequestBody UpdateAdminPasswordParam updatePasswordParam) {
//        int status = adminService.updatePassword(updatePasswordParam);
//        if (status > 0) {
//            return CommonResult.success(status);
//        } else if (status == -1) {
//            return CommonResult.failed("提交参数不合法");
//        } else if (status == -2) {
//            return CommonResult.failed("找不到该用户");
//        } else if (status == -3) {
//            return CommonResult.failed("旧密码错误");
//        } else {
//            return CommonResult.failed();
//        }
//    }
//
//    @ApiOperation("删除指定用户信息")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult delete(@PathVariable Long id) {
//        int count = adminService.delete(id);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }
//
//    @ApiOperation("修改帐号状态")
//    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
//        UmsAdmin umsAdmin = new UmsAdmin();
//        umsAdmin.setStatus(status);
//        int count = adminService.update(id,umsAdmin);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }
//
//    @ApiOperation("给用户分配角色")
//    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
//                                   @RequestParam("roleIds") List<Long> roleIds) {
//        int count = adminService.updateRole(adminId, roleIds);
//        if (count >= 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }
//
//    @ApiOperation("获取指定用户的角色")
//    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
//        List<UmsRole> roleList = adminService.getRoleList(adminId);
//        return CommonResult.success(roleList);
//    }
//
    @ApiOperation("根据用户名获取通用用户信息")
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDTO = adminService.loadUserByUsername(username);
        return userDTO;
    }
}
