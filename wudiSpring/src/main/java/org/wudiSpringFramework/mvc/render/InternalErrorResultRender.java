package org.wudiSpringFramework.mvc.render;


import org.wudiSpringFramework.mvc.RequestProcessorChain;

import javax.servlet.http.HttpServletResponse;

/**
 * 内部异常渲染器
 * 没有执行
 */
public class InternalErrorResultRender implements ResultRender{
    // 错误信息
    private String errorMsg;
    public InternalErrorResultRender(String  errorMsg){
        this.errorMsg = errorMsg;
    }
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMsg);
    }
}