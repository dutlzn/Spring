package org.wudiSpringFramework.mvc.processor.impl;


import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.processor.RequestProcessor;

/**
 * 请求预处理，包括编码以及路径处理
 */
@Slf4j
public class PreRequestProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain requestProcessorChain) throws Exception {
        // 1.设置请求编码，将其统一设置成UTF-8
        requestProcessorChain.getRequest().setCharacterEncoding("UTF-8");
        // 2.将请求路径末尾的/剔除，为后续匹配Controller请求路径做准备
        // （一般Controller的处理路径是/aaa/bbb，所以如果传入的路径结尾是/aaa/bbb/，
        // 就需要处理成/aaa/bbb）
        String path = requestProcessorChain.getRequestPath();
        if(path.length()> 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
            requestProcessorChain.setRequestPath(path);
        }

        log.info("PreRequestProcessor  method:{}, request path: {}",
                requestProcessorChain.getRequestMethod(), requestProcessorChain.getRequestPath());

        return true;
    }
}
