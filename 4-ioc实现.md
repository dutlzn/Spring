---
typora-root-url: /images
---

# IOC实现

## 自研框架IOC容器的使用



![](/26.png)



## 框架具备的最基本功能

* 解析配置
* 定位与注册对象
* 注入对象
* 提供通用的工具类

## IOC容器的实现

需要实现的点

![](/27.png)



# 创建注解



```
package org.wudiSpringFramework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
```



```
package org.wudiSpringFramework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
```



```
package org.wudiSpringFramework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {
}
```



```java
package org.wudiSpringFramework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
}
```



# 提取标记对象

实现思路：

指定范围，获取范围内的所有类

遍历所有类，获取被注解标记的类并加载进容器里

## extractPackageClass

* 获取到类的加载器
* 通过类加载器获取到加载的资源信息
* 依据不同的资源类型，采用不同的方式获取资源的集合

获取项目类加载器的目的：

获取项目发布的实际路径



为什么不让用户传入绝对路径：

不够友好，不同机器之间的路径可能不相同

如果打的是war包或者jar包，根本找不到路径

因此通用的做法是通过项目的类加载器来获取



## 类加载器ClassLoader

![](/28.png)

根据一个指定类的名称，找到或生成其对应的字节码

加载java应用所需要的资源



## 统一资源定位符 URL

某个资源的唯一地址

通过获取java.net.URL实例获取协议名、资源名路径等信息

![](/29.png)





```java
package org.wudiSpringFramework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {
    public static final String FILE_PROTOCOL = "file";
    /**
     * 获取包下类集合
     * @param packageName
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        // 1 获取到类加载器
        ClassLoader classLoader = getClassLoader();
        // 2 通过类加载器获取到加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unable to retrieve anything from package." + packageName);
            return null;
        }
        // 3 依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet  = null;
        // 过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<Class<?>>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件(包括子package里的class文件)
     *
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource    文件或者目录
     * @param packageName   包名
     * @return 类集合
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {        // 如果是文件
        if(!fileSource.isDirectory()){
            return ;
        }
        //如果是一个文件夹，则调用其listFiles方法获取文件夹下的文件或文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory()) {
                    return  true;
                } else {
                    // 获取文件绝对值路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if(absoluteFilePath.endsWith(".class")) {
                        // 若是class文件，则直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }
            //根据class文件的绝对值路径，获取并生成class对象，并放入classSet中
            private void addToClassSet(String absoluteFilePath) {
                //1.从class文件的绝对值路径里提取出包含了package的类名
                //如XX/XX/XX/com/spring/entity/dto/MainPageInfoDTO.class
                //需要弄成com.spring.entity.dto.MainPageInfoDTO
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                //2.通过反射机制获取对应的Class对象并加入到classSet里
                Class targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });

        if(files != null){
            for(File f : files){
                //递归调用
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }
    /**
     * 获取Class对象
     *
     * @param className class全名=package + 类名
     * @return Class
     */
    private static Class loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取classLoader
     * @return 当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        // 程序是通过线程来执行的
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        extractPackageClass("com.spring.entity");
    }


}

```





引入测试用的jar包

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>wudiSpring</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>

        <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>


        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.28</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.5.2</version>
            <scope>test</scope>
        </dependency>

    </dependencies>
    <build>
        <finalName>wudiSpring</finalName>
<!--        <pluginManagement>-->
            <plugins>
                <plugin>
                    <!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin -->
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.1</version>
                    <configuration>
                        <source>8</source>
                        <target>8</target>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.apache.tomcat.maven</groupId>
                    <artifactId>tomcat7-maven-plugin</artifactId>
                    <version>2.2</version>
                    <configuration>
                        <path>/${project.artifactId}</path>
                    </configuration>
                </plugin>
            </plugins>
<!--        </pluginManagement>-->
    </build>

</project>
```





# 单例模式Singleton Pattern

确保一个类只有一个实例，并对外提供统一访问方式

饿汉模式：类加载的时候就立即初始化并创建唯一实例 （ 线程安全）



懒汉模式：在被客户端首次调用的时候才创建唯一实例 



**加入双重检查锁机制的懒汉模式能确保线程安全**



实际上，可以通过反射修改单例模式中私有的构造函数，然后设置构造函数权限

就可以新new一个对象，打破了单例模式所谓的安全



# 无视反射和序列化攻击的单例



枚举的私有构造是可以抵挡住反射的攻击的

装备了枚举的饿汉模式能抵御反射与序列化的进攻，满足容器需求

去查看ObjectInputStream的readObject源码



# 容器的载体以及容器的加载

BeanContainer

用枚举类的饿汉模式来实现IOC容器



```java
package org.wudiSpringFramework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 私有的构造函数
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    /**
     * 获取Bean容器实例
     * @return BeanContainer
     */
    public static BeanContainer  getInstance() {
        return ContainerHolder.HOLDER.instance;
    }
    public enum ContainerHolder {
        HOLDER; // 用来存放单例
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }
}
```



## 实现容器

容器组成部分：

保存class对象机器实例的载体

容器的加载

容器的操作方式



## 实现容器的加载

配置的管理与获取

获取指定范围内的Class对象

依据配置提取Class对象，连同实例一并存入容器

```java
package org.wudiSpringFramework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {
    public static final String FILE_PROTOCOL = "file";
    /**
     * 获取包下类集合
     * @param packageName
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        // 1 获取到类加载器
        ClassLoader classLoader = getClassLoader();
        // 2 通过类加载器获取到加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unable to retrieve anything from package." + packageName);
            return null;
        }
        // 3 依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet  = null;
        // 过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<Class<?>>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        // TODO 此处可以加入针对其他类型资源的处理
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件(包括子package里的class文件)
     *
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource    文件或者目录
     * @param packageName   包名
     * @return 类集合
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {        // 如果是文件
        if(!fileSource.isDirectory()){
            return ;
        }
        //如果是一个文件夹，则调用其listFiles方法获取文件夹下的文件或文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory()) {
                    return  true;
                } else {
                    // 获取文件绝对值路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if(absoluteFilePath.endsWith(".class")) {
                        // 若是class文件，则直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }
            //根据class文件的绝对值路径，获取并生成class对象，并放入classSet中
            private void addToClassSet(String absoluteFilePath) {
                //1.从class文件的绝对值路径里提取出包含了package的类名
                //如XX/XX/XX/com/spring/entity/dto/MainPageInfoDTO.class
                //需要弄成com.spring.entity.dto.MainPageInfoDTO
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                //2.通过反射机制获取对应的Class对象并加入到classSet里
                Class targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });

        if(files != null){
            for(File f : files){
                //递归调用
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }
    /**
     * 获取Class对象
     *
     * @param className class全名=package + 类名
     * @return Class
     */
    private static Class loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取classLoader
     * @return 当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        // 程序是通过线程来执行的
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 实例化class
     *
     * @param clazz Class
     * @param <T>   class的类型
     * @param accessible   是否支持创建出私有class对象的实例
     * @return 类的实例化
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible){
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T)constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        extractPackageClass("com.spring.entity");
    }


}
```



```java
package org.wudiSpringFramework.util;

import java.util.Collection;
import java.util.Map;

public class ValidationUtil {
    /**
     * String是否为null或""
     *
     * @param obj String
     * @return 是否为空
     */
    public static boolean isEmpty(String obj) {
        return (obj == null || "".equals(obj));
    }

    /**
     * Array是否为null或者size为0
     *
     * @param obj Array
     * @return 是否为空
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }
    /**
     * Collection是否为null或size为0
     *
     * @param obj Collection
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> obj){
        return obj == null || obj.isEmpty();
    }
    /**
     * Map是否为null或size为0
     *
     * @param obj Map
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> obj) {
        return obj == null || obj.isEmpty();
    }
}
```





自己写的文件里使用controller 等自定义注解





# 提供容器对外操作的方法

涉及到容器的增删改查

增加、删除操作

根据Class获取对应实例

获取所有的Class和实例

通过注解来获取被注解标注的Class

通过超类获取对应的子类Class

获取容器载体保存Class的数量

```JAVA
package org.wudiSpringFramework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.core.annotation.Component;
import org.wudiSpringFramework.core.annotation.Controller;
import org.wudiSpringFramework.core.annotation.Repository;
import org.wudiSpringFramework.core.annotation.Service;
import org.wudiSpringFramework.util.ClassUtil;
import org.wudiSpringFramework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
// 私有的构造函数
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    /**
     * 存放所有被配置标记的目标对象的Map
     * Class对象----对应的实例
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();
    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class);


    /**
     * 获取Bean容器实例
     * @return BeanContainer
     */
    public static BeanContainer  getInstance() {
        return ContainerHolder.HOLDER.instance;
    }
    public enum ContainerHolder {
        HOLDER; // 用来存放单例
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 容器是否已经加载过bean
     */
    private boolean loaded = false;

    /**
     * 是否已加载过Bean
     *
     * @return 是否已加载
     */
    public boolean isLoaded() {
        return loaded;
    }



    /**
     * 扫描加载所有Bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        // 判断bean容器是否被加载过
        if(isLoaded()) {
            log.warn("BeanContainer has been loaded.");
            return ;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
//        if(classSet == null || classSet.isEmpty()) {
        if(ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName:" + packageName);
            return ;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                //如果类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    //将目标类本身作为键，目标类的实例作为值，放入到beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        loaded = true;
    }

    /**
     * Bean实例数量
     *
     * @return 数量
     */
    public int size() {
        return beanMap.size();
    }


    /**
     * 添加一个class对象及其bean实例
     * @param clazz Class对象
     * @param bean Bean实例
     * @return 原有的Bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除一个IOC容器管理的对象
     *
     * @param clazz Class对象
     * @return 删除的Bean实例, 没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     * @param clazz Class对象
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }


    /**
     * 获取容器管理的所有实例
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取容器管理的所有Bean集合（实例）
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注解筛选出Bean的Class集合
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation){
        //1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        //2.通过注解筛选被注解标记的class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : keySet){
            //类是否有相关的注解标记
            if(clazz.isAnnotationPresent(annotation)){
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0? classSet: null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     *
     * @param interfaceOrClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass){
        //1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        //2.判断keySet里的元素是否是传入的接口或者类的子类，如果是，就将其添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> clazz : keySet){
            //判断keySet里的元素是否是传入的接口或者类的子类
            if(interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)){
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0? classSet: null;
    }





}
```



## 容器管理的Bean实例

都是单例

对于spring来说 也有作用域

Scope(value="singleton")

## Spring框架有多种作用域

singleton

prototype

request

session

globalsession

# 实现容器的依赖注入

目前容器里面管理的bean实例仍然时不完备的

实例里面某些必须的成员变量还没有被创建出来



定义相关的注解标签

实现创建被注解标记的成员变量实例，并将其注入到成员变量里

依赖注入的使用

```java
package org.wudiSpringFramework.inject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowired目前仅支持成员变量注入
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}
```



