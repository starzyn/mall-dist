package com.codezzz.mall.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.codezzz.mall.common.entity.UmsMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 后台菜单表 Mapper 接口
 *
 * @author starzyn
 * @since 2021-11-04
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {
    /**
     * 不做空值检查，根据主键强制更新除“created_at”之外的所有字段（可将字段更新为null）
     *
     * @param entity 目标实体对象
     * @return 受影响的数据量
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) UmsMenu entity);

    @Select("SELECT id, parent_id, create_time, title, `level`, sort, `name`, icon, hidden FROM ums_menu WHERE id IN ( SELECT menu_id FROM ums_role_menu_relation WHERE role_id IN ( SELECT role_id FROM ums_admin_role_relation WHERE admin_id = #{adminId} ) )")
    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);
}
