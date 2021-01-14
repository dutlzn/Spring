package org.wudiSpringFramework.mvc.render;

import org.wudiSpringFramework.mvc.RequestProcessorChain;

/**
 * 默认渲染器，只会返回状态码
 * 主要是处理的结果状态码返回，默认是200
 */
public class DefaultResultRender implements ResultRender {
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        //将相应状态码设置到response中
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }
}
