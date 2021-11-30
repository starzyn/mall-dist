package com.codezzz.mall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.mall.common.entity.UmsMenu;
import com.codezzz.mall.common.entity.UmsRole;
import com.codezzz.mall.common.entity.UmsRoleMenuRelation;
import com.codezzz.mall.common.mapper.UmsRoleMapper;
import com.codezzz.mall.common.mapper.UmsRoleMenuRelationMapper;
import com.codezzz.mall.common.mapper.UmsRoleResourceRelationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台角色管理Service实现类
 */
@Service
@RequiredArgsConstructor
public class UmsRoleService extends ServiceImpl<UmsRoleMapper, UmsRole> {

//    private final UmsRoleMenuRelationService roleMenuRelationService;
//
//    private UmsRoleMenuRelationMapper roleMenuRelationMapper;
//
//    private final UmsRoleResourceRelationMapper roleResourceRelationMapper;
//
//
//    private final UmsRoleMapper roleDao;
//
//    private final UmsResourceService resourceService;
//
//    public int create(UmsRole role) {
//        role.setCreateTime(new Date());
//        role.setAdminCount(0);
//        role.setSort(0);
//        return roleMapper.insert(role);
//    }
//
//    @Override
//    public int update(Long id, UmsRole role) {
//        role.setId(id);
//        return roleMapper.updateByPrimaryKeySelective(role);
//    }
//
//    @Override
//    public int delete(List<Long> ids) {
//        UmsRoleExample example = new UmsRoleExample();
//        example.createCriteria().andIdIn(ids);
//        int count = roleMapper.deleteByExample(example);
//        resourceService.initResourceRolesMap();
//        return count;
//    }
//
//    @Override
//    public List<UmsRole> list() {
//        return roleMapper.selectByExample(new UmsRoleExample());
//    }
//
//    @Override
//    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
//        PageHelper.startPage(pageNum, pageSize);
//        UmsRoleExample example = new UmsRoleExample();
//        if (!StringUtils.isEmpty(keyword)) {
//            example.createCriteria().andNameLike("%" + keyword + "%");
//        }
//        return roleMapper.selectByExample(example);
//    }
//
//    @Override
//    public List<UmsMenu> getMenuList(Long adminId) {
//        Long roleId = this.getOne(Wrappers.lambdaQuery(new UmsRole()).eq(Um))
//        return roleDao.getMenuList(adminId);
//    }
//
//    @Override
//    public List<UmsMenu> listMenu(Long roleId) {
//        return roleDao.getMenuListByRoleId(roleId);
//    }
//
//    @Override
//    public List<UmsResource> listResource(Long roleId) {
//        return roleDao.getResourceListByRoleId(roleId);
//    }
//
//    @Override
//    public int allocMenu(Long roleId, List<Long> menuIds) {
//        //先删除原有关系
//        UmsRoleMenuRelationExample example=new UmsRoleMenuRelationExample();
//        example.createCriteria().andRoleIdEqualTo(roleId);
//        roleMenuRelationMapper.deleteByExample(example);
//        //批量插入新关系
//        for (Long menuId : menuIds) {
//            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
//            relation.setRoleId(roleId);
//            relation.setMenuId(menuId);
//            roleMenuRelationMapper.insert(relation);
//        }
//        return menuIds.size();
//    }
//
//    @Override
//    public int allocResource(Long roleId, List<Long> resourceIds) {
//        //先删除原有关系
//        UmsRoleResourceRelationExample example=new UmsRoleResourceRelationExample();
//        example.createCriteria().andRoleIdEqualTo(roleId);
//        roleResourceRelationMapper.deleteByExample(example);
//        //批量插入新关系
//        for (Long resourceId : resourceIds) {
//            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
//            relation.setRoleId(roleId);
//            relation.setResourceId(resourceId);
//            roleResourceRelationMapper.insert(relation);
//        }
//        resourceService.initResourceRolesMap();
//        return resourceIds.size();
//    }
}
