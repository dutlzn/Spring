package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.*;

public class HpComputerFactory implements ComputerFactory {
    @Override
    public Mouse createMouse() {
        return new HpMouse();
    }

    @Override
    public KeyBoard createKeyboard() {
        return new HpKeyboard();
    }
}
