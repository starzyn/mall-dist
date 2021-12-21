package com.codezzz.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.mall.common.entity.UmsResource;
import com.codezzz.mall.common.entity.UmsRole;
import com.codezzz.mall.common.entity.UmsRoleResourceRelation;
import com.codezzz.mall.common.mapper.UmsResourceMapper;
import com.codezzz.mall.common.mapper.UmsRoleResourceRelationMapper;
import com.codezzz.mall.common.constant.AuthConstant;
import com.codezzz.mall.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台资源管理
 */
@Service
@RequiredArgsConstructor
public class UmsResourceService extends ServiceImpl<UmsResourceMapper, UmsResource> {

    private final UmsRoleService roleService;

    private final UmsRoleResourceRelationMapper roleResourceRelationMapper;

    private final RedisService redisService;

    @Value("${spring.application.name}")
    private final String applicationName;

    public void create(UmsResource umsResource) {
        this.save(umsResource);
        initResourceRolesMap();
    }

    public void update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        this.updateById(umsResource);
        initResourceRolesMap();
    }

    public UmsResource getItem(Long id) {
        return this.getById(id);
    }

    public void delete(Long id) {
        this.removeById(id);
        initResourceRolesMap();
    }

    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        IPage<UmsResource> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        return this.page(page, Wrappers.lambdaQuery(new UmsResource())
                .eq(UmsResource::getCategoryId, categoryId)
                .like(StringUtils.isEmpty(nameKeyword), UmsResource::getName, nameKeyword)
                .like(StringUtils.isEmpty(urlKeyword), UmsResource::getUrl, urlKeyword)).getRecords();
    }

    public Map<String,List<String>> initResourceRolesMap() {
        Map<String,List<String>> resourceRoleMap = new TreeMap<>();
        List<UmsResource> resourceList = list();
        List<UmsRole> roleList = roleService.list();
        List<UmsRoleResourceRelation> relationList = roleResourceRelationMapper.selectList(null);
        for (UmsResource resource : resourceList) {
            Set<Long> roleIds = relationList.stream().filter(item -> item.getResourceId().equals(resource.getId())).map(UmsRoleResourceRelation::getRoleId).collect(Collectors.toSet());
            List<String> roleNames = roleList.stream().filter(item -> roleIds.contains(item.getId())).map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
            resourceRoleMap.put("/"+applicationName+resource.getUrl(),roleNames);
        }
        redisService.del(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        redisService.hSetAll(AuthConstant.RESOURCE_ROLES_MAP_KEY, resourceRoleMap);
        return resourceRoleMap;

    }
}
