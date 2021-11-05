//package com.codezzz.mall.service.impl;
//
//import com.github.pagehelper.PageHelper;
//import com.codezzz.mall.dao.SmsFlashPromotionProductRelationDao;
//import com.codezzz.mall.dto.SmsFlashPromotionProduct;
//import com.codezzz.mall.mapper.SmsFlashPromotionProductRelationMapper;
//import com.codezzz.mall.model.SmsFlashPromotionProductRelation;
//import com.codezzz.mall.model.SmsFlashPromotionProductRelationExample;
//import com.codezzz.mall.service.SmsFlashPromotionProductRelationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 限时购商品关联管理Service实现类
// * Created by macro on 2018/11/16.
// */
//@Service
//public class SmsFlashPromotionProductRelationServiceImpl implements SmsFlashPromotionProductRelationService {
//    @Autowired
//    private SmsFlashPromotionProductRelationMapper relationMapper;
//    @Autowired
//    private SmsFlashPromotionProductRelationDao relationDao;
//    @Override
//    public int create(List<SmsFlashPromotionProductRelation> relationList) {
//        for (SmsFlashPromotionProductRelation relation : relationList) {
//            relationMapper.insert(relation);
//        }
//        return relationList.size();
//    }
//
//    @Override
//    public int update(Long id, SmsFlashPromotionProductRelation relation) {
//        relation.setId(id);
//        return relationMapper.updateByPrimaryKey(relation);
//    }
//
//    @Override
//    public int delete(Long id) {
//        return relationMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public SmsFlashPromotionProductRelation getItem(Long id) {
//        return relationMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum) {
//        PageHelper.startPage(pageNum,pageSize);
//        return relationDao.getList(flashPromotionId,flashPromotionSessionId);
//    }
//
//    @Override
//    public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
//        SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
//        example.createCriteria()
//                .andFlashPromotionIdEqualTo(flashPromotionId)
//                .andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
//        return relationMapper.countByExample(example);
//    }
//}
