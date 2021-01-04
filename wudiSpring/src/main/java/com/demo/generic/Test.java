package com.demo.generic;

public class Test {
//    public static void handleMember(GenericClassExample<Integer> integerGenericClassExample) {
//        Integer result = 111 + integerGenericClassExample.getMember();
//        System.out.println("result is:" + result);
//    }

//    public static void handleMember(GenericClassExample<?> integerGenericClassExample) {
//        Integer result = 111 + (Integer)integerGenericClassExample.getMember();
//        System.out.println("result is:" + result);
//    }
//    public static void handleMember(GenericClassExample<? extends Number> integerGenericClassExample) {
//        Integer result = 111 + (Integer)integerGenericClassExample.getMember();
//        System.out.println("result is:" + result);
//    }
    public static void handleMember(GenericClassExample<? super Integer> integerGenericClassExample) {
        Integer result = 111 + (Integer)integerGenericClassExample.getMember();
        System.out.println("result is:" + result);
    }
    public static void main(String[] args) {
        GenericClassExample<String> g1 = new GenericClassExample<>("143");
        GenericClassExample<Integer> g2 = new GenericClassExample<>(1432);
        System.out.println(g1.getMember().getClass());
        System.out.println(g2.getMember().getClass());
        System.out.println(g1.getClass());
        System.out.println(g2.getClass());

        handleMember(g2);

    }
}
