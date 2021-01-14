package com.spring.controller.superadmin;

import com.spring.entity.bo.HeadLine;
import com.spring.entity.dto.Result;
import com.spring.service.solo.HeadLineService;
import org.wudiSpringFramework.core.annotation.Controller;
import org.wudiSpringFramework.inject.annotation.Autowired;
import org.wudiSpringFramework.mvc.annotation.RequestMapping;
import org.wudiSpringFramework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/headline")
public class HeadLineOperationController {
    @Autowired(value = "HeadLineServiceImpl")
    private HeadLineService headLineService;

    public Result<Boolean> addHeadLine(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.addHeadLine(new HeadLine());
    };


    @RequestMapping(value="/remove", method= RequestMethod.GET)
    public void removeHeadLine(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除headline");
    }
//    public Result<Boolean> removeHeadLine(HttpServletRequest req, HttpServletResponse resp){
//        //TODO:参数校验以及请求参数转化
//        return headLineService.removeHeadLine(1);
//    }
    public Result<Boolean> modifyHeadLine(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.modifyHeadLine(new HeadLine());
    }
    public Result<HeadLine> queryHeadLineById(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.queryHeadLineById(1);
    }
    public Result<List<HeadLine>>queryHeadLine(HttpServletRequest req, HttpServletResponse resp){
        //TODO:参数校验以及请求参数转化
        return headLineService.queryHeadLine(null, 1, 100);
    }
}
