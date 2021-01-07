package com.spring.controller.frontend;

import com.spring.entity.dto.MainPageInfoDto;
import com.spring.entity.dto.Result;
import com.spring.service.combine.HeadLineShopCategoryCombineService;
import org.wudiSpringFramework.core.annotation.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainPageController {
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDto> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("front");
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
