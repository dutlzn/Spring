package com.demo.pattern.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 *  {@link} https://blog.csdn.net/jiankunking/article/details/52143504
 *  {@link} https://www.cnblogs.com/zuidongfeng/p/8735241.html
 */
public class JdkDynamicProxyUtil {
    public static <T> T newProxyInstance(T targetObject, InvocationHandler handler){
        ClassLoader classLoader = targetObject.getClass().getClassLoader();
        Class<?>[] interfaces = targetObject.getClass().getInterfaces();
        return (T)Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
