package org.wudiSpringFramework.mvc.render;

import com.google.gson.Gson;
import org.ietf.jgss.GSSContext;
import org.wudiSpringFramework.mvc.RequestProcessorChain;

import java.io.PrintWriter;


/**
 * Json渲染器
 */
public class JsonResultRender implements ResultRender{
    private Object jsonData;

    public JsonResultRender(Object result) {
        this.jsonData = result;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        // 设置响应头
        requestProcessorChain.getResponse().setContentType("application/json");
        requestProcessorChain.getResponse().setCharacterEncoding("UTF-8");
        // 响应流写入经过json格式化的处理结果

        try( PrintWriter writer = requestProcessorChain.getResponse().getWriter()) {
            Gson gson = new Gson();
            String str = gson.toJson(jsonData);
            writer.write(str);
            writer.flush();
        }
    }
}
