package com.codezzz.mall.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.codezzz.mall.common.entity.PmsCommentReplay;
import org.apache.ibatis.annotations.Param;

/**
 * 产品评价回复表 Mapper 接口
 *
 * @author starzyn
 * @since 2021-11-04
 */
public interface PmsCommentReplayMapper extends BaseMapper<PmsCommentReplay> {
    /**
     * 不做空值检查，根据主键强制更新除“created_at”之外的所有字段（可将字段更新为null）
     *
     * @param entity 目标实体对象
     * @return 受影响的数据量
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) PmsCommentReplay entity);
}
