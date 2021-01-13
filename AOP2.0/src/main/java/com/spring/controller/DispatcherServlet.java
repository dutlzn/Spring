package com.spring.controller;

import com.spring.controller.frontend.MainPageController;
import com.spring.controller.superadmin.HeadLineOperationController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 拦截所有请求
@Slf4j
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(){
        System.out.println("拦截所有请求");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("request path is : " + req.getServletPath());
        System.out.println("request method is : " + req.getMethod());
        if (req.getServletPath().equals("/frontend/getmainpageinfo") && req.getMethod() == "GET"){
            System.out.println("ok");
            new MainPageController().getMainPageInfo(req, resp);
        } else if(req.getServletPath() == "/superadmin/addheadline" && req.getMethod() == "POST"){
            new HeadLineOperationController().addHeadLine(req, resp);
        }
    }
}
