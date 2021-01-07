package com.org.wudiSpringFramework.core;

import org.junit.jupiter.api.*;
import org.wudiSpringFramework.core.BeanContainer;

public class BeanContainerTest {
    private static BeanContainer beanContainer;
    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @DisplayName("加载目标类及其实例到BeanContainer：loadBeansTest")
    @Order(1)
    @Test
    public void loadBeansTest(){
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("com.spring");
        Assertions.assertEquals(6, beanContainer.size());
        Assertions.assertEquals(true, beanContainer.isLoaded());
    }
}
