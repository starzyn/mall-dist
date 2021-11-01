package com.codezzz.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.codezzz.core.model.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

/**
 * 产品分类 Mapper 接口
 *
 * @author zhangyn
 * @since 2021-10-28
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    /**
     * 不做空值检查，根据主键强制更新除“created_at”之外的所有字段（可将字段更新为null）
     *
     * @param entity 目标实体对象
     * @return 受影响的数据量
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) ProductCategory entity);
}
