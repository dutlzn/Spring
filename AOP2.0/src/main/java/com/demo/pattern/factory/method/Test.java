package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.Mouse;

public class Test {
    public static void main(String[] args) {
        MouseFactory mf = new HpMouseFactory();
        mf.createMouse().sayHi();
    }
}
