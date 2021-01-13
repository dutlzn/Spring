package org.wudiSpringFramework.mvc.processor.impl;

import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

/**
 * Controller请求处理器
 */
public class ControllerRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        return false;
    }
}
