package com.demo.pattern.proxy;

public class AliPayToB implements ToBPayment{

    ToBPayment toBPayment;
    public AliPayToB(ToBPayment toBPayment) {
        this.toBPayment = toBPayment;
    }
    @Override
    public void pay() {
        beforePay();
        toBPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("打印交易信息");
    }

    private void beforePay() {
        System.out.println("从银行取款");
    }
}
