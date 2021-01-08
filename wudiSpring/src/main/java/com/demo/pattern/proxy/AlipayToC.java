package com.demo.pattern.proxy;

public class AlipayToC implements ToCPayment{
    ToCPayment toCPayment;

    public AlipayToC(ToCPayment toCPayment) {
        this.toCPayment = toCPayment;
    }

    @Override
    public void pay() {
        beforePay();
        toCPayment.pay();
        afterPay();
    }

    private void afterPay() {
        System.out.println("打印交易信息");
    }

    private void beforePay() {
        System.out.println("从银行取款");
    }

}
