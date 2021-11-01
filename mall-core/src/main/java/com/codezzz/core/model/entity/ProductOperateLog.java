package com.codezzz.core.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 *
 * @author zhangyn
 * @since 2021-10-28
 */
@Data
@ApiModel(description = "")
public class ProductOperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = ValidateUpdate.class)
    @Null(groups = ValidateCreate.class)
    private String id;

    private String productId;

    private BigDecimal priceOld;

    private BigDecimal priceNew;

    private BigDecimal salePriceOld;

    private BigDecimal salePriceNew;

    @ApiModelProperty(value = "赠送的积分")
    private Integer giftPointOld;

    private Integer giftPointNew;

    private Integer usePointLimitOld;

    private Integer usePointLimitNew;

    @ApiModelProperty(value = "操作人")
    private String operateMan;

    private LocalDateTime createTime;

}
