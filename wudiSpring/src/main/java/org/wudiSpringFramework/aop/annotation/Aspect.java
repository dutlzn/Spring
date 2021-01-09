package org.wudiSpringFramework.aop.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 当前被Aspect标记的横切逻辑 是会被织入进被属性值标记的那些类里
     * @return
     */
    Class<? extends Annotation> value();
}
