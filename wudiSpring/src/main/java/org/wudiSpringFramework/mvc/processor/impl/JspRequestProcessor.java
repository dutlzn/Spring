package org.wudiSpringFramework.mvc.processor.impl;

import com.sun.org.apache.regexp.internal.RE;
import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
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
    private RequestDispatcher jspServlet;

    public JspRequestProcessor(ServletContext servletContext){
        this.jspServlet = servletContext.getNamedDispatcher(JSP_SERVLET);

        if(this.jspServlet == null) {
            throw new RuntimeException("There is no jsp servlet.");
        }

    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        if(isJspResponse(requestProcessorChain.getRequestPath())) {
            jspServlet.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 是否是JSP请求
     * @param requestPath
     * @return
     */
    private boolean isJspResponse(String requestPath) {
        return requestPath.startsWith(JSP_RESOURCE_FREFIX);
    }
}
