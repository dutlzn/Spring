package org.wudiSpringFramework.mvc.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * 静态资源请求处理包括但不限于图片、css以及js文件等
 * 利用的tomcat默认请求派发器RequestDispatcher处理
 */
@Slf4j
public class StaticResourceRequestProcessor implements RequestProcessor {
    private static final String STATIC_RESOURCE_PREFIX = "/static/";
    private static final String DEFAULT_TOMCAT_SERVLET = "default";

    // tomcat 默认请求派发器
    RequestDispatcher requestDispatcher;

    public StaticResourceRequestProcessor(ServletContext servletContext) {
        this.requestDispatcher = servletContext.getNamedDispatcher(DEFAULT_TOMCAT_SERVLET);
        if(this.requestDispatcher == null) {
            throw new RuntimeException("There is no default tomcat servlet");
        }

        log.info("The default servlet for static resource is {}", DEFAULT_TOMCAT_SERVLET);
    }

    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1 通过请求路径判断是否是请求的静态资源 webapp/static
        String requestPath = requestProcessorChain.getRequestPath();
        if(isStaticResource(requestPath)) {
            // 2 如果是静态资源，则将请求转发给default servlet 处理
            requestDispatcher.forward(requestProcessorChain.getRequest(), requestProcessorChain.getResponse());
            return false;
        }
        return true;
    }

    /**
     * 通过请求路径前缀（目录）是否为静态资源 /static
     * @param requestPath
     * @return
     */
    private boolean isStaticResource(String requestPath) {
        return requestPath.startsWith(STATIC_RESOURCE_PREFIX);
    }
}
