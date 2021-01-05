package com.spring.service.combine.impl;

import com.spring.entity.bo.HeadLine;
import com.spring.entity.bo.ShopCategory;
import com.spring.entity.dto.MainPageInfoDto;
import com.spring.entity.dto.Result;
import com.spring.service.combine.HeadLineShopCategoryCombineService;
import com.spring.service.solo.HeadLineService;
import com.spring.service.solo.ShopCategoryService;

import java.util.List;

public class HeadLineShopCategoryCombineServiceImpl implements HeadLineShopCategoryCombineService {
    private HeadLineService headLineService;
    private ShopCategoryService shopCategoryService;
    @Override
    public Result<MainPageInfoDto> getMainPageInfo() {
        // 1 获取头条列表
        HeadLine headLineCondition = new HeadLine();
        headLineCondition.setEnableStatus(1);
        Result<List<HeadLine>> HeadLineResult = headLineService.queryHeadLine(headLineCondition,1,4);
        // 2 获取店铺类别列表
        ShopCategory shopCategoryCondition = new ShopCategory();
        Result<List<ShopCategory>> shopCategoryResult = shopCategoryService.queryShopCategory(shopCategoryCondition,1,100);
        //3.合并两者并返回
        Result<MainPageInfoDto> result = mergeMainPageInfoResult(HeadLineResult, shopCategoryResult);
        return result;
    }

    private Result<MainPageInfoDto> mergeMainPageInfoResult(Result<List<HeadLine>> headLineResult, Result<List<ShopCategory>> shopCategoryResult) {
        return  null;
    }
}
