package org.wudiSpringFramework.aop;

import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.wudiSpringFramework.aop.aspect.AspectInfo;
import org.wudiSpringFramework.util.ValidationUtil;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 往被代理的对象里，添加横切逻辑
 */
public class AspectListExecutor implements MethodInterceptor {
    // 被代理的类
    private Class<?> targetClass;
    @Getter
    private List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    /**
     * 按照order值进行升序排序，保证order值小的aspect先被织入
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        Collections.sort(aspectInfoList, new Comparator<AspectInfo>() {
            @Override
            public int compare(AspectInfo o1, AspectInfo o2) {
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnValue = null;
        if(ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return null;
        }
        // 1 按照order的顺序升序执行完所有Aspect的before方法
        invokeBeforeAdvices(method, args);
        try {
            // 2 执行被代理类的方法
            returnValue = methodProxy.invokeSuper(proxy, args);
            // 3 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning
            invokeAfterReturningAdvices(method, args, returnValue);
        }
  // 4 如果被代理方法抛出异常，则按照order的顺序降序执行玩所有Aspect的afterThrowing

       return returnValue;
    }
}
