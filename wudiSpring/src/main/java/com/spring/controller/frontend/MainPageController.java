package com.spring.controller.frontend;

import com.spring.entity.dto.MainPageInfoDto;
import com.spring.entity.dto.Result;
import com.spring.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.core.annotation.Controller;
import org.wudiSpringFramework.inject.annotation.Autowired;
import org.wudiSpringFramework.mvc.annotation.RequestMapping;
import org.wudiSpringFramework.mvc.type.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Getter
@RequestMapping(value = "/main")
public class MainPageController {
    @Autowired(value = "HeadLineShopCategoryCombineServiceImpl")
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDto> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp) {
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void throwException() {
        System.out.println("发生异常啦");
        throw new RuntimeException("抛出异常测试");
    }
}