package com.codezzz.goods.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.core.mapper.ProductMapper;
import com.codezzz.core.model.entity.Product;
import com.codezzz.core.model.entity.SkuStock;
import com.codezzz.goods.api.vo.ProductDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codezzz
 * @Description:
 * @date 2021/10/28 22:14
 */
@Service
@RequiredArgsConstructor
public class ProductService extends ServiceImpl<ProductMapper, Product> {

    private final SKUService skuService;

    /** 
     * @description: 获取商品详情业务层
     * @param: id 
     * @return: com.codezzz.goods.api.vo.ProductItemVo 
     * @author zhan9yn
     * @date: 2021/11/1 22:37
     */
    public ProductDetailVo getProductDetail(String id) {
        Product product = this.getById(id);
        List<SkuStock> skus = skuService.list();
        return ProductDetailVo.builder().product(product).skus(skus).build();
    }
        
}
