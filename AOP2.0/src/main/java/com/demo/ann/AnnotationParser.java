package com.demo.ann;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {
    // 解析类的注解
    public static void parserTypeAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("com.demo.ann.CourseTest");
        // 这里获取的是Class对象的注解，而不是其里面的方法和成员变量的注解
        Annotation[] annotations = clazz.getAnnotations();
        for(Annotation annotation : annotations){
            CourseInfoAnnotation courseInfoAnnotation =  (CourseInfoAnnotation) annotation;
            System.out.println("课程名:" + courseInfoAnnotation.courseName() + "\n" +
                    "课程标签：" + courseInfoAnnotation.courseTag() + "\n" +
                    "课程简介：" + courseInfoAnnotation.courseProfile() + "\n" +
                    "课程序号：" + courseInfoAnnotation.courseIndex() );
        }

    }

    // 解析成员变量上的标签
    public static void parserFieldAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("com.demo.ann.CourseTest");
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            //判断成员变量中是否有指定注解类型的注解
            boolean hasAnnotation = f.isAnnotationPresent(PersonInfoAnnotation.class);
            if(hasAnnotation){
                PersonInfoAnnotation personInfoAnnotation = f.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("名字：" + personInfoAnnotation.name() + "\n" +
                        "年龄：" + personInfoAnnotation.age() + "\n" +
                        "性别：" + personInfoAnnotation.gender() + "\n");
                for(String language : personInfoAnnotation.language()){
                    System.out.println("开发语言：" + language);
                }

            }
        }
    }

    //解析方法注解
    public static void parseMethodAnnotation() throws ClassNotFoundException{
        Class clazz = Class.forName("com.demo.ann.CourseTest");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            /*
             * 判断方法中是否有指定注解类型的注解
             */
            boolean hasAnnotation = method.isAnnotationPresent(CourseInfoAnnotation.class);
            if(hasAnnotation){
                CourseInfoAnnotation courseInfoAnnotation = method.getAnnotation(CourseInfoAnnotation.class);
                System.out.println("课程名：" + courseInfoAnnotation.courseName() + "\n" +
                        "课程标签：" + courseInfoAnnotation.courseTag() + "\n" +
                        "课程简介：" + courseInfoAnnotation.courseProfile() + "\n"+
                        "课程序号：" + courseInfoAnnotation .courseIndex() + "\n");
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        parserTypeAnnotation();
        parserFieldAnnotation();
//        parseMethodAnnotation();
    }
}
