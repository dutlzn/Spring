package com.spring.controller.frontend;

import com.spring.entity.dto.MainPageInfoDto;
import com.spring.entity.dto.Result;
import com.spring.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.core.annotation.Controller;
import org.wudiSpringFramework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Getter
public class MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;
    public Result<MainPageInfoDto> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("front");
        return headLineShopCategoryCombineService.getMainPageInfo();
    }
}
