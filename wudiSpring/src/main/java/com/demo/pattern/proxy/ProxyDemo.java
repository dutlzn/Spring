package com.demo.pattern.proxy;

import com.demo.pattern.proxy.cglib.AlipayMethodInterceptor;
import com.demo.pattern.proxy.cglib.CglibUtil;
import com.demo.pattern.proxy.jdkProxy.AlipayInvocationHandler;
import com.demo.pattern.proxy.jdkProxy.JdkDynamicProxyUtil;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;

/**
 * 代理 测试类
 */
public class ProxyDemo {
    public static void main(String[] args) {
//        静态代理
//       ToCPayment toCPayment =  new AlipayToC(new ToCPaymentImpl());
//       toCPayment.pay();
//       ToBPayment toBPayment = new AliPayToB(new ToBPaymentImpl());
//       toBPayment.pay();


//        Jdk动态代理
//       ToCPayment toCPayment =  new ToCPaymentImpl();
//       InvocationHandler handler = new AlipayInvocationHandler(toCPayment);
//       ToCPayment toCProxy = JdkDynamicProxyUtil.newProxyInstance(new ToCPaymentImpl(), new AlipayInvocationHandler(new ToCPaymentImpl()));
//       toCProxy.pay();
//       System.out.println("==============================================");
//       ToBPayment toBPayment = new ToBPaymentImpl();
//       InvocationHandler handler1 = new AlipayInvocationHandler(toBPayment);
//       ToBPayment toBProxy = JdkDynamicProxyUtil.newProxyInstance(toBPayment, handler1);
//       toBProxy.pay();


////        Cglib动态代理
//        CommonPayment commonPayment = new CommonPayment();
////        AlipayInvocationHandler alipayInvocationHandler = new AlipayInvocationHandler(commonPayment);
////        CommonPayment commonPaymentProxy = JdkDynamicProxyUtil.newProxyInstance(commonPayment, alipayInvocationHandler);
//        // 方法拦截器
//        MethodInterceptor methodInterceptor = new AlipayMethodInterceptor();
//        CommonPayment commonPaymentProxy = CglibUtil.creteProxy(commonPayment, methodInterceptor);
//        commonPaymentProxy.pay();

//        ToCPayment toCPayment = new ToCPaymentImpl();
//        ToCPayment proxy = CglibUtil.creteProxy(toCPayment, methodInterceptor);
//        proxy.pay();

        CommonPayment commonPayment = new CommonPayment();
        AlipayMethodInterceptor alipayMethodInterceptor = new AlipayMethodInterceptor();
        CommonPayment commonPaymentProxy = CglibUtil.creteProxy(commonPayment, alipayMethodInterceptor);
        commonPaymentProxy.pay();

//        ToCPayment toCPayment = new ToCPaymentImpl();
//        ToCPayment proxy = CglibUtil.creteProxy(toCPayment, new AlipayMethodInterceptor());
//        proxy.pay();
    }
}
