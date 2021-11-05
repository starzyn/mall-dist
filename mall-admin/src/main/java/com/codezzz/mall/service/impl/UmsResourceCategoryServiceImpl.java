//package com.codezzz.mall.service.impl;
//
//import com.codezzz.mall.mapper.UmsResourceCategoryMapper;
//import com.codezzz.mall.model.UmsResourceCategory;
//import com.codezzz.mall.model.UmsResourceCategoryExample;
//import com.codezzz.mall.service.UmsResourceCategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * 后台资源分类管理Service实现类
// * Created by macro on 2020/2/5.
// */
//@Service
//public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {
//    @Autowired
//    private UmsResourceCategoryMapper resourceCategoryMapper;
//
//    @Override
//    public List<UmsResourceCategory> listAll() {
//        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
//        example.setOrderByClause("sort desc");
//        return resourceCategoryMapper.selectByExample(example);
//    }
//
//    @Override
//    public int create(UmsResourceCategory umsResourceCategory) {
//        umsResourceCategory.setCreateTime(new Date());
//        return resourceCategoryMapper.insert(umsResourceCategory);
//    }
//
//    @Override
//    public int update(Long id, UmsResourceCategory umsResourceCategory) {
//        umsResourceCategory.setId(id);
//        return resourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
//    }
//
//    @Override
//    public int delete(Long id) {
//        return resourceCategoryMapper.deleteByPrimaryKey(id);
//    }
//}
