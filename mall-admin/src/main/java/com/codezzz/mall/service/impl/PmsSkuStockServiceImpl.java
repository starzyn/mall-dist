//package com.codezzz.mall.service.impl;
//
//import com.codezzz.mall.dao.PmsSkuStockDao;
//import com.codezzz.mall.mapper.PmsSkuStockMapper;
//import com.codezzz.mall.model.PmsSkuStock;
//import com.codezzz.mall.model.PmsSkuStockExample;
//import com.codezzz.mall.service.PmsSkuStockService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
///**
// * 商品sku库存管理Service实现类
// * Created by macro on 2018/4/27.
// */
//@Service
//public class PmsSkuStockServiceImpl implements PmsSkuStockService {
//    @Autowired
//    private PmsSkuStockMapper skuStockMapper;
//    @Autowired
//    private PmsSkuStockDao skuStockDao;
//
//    @Override
//    public List<PmsSkuStock> getList(Long pid, String keyword) {
//        PmsSkuStockExample example = new PmsSkuStockExample();
//        PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
//        if (!StringUtils.isEmpty(keyword)) {
//            criteria.andSkuCodeLike("%" + keyword + "%");
//        }
//        return skuStockMapper.selectByExample(example);
//    }
//
//    @Override
//    public int update(Long pid, List<PmsSkuStock> skuStockList) {
//        return skuStockDao.replaceList(skuStockList);
//    }
//}
