//package com.codezzz.mall.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.codezzz.mall.common.entity.CmsSubject;
//import com.codezzz.mall.common.mapper.CmsSubjectCategoryMapper;
//import com.github.pagehelper.PageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//
///**
// * 商品专题Service实现类
// * Created by macro on 2018/6/1.
// */
//@Service
//public class CmsSubjectService extends ServiceImpl<CmsSubjectCategoryMapper, CmsSubject> {
//
//    public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        CmsSubjectExample example = new CmsSubjectExample();
//        CmsSubjectExample.Criteria criteria = example.createCriteria();
//        if (!StringUtils.isEmpty(keyword)) {
//            criteria.andTitleLike("%" + keyword + "%");
//        }
//        return subjectMapper.selectByExample(example);
//    }
//}
