package com.spring.controller;

import com.spring.entity.bo.HeadLine;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
@Slf4j
public class HelloServlet2 extends HttpServlet {
//    引入 @Slf4j可以省略这句话
//    Logger log = LoggerFactory.getLogger(HelloServlet.class);
    @Override
    public void init() {
        System.out.println("初始化Servlet");
    }

    @Override
    public void destroy() {
        System.out.println("destroy Servlet");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("是我执行了doGet方法");
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "我的简易框架111";
        log.debug("name is: " + name);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
        HeadLine headLine = new HeadLine();
        headLine.setLineId(1l);
        headLine.getLineId();
    }
}
