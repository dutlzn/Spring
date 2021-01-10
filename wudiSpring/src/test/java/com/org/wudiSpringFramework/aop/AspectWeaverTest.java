package com.org.wudiSpringFramework.aop;

import com.spring.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.wudiSpringFramework.aop.AspectWeaver;
import org.wudiSpringFramework.core.BeanContainer;
import org.wudiSpringFramework.inject.DependencyInjector;

public class AspectWeaverTest {
    @DisplayName("织入通用逻辑测试：doAop")
    @Test
    public void doAopTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.spring");
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
        HeadLineOperationController bean = (HeadLineOperationController) beanContainer.getBean(HeadLineOperationController.class);
        bean.addHeadLine(null, null);
    }
}
