package com.demo.pattern.proxy;

public class ProxyDemo {
    public static void main(String[] args) {
       ToCPayment toCPayment =  new AlipayToC(new ToCPaymentImpl());
       toCPayment.pay();
       ToBPayment toBPayment = new AliPayToB(new ToBPaymentImpl());
       toBPayment.pay();
    }
}
