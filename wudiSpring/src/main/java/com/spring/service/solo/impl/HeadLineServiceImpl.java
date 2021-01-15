package com.spring.service.solo.impl;

import com.spring.entity.bo.HeadLine;
import com.spring.entity.dto.Result;
import com.spring.service.solo.HeadLineService;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.core.annotation.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        log.info("addHeadLine被执行啦" +
                "lineName:{}, lineLink:{}, lineImg:{}, priority:{}",
                headLine.getLineName(),
                headLine.getLineLink(),
                headLine.getLineImg(),
                headLine.getPriority());
        Result<Boolean> result = new Result<>();
        result.setCode(200);
        result.setMsg("成功了");
        result.setData(true);
        return result;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize) {
        List<HeadLine> headLineList = new ArrayList<>();
        HeadLine headLine1 = new HeadLine();
        headLine1.setLineId(1L);
        headLine1.setLineName("头条1");
        headLine1.setLineLink("www.baidu.com");
        headLine1.setLineImg("头条图片地址1");
        headLineList.add(headLine1);
        HeadLine headLine2 = new HeadLine();
        headLine2.setLineId(2L);
        headLine2.setLineName("头条2");
        headLine2.setLineLink("www.bilibili.com");
        headLine2.setLineImg("头条图片地址2");
        headLineList.add(headLine2);
        Result<List<HeadLine>> result = new Result<>();
        result.setData(headLineList);
        result.setCode(200);
        return result;
    }
}
