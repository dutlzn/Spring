package com.demo.pattern.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CglibUtil {
    public static <T> T creteProxy(T targetObject, MethodInterceptor methodInterceptor) {
        return (T)Enhancer.create(targetObject.getClass(), methodInterceptor);
    }
}
