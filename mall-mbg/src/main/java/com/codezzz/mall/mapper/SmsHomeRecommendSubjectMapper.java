package com.codezzz.mall.mapper;

import com.codezzz.mall.model.SmsHomeRecommendSubject;
import com.codezzz.mall.model.SmsHomeRecommendSubjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsHomeRecommendSubjectMapper {
    long countByExample(SmsHomeRecommendSubjectExample example);

    int deleteByExample(SmsHomeRecommendSubjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsHomeRecommendSubject record);

    int insertSelective(SmsHomeRecommendSubject record);

    List<SmsHomeRecommendSubject> selectByExample(SmsHomeRecommendSubjectExample example);

    SmsHomeRecommendSubject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsHomeRecommendSubject record, @Param("example") SmsHomeRecommendSubjectExample example);

    int updateByExample(@Param("record") SmsHomeRecommendSubject record, @Param("example") SmsHomeRecommendSubjectExample example);

    int updateByPrimaryKeySelective(SmsHomeRecommendSubject record);

    int updateByPrimaryKey(SmsHomeRecommendSubject record);
}