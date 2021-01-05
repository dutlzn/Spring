package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.KeyBoard;
import com.demo.pattern.factory.entity.Mouse;

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        ComputerFactory cf = new HpComputerFactory();
        Mouse mouse = cf.createMouse();
        KeyBoard keyBoard = cf.createKeyboard();
        mouse.sayHi();
        keyBoard.sayHello();
    }
}
