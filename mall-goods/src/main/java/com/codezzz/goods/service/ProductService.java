package com.codezzz.goods.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codezzz.core.mapper.ProductMapper;
import com.codezzz.core.model.entity.Product;
import com.codezzz.goods.api.vo.ProductItemVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codezzz
 * @Description:
 * @date 2021/10/28 22:14
 */
@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {

    /** 
     * @description: 商品列表业务层接口
     * @param:  
     * @return: java.util.List<com.codezzz.goods.api.vo.ProductVo> 
     * @author zhan9yn
     * @date: 2021/10/28 22:37
     */
    public List<ProductItemVo> listAll() {

        return null;
    }
}
