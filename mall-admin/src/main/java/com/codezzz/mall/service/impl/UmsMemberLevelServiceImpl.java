//package com.codezzz.mall.service.impl;
//
//import com.codezzz.mall.mapper.UmsMemberLevelMapper;
//import com.codezzz.mall.model.UmsMemberLevel;
//import com.codezzz.mall.model.UmsMemberLevelExample;
//import com.codezzz.mall.service.UmsMemberLevelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 会员等级管理Service实现类
// * Created by macro on 2018/4/26.
// */
//@Service
//public class UmsMemberLevelServiceImpl implements UmsMemberLevelService{
//    @Autowired
//    private UmsMemberLevelMapper memberLevelMapper;
//    @Override
//    public List<UmsMemberLevel> list(Integer defaultStatus) {
//        UmsMemberLevelExample example = new UmsMemberLevelExample();
//        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
//        return memberLevelMapper.selectByExample(example);
//    }
//}
