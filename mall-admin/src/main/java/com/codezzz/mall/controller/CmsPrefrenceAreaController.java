package com.codezzz.mall.controller;

import com.codezzz.mall.common.api.CommonResult;
import com.codezzz.mall.common.entity.CmsPrefrenceArea;
import com.codezzz.mall.service.impl.CmsPrefrenceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品优选管理相关接口
 */
@Controller
@Api(tags = "商品优选管理相关接口")
@RequestMapping("/prefrenceArea")
@RequiredArgsConstructor
public class CmsPrefrenceAreaController {

    private final CmsPrefrenceAreaService prefrenceAreaService;

    @ApiOperation("获取所有商品优选")
    @GetMapping(value = "/listAll")
    public CommonResult<List<CmsPrefrenceArea>> listAll() {
        List<CmsPrefrenceArea> prefrenceAreaList = prefrenceAreaService.list();
        return CommonResult.success(prefrenceAreaList);
    }
}
