package com.demo.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// 类 + 方法
@Target({ElementType.TYPE, ElementType.METHOD})
//反射获取
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseInfoAnnotation {
    public String courseName();
    public String courseTag();
    public String courseProfile();
    public int courseIndex() default 303;
}
