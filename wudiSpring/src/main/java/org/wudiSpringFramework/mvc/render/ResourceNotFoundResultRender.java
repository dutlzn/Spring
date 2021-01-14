package org.wudiSpringFramework.mvc.render;

import org.wudiSpringFramework.mvc.RequestProcessorChain;

/**
 * 资源找不到时使用的    渲染器
 */
public class ResourceNotFoundResultRender implements ResultRender{
    public ResourceNotFoundResultRender(String method, String path) {
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {

    }
}
