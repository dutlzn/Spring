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

