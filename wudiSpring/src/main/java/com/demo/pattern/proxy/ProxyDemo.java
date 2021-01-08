package com.demo.pattern.proxy;

import com.demo.pattern.proxy.jdkProxy.AlipayInvocationHandler;
import com.demo.pattern.proxy.jdkProxy.JdkDynamicProxyUtil;

import java.lang.reflect.InvocationHandler;

public class ProxyDemo {
    public static void main(String[] args) {
//        静态代理
//       ToCPayment toCPayment =  new AlipayToC(new ToCPaymentImpl());
//       toCPayment.pay();
//       ToBPayment toBPayment = new AliPayToB(new ToBPaymentImpl());
//       toBPayment.pay();


//        Jdk动态代理
       ToCPayment toCPayment =  new ToCPaymentImpl();
       InvocationHandler handler = new AlipayInvocationHandler(toCPayment);
       ToCPayment toCProxy = JdkDynamicProxyUtil.newProxyInstance(toCPayment, handler);
       toCProxy.pay();
       System.out.println("==============================================");
       ToBPayment toBPayment = new ToBPaymentImpl();
       InvocationHandler handler1 = new AlipayInvocationHandler(toBPayment);
       ToBPayment toBProxy = JdkDynamicProxyUtil.newProxyInstance(toBPayment, handler1);
       toBProxy.pay();


    }
}
