package com.codezzz.goods.api.controller;

import com.codezzz.core.model.dto.RespDTO;
import com.codezzz.core.model.entity.Product;
import com.codezzz.goods.api.vo.ProductDetailVo;
import com.codezzz.goods.api.vo.ProductItemVo;
import com.codezzz.goods.api.vo.SKUVo;
import com.codezzz.goods.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author codezzz
 * @version 0.0.1
 * @Description: 商品相关接口
 * @date 2021/10/28 22:07
 */

@RestController
@RequestMapping("${service-prefix}/goods")
@RequiredArgsConstructor
@Api(tags = "商品相关接口")
public class GoodsController {

    private final ProductService productService;


    @ApiOperation("商品列表接口")
    @GetMapping("/list")
    /** 
     * @description: 商品列表接口
     * @param:  
     * @return: com.codezzz.core.model.dto.RespDTO<java.util.List<com.codezzz.goods.api.vo.ProductVo>> 
     * @author zhan9yn
     * @date: 2021/10/28 22:12
     */
    public RespDTO<List<Product>> list() {
        return RespDTO.onSuc(productService.list());
    }



    @ApiOperation("商品详情接口")
    @GetMapping("/detail/{id}")
    /** 
     * @description: 商品详情接口
     * @param: id 商品ID
     * @return: com.codezzz.core.model.dto.RespDTO<com.codezzz.goods.api.vo.ProductDetailVo> 
     * @author zhan9yn
     * @date: 2021/10/31 22:44
     */
    public RespDTO<ProductDetailVo> detail(@ApiParam("商品ID") @PathVariable("id") String id) {
        return RespDTO.onSuc(productService.getProductDetail(id));
    }



    @GetMapping("/{id}/sku")
    /** 
     * @description: 根据商品ID获取该商品的sku
     * @param: id 
     * @return: com.codezzz.core.model.dto.RespDTO<com.codezzz.goods.api.vo.SKUVo> 
     * @author zhan9yn
     * @date: 2021/11/1 22:05
     */
    public RespDTO<SKUVo> getSKU(@ApiParam("商品ID") @PathVariable("id") String id) {
        return RespDTO.onSuc(null);

    }





}
