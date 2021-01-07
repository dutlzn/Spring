package com.demo.pattern.singleton;

public class StarvingSingleton {
    /**
     * final变量 ： 一经初始化不能被改变
     */
    private static final StarvingSingleton starvingSingleton = new StarvingSingleton();
    private StarvingSingleton() {};
    public static StarvingSingleton getInstance() {
        return starvingSingleton;
    }

    public static void main(String[] args) {
        System.out.println(StarvingSingleton.getInstance());
        System.out.println(StarvingSingleton.getInstance());

    }
}
