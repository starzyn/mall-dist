package com.codezzz.mall.dto;

import com.codezzz.mall.common.entity.PmsProduct;
import com.codezzz.mall.common.entity.SmsFlashPromotionProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 限时购及商品信息封装
 * Created by macro on 2018/11/16.
 */
public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation {
    @Getter
    @Setter
    @ApiModelProperty("关联商品")
    private PmsProduct product;
}
