package com.demo.pattern.factory.abstractf;

import com.demo.pattern.factory.entity.*;

public class DellComputerFactory implements ComputerFactory {
    @Override
    public Mouse createMouse() {
        return new DellMouse();
    }

    @Override
    public KeyBoard createKeyboard() {
        return new DellKeyboard();
    }
}
