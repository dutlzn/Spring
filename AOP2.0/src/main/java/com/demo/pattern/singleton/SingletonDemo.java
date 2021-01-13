package com.demo.pattern.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonDemo {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        System.out.println(StarvingSingleton.getInstance());
//        System.out.println(StarvingSingleton.getInstance());
//        System.out.println(LazyDoubleCheckSingleton.getInstance());
//        System.out.println(LazyDoubleCheckSingleton.getInstance());
//        更倾向于使用饿汉模式
//        单例模式真的安全吗？private真的可以private吗
//        System.out.println(StarvingSingleton.getInstance());
//        System.out.println(StarvingSingleton.getInstance());
//        Class clazz = StarvingSingleton.class;
//        Constructor constructor = clazz.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        // 反射可以破除private，所谓的线程安全不适合于反射场景下
//        System.out.println(constructor.newInstance());

        System.out.println(EnumStarvingSingleton.getInstance());
        Class clazz = EnumStarvingSingleton.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        EnumStarvingSingleton enumStarvingSingleton = (EnumStarvingSingleton)constructor.newInstance();
        System.out.println(enumStarvingSingleton.getInstance());
    }
}