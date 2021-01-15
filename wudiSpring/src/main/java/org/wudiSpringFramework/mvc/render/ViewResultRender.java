package org.wudiSpringFramework.mvc.render;

import org.wudiSpringFramework.mvc.RequestProcessorChain;
import org.wudiSpringFramework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 页面渲染器
 */
public class ViewResultRender implements ResultRender{
    private static final String VIEW_PATH = "/templates/";

    private ModelAndView modelAndView;
    public ViewResultRender(Object result) {
        // 1 如果入参类型是ModelAndView 则直接赋值给成员变量
        if(result instanceof ModelAndView) {
            this.modelAndView = (ModelAndView)result;
        }
        // 2 如果入参类型是String 则为视图，需要包装后才赋值给成员变量
        else if (result instanceof String) {
            this.modelAndView = new ModelAndView().setView((String)result);
        }
        // 3 针对其他情况，则直接抛出异常
        else {
            throw new RuntimeException("illegal request result type");
        }

    }

    /**
     * 将请求处理结果按照视图路径转发至对应视图进行展示
     */
    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest req = requestProcessorChain.getRequest();
        HttpServletResponse resp = requestProcessorChain.getResponse();
        // 视图路径
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        for(Map.Entry<String, Object> entry : model.entrySet()){
            req.setAttribute(entry.getKey(), entry.getValue());
        }
        // jsp
        req.getRequestDispatcher(VIEW_PATH + path).forward(req, resp);
    }
}
