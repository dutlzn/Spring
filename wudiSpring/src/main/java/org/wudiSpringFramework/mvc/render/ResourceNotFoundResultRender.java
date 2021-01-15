package org.wudiSpringFramework.mvc.render;

import org.wudiSpringFramework.mvc.RequestProcessorChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源找不到时使用的    渲染器
 */
public class ResourceNotFoundResultRender implements ResultRender{
    private String httpMethod;
    private String httpPath;

    public ResourceNotFoundResultRender(String httpMethod, String httpPath) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                "获取不到对应的请求资源：请求路径[" + httpPath + "]" + "请求方法[" + httpMethod + "]");
    }
}
