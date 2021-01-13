package org.wudiSpringFramework.mvc.processor;

import org.wudiSpringFramework.mvc.RequestProcessorChain;

/**
 * 请求处理器
 */
public interface RequestProcessor {
    boolean process(RequestProcessorChain requestProcessorChain)
            throws Exception;
}
