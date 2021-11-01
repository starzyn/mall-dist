package com.codezzz.goods.api.vo;

import com.codezzz.core.model.entity.Product;
import com.codezzz.core.model.entity.SkuStock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author : zhan9yn
 * @version : 1.0
 * @description : TODO
 * @date : 2021/10/29 5:51 下午
 */
@Data
@Builder
public class ProductDetailVo {
    private Product product;
    private List<SkuStock> skus;
}
