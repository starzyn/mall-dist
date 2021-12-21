package com.codezzz.mall.component;

import com.codezzz.mall.service.impl.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>
 *     资源与角色访问对应关系操作组件
 *     初始化url -> 角色 映射关系到缓存
 * </p>
 */
@Component
public class ResourceRoleRulesHolder {

    @Autowired
    private UmsResourceService resourceService;

    @PostConstruct
    public void initResourceRolesMap(){
        resourceService.initResourceRolesMap();
    }
}
