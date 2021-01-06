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



