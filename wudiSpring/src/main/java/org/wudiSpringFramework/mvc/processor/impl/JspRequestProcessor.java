package org.wudiSpringFramework.mvc.processor.impl;

import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

import javax.servlet.ServletContext;

/**
 * JSP资源请求处理
 * 利用tomcat的jspServlet处理
 */
public class JspRequestProcessor implements RequestProcessor {
    // jsp请求的RequestDispatcher名称
    private static final String JSP_SERVLET = "jsp";
    // jsp请求资源路径前缀
    private static final String JSP_RESOURCE_FREFIX = "/templates/";

    /**
     * jsp的RequestDispatcher 处理jsp资源
     * @param servletContext
     */
    public JspRequestProcessor(ServletContext servletContext) {
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
