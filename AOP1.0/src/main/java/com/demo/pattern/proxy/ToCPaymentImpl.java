package com.demo.pattern.proxy;

public class ToCPaymentImpl implements ToCPayment {
    @Override
    public void pay() {
        System.out.println("以用户名义进行支付");
    }
}
