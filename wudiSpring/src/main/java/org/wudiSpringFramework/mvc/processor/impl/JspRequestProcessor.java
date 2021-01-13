package org.wudiSpringFramework.mvc.processor.impl;

import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

/**
 * JSP资源请求处理
 */
public class JspRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
