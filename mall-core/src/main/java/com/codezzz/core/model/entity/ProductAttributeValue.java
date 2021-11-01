package com.codezzz.core.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 存储产品参数信息的表
 *
 * @author zhangyn
 * @since 2021-10-28
 */
@Data
@ApiModel(description = "存储产品参数信息的表")
public class ProductAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = ValidateUpdate.class)
    @Null(groups = ValidateCreate.class)
    private String id;

    private String productId;

    private String productAttributeId;

    @ApiModelProperty(value = "手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开")
    private String value;

}
