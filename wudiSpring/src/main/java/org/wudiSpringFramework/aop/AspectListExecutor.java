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
 *  创建MethodInterceptor 的实现类
 *  定义必要的成员变量， 被代理的类以及Aspect列表
 *  按照Order对Aspect进行排序
 *  实现对横切逻辑以及被代理对象方法的定序执行
 */
public class AspectListExecutor implements MethodInterceptor {
    // 被代理的类
    private Class<?> targetClass;
    // 排序好的Aspect列表
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
                // 按照order从小到大进行升序排序
                return o1.getOrderIndex() - o2.getOrderIndex();
            }
        });
        return aspectInfoList;
    }

    /**
     *
     * @param proxy 被代理的类
     * @param method 被代理的方法
     * @param args 参数
     * @param methodProxy
     * @return
     * @throws Throwable
     */
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
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e) {
            // 4 如果被代理方法抛出异常，则按照order的顺序降序执行玩所有Aspect的afterThrowing
            invokeAfterThrowingAdvides(method, args, e);

        }
       return returnValue;
    }

    // 4 如果被代理方法抛出异常，则按照order的顺序降序执行玩所有Aspect的afterThrowing
    private void invokeAfterThrowingAdvides(Method method, Object[] args, Exception e) throws Throwable {
        for(int i = sortedAspectInfoList.size() -1;i>=0;--i){
           sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }

    // 3 如果被代理方法正常返回，则按照order的顺序降序执行完所有Aspect的afterReturning
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for(int i = sortedAspectInfoList.size() -1;i>=0;--i){
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    // 1 按照order的顺序升序执行完所有的Aspect的before方法
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }


}
