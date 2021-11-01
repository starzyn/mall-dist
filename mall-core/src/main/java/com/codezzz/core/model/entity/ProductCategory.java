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
 * 产品分类
 *
 * @author zhangyn
 * @since 2021-10-28
 */
@Data
@ApiModel(description = "产品分类")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = ValidateUpdate.class)
    @Null(groups = ValidateCreate.class)
    private String id;

    @ApiModelProperty(value = "上机分类的编号：0表示一级分类")
    private String parentId;

    private String name;

    @ApiModelProperty(value = "分类级别：0->1级；1->2级")
    private Integer level;

    private Integer productCount;

    private String productUnit;

    @ApiModelProperty(value = "是否显示在导航栏：0->不显示；1->显示")
    private Integer navStatus;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer showStatus;

    private Integer sort;

    @ApiModelProperty(value = "图标")
    private String icon;

    private String keywords;

    @ApiModelProperty(value = "描述")
    private String description;

}
