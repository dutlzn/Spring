package org.wudiSpringFramework.mvc.processor.impl;

import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

/**
 * 静态资源请求处理包括但不限于图片、css以及js文件等
 */
public class StaticResourceRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
