package com.demo.pattern.factory.method;

import com.demo.pattern.factory.entity.HpMouse;
import com.demo.pattern.factory.entity.Mouse;

public class HpMouseFactory implements MouseFactory{
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }
}
