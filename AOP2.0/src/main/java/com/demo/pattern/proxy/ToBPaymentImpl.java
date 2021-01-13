package com.demo.pattern.proxy;

public class ToBPaymentImpl implements ToBPayment {
    @Override
    public void pay() {
        System.out.println("以公司名义进行支付");
    }
}
