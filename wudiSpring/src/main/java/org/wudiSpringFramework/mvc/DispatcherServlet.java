package org.wudiSpringFramework.mvc;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.aop.AspectWeaver;
import org.wudiSpringFramework.core.BeanContainer;
import org.wudiSpringFramework.inject.DependencyInjector;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;
import org.wudiSpringFramework.mvc.processor.impl.ControllerRequestProcessor;
import org.wudiSpringFramework.mvc.processor.impl.JspRequestProcessor;
import org.wudiSpringFramework.mvc.processor.impl.PreRequestProcessor;
import org.wudiSpringFramework.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


// 拦截所有请求 https://blog.csdn.net/qq_43040688/article/details/108521981
@Slf4j
@WebServlet("/*") // 真正拦截所有的请求
public class DispatcherServlet extends HttpServlet {

    List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init(){
        // 1 初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.spring");
        // aop 在 ioc 之前
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
        // 2 初始化请求处理器责任链
        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JspRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // 1 创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSOR.iterator(), req, resp);
        // 2 通过责任链模式来依次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        // 3 对处理结果进行渲染
        requestProcessorChain.doRender();
    }


}
