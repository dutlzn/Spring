package com.spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.aop.annotation.Aspect;
import org.wudiSpringFramework.aop.annotation.Order;
import org.wudiSpringFramework.aop.aspect.DefaultAspect;
import org.wudiSpringFramework.core.annotation.Controller;

import java.lang.reflect.Method;

@Slf4j
@Aspect(value = Controller.class)
@Order(10)
public class ControllerRecordAspect extends DefaultAspect {
    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        log.info("开始计时，执行的类【{}】，执行的方法是【{}】，参数是【{}】",
                targetClass.getName(), method.getName(), args);
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        log.info("结束计时，执行的类【{}】，执行的方法是【{}】，参数是【{}】，返回值是【{}】",
                targetClass.getName(), method.getName(), args, returnValue);
        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
        log.info("结束计时，执行的类【{}】，执行的方法是【{}】，参数是【{}】，异常是【{}】",
                targetClass.getName(), method.getName(), args, e.getMessage());;
    }
}
