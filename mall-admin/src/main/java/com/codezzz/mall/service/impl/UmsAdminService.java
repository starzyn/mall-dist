package com.codezzz.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.mall.common.api.CommonResult;
import com.codezzz.mall.common.api.ResultCode;
import com.codezzz.mall.common.constant.AuthConstant;
import com.codezzz.mall.common.domain.UserDto;
import com.codezzz.mall.common.entity.UmsAdmin;
import com.codezzz.mall.common.entity.UmsAdminLoginLog;
import com.codezzz.mall.common.entity.UmsAdminRoleRelation;
import com.codezzz.mall.common.entity.UmsRole;
import com.codezzz.mall.common.exception.Asserts;
import com.codezzz.mall.common.mapper.UmsAdminMapper;
import com.codezzz.mall.dto.UmsAdminParam;
import com.codezzz.mall.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UmsAdminService实现类
 * Created by macro on 2018/4/26.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UmsAdminService extends ServiceImpl<UmsAdminMapper, UmsAdmin> {

    private final UmsAdminRoleRelationService adminRoleRelationService;

    private final UmsAdminLoginLogService loginLogService;

    private final UmsRoleService roleService;

    private final AuthService authService;

    private final UmsAdminCacheService adminCacheService;

    private final HttpServletRequest request;


    public UmsAdmin getAdminByUsername(String username) {
        List<UmsAdmin> adminList = this.list(Wrappers.lambdaQuery(new UmsAdmin()).eq(UmsAdmin::getUsername, username).select());
        if (adminList != null && adminList.size() > 0) {
            return adminList.get(0);
        }
        return null;
    }


    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        //查询是否有相同用户名的用户
        List<UmsAdmin> umsAdminList = this.list(Wrappers.lambdaQuery(umsAdmin));
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = BCrypt.hashpw(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        this.save(umsAdmin);
        return umsAdmin;
    }

    public CommonResult login(String username, String password) {
        if(StrUtil.isEmpty(username)||StrUtil.isEmpty(password)){
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret","123456");
        params.put("grant_type","password");
        params.put("username",username);
        params.put("password",password);
        CommonResult restResult = authService.getAccessToken(params);
        if(Objects.equals(ResultCode.SUCCESS.getCode(), restResult.getCode()) && restResult.getData()!=null){
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        }
        return restResult;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if(admin==null) return;
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        loginLogService.save(loginLog);
    }
//
//    /**
//     * 根据用户名修改登录时间
//     */
//    private void updateLoginTimeByUsername(String username) {
//        UmsAdmin record = new UmsAdmin();
//        record.setLoginTime(new Date());
//        UmsAdminExample example = new UmsAdminExample();
//        example.createCriteria().andUsernameEqualTo(username);
//        adminMapper.updateByExampleSelective(record, example);
//    }
//
//    @Override
//    public UmsAdmin getItem(Long id) {
//        return adminMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
//        PageHelper.startPage(pageNum, pageSize);
//        UmsAdminExample example = new UmsAdminExample();
//        UmsAdminExample.Criteria criteria = example.createCriteria();
//        if (!StringUtils.isEmpty(keyword)) {
//            criteria.andUsernameLike("%" + keyword + "%");
//            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
//        }
//        return adminMapper.selectByExample(example);
//    }
//
//    @Override
//    public int update(Long id, UmsAdmin admin) {
//        admin.setId(id);
//        UmsAdmin rawAdmin = adminMapper.selectByPrimaryKey(id);
//        if(rawAdmin.getPassword().equals(admin.getPassword())){
//            //与原加密密码相同的不需要修改
//            admin.setPassword(null);
//        }else{
//            //与原加密密码不同的需要加密修改
//            if(StrUtil.isEmpty(admin.getPassword())){
//                admin.setPassword(null);
//            }else{
//                admin.setPassword(BCrypt.hashpw(admin.getPassword()));
//            }
//        }
//        int count = adminMapper.updateByPrimaryKeySelective(admin);
//        adminCacheService.delAdmin(id);
//        return count;
//    }
//
//    @Override
//    public int delete(Long id) {
//        int count = adminMapper.deleteByPrimaryKey(id);
//        adminCacheService.delAdmin(id);
//        return count;
//    }
//
//    @Override
//    public int updateRole(Long adminId, List<Long> roleIds) {
//        int count = roleIds == null ? 0 : roleIds.size();
//        //先删除原来的关系
//        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
//        adminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
//        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
//        //建立新关系
//        if (!CollectionUtils.isEmpty(roleIds)) {
//            List<UmsAdminRoleRelation> list = new ArrayList<>();
//            for (Long roleId : roleIds) {
//                UmsAdminRoleRelation roleRelation = new UmsAdminRoleRelation();
//                roleRelation.setAdminId(adminId);
//                roleRelation.setRoleId(roleId);
//                list.add(roleRelation);
//            }
//            adminRoleRelationDao.insertList(list);
//        }
//        return count;
//    }
//
    public List<UmsRole> getRoleList(Long adminId) {
        List<Long> roleIds = adminRoleRelationService.listObjs(Wrappers.lambdaQuery(new UmsAdminRoleRelation())
                .eq(UmsAdminRoleRelation::getAdminId, adminId)
                .select(UmsAdminRoleRelation::getRoleId))
                .parallelStream()
                .map(obj -> (Long)obj)
                .collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            return null;
        }
        return roleService.list(Wrappers.lambdaQuery(new UmsRole()).in(UmsRole::getId, roleIds).select());
    }
//
//    @Override
//    public List<UmsResource> getResourceList(Long adminId) {
//        return adminRoleRelationDao.getResourceList(adminId);
//    }
//
//    @Override
//    public int updatePassword(UpdateAdminPasswordParam param) {
//        if(StrUtil.isEmpty(param.getUsername())
//                ||StrUtil.isEmpty(param.getOldPassword())
//                ||StrUtil.isEmpty(param.getNewPassword())){
//            return -1;
//        }
//        UmsAdminExample example = new UmsAdminExample();
//        example.createCriteria().andUsernameEqualTo(param.getUsername());
//        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
//        if(CollUtil.isEmpty(adminList)){
//            return -2;
//        }
//        UmsAdmin umsAdmin = adminList.get(0);
//        if(!BCrypt.checkpw(param.getOldPassword(),umsAdmin.getPassword())){
//            return -3;
//        }
//        umsAdmin.setPassword(BCrypt.hashpw(param.getNewPassword()));
//        adminMapper.updateByPrimaryKey(umsAdmin);
//        adminCacheService.delAdmin(umsAdmin.getId());
//        return 1;
//    }
//
    public UserDto loadUserByUsername(String username){
        //获取用户信息
        UmsAdmin admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsRole> roleList = getRoleList(admin.getId());
            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(admin,userDTO);
            if(CollUtil.isNotEmpty(roleList)){
                List<String> roleStrList = roleList.stream().map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
                userDTO.setRoles(roleStrList);
            }
            return userDTO;
        }
        return null;
    }

    public UmsAdmin getCurrentAdmin() {
        String userStr = (String) request.getAttribute(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(userStr)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
        UmsAdmin admin = adminCacheService.getAdmin(userDto.getId());
        if(admin!=null){
            return admin;
        }else{
            admin = this.getById(userDto.getId());
            adminCacheService.setAdmin(admin);
            return admin;
        }
    }
}
