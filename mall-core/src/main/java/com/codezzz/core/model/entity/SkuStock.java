package com.codezzz.core.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.math.BigDecimal;
import java.io.Serializable;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * sku的库存
 *
 * @author zhangyn
 * @since 2021-10-28
 */
@Data
@ApiModel(description = "sku的库存")
public class SkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = ValidateUpdate.class)
    @Null(groups = ValidateCreate.class)
    private String id;

    private String productId;

    @ApiModelProperty(value = "sku编码")
    @NotBlank
    private String skuCode;

    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "单品促销价格")
    private BigDecimal promotionPrice;

    @ApiModelProperty(value = "锁定库存")
    private Integer lockStock;

    @ApiModelProperty(value = "商品销售属性，json格式")
    private String spData;

}
