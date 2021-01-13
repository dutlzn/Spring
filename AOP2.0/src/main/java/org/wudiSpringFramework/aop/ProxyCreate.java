package org.wudiSpringFramework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyCreate {
    /**
     * 创建动态代理对象并返回
     * @param targetClass 被代理的Class对象
     * @param methodInterceptor 方法拦截器
     * @return
     */
    public static Object createProxy(Class<?> targetClass, MethodInterceptor methodInterceptor) {
        return Enhancer.create(targetClass, methodInterceptor);
    }
}
