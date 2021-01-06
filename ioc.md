---
typora-root-url: /images
---

# IOC容器

# 简单工厂模式

```
if (req.getServletPath().equals("/frontend/getmainpageinfo") && req.getMethod() == "GET"){
    System.out.println("ok");
    new MainPageController().getMainPageInfo(req, resp);
} else if(req.getServletPath() == "/superadmin/addheadline" && req.getMethod() == "POST"){
    new HeadLineOperationController().addHeadLine(req, resp);
}
```

**定义一个工厂类，根据传入的参数的值不同返回不同的实例**

特点： 被创建的实例具有共同的父类或接口



![](/14.png)



demo里面pattern里的代码



使用场景：

* 需要创建的对象比较少
* 客户端不关心对象的创建过程

优缺点：

优点：可以对创建的对象进行加工，对客户隐藏相关细节

缺点：因创建逻辑复杂或创建对象过多，而造成代码臃肿

缺点：新增、删除子类均会违反开闭原则



## 开闭原则

**一个软件实体，应该对扩展开放，对修改关闭**

应该通过扩展来实现变化，而不是通过修改已有的代码来实现变化

多一个实体类或者其他什么东西，需要新增条件判断，这就改变了原先的代码





# 工厂方法模式

**定义一个用于创建对象的接口，让子类决定实例化哪一个类**

对象的实例化延迟到了其子类

![](/15.png)

D:\code\Spring\wudiSpring\src\main\java\com\demo\pattern\factory\method



优点：

**遵循开闭原则**

对客户端隐藏对象的创建细节

遵循单一职责

单一职责：每个工厂类对应一个产品



简单工厂模式的进一步抽象和扩展，但是也有不足

* 添加子类的时候，拖家带口
* 只支持同一类产品的创建





# 抽象工厂模式

**提供一个创建一系列相关或相关依赖对象的接口**

抽象工厂模式侧重的是同一个产品族

工厂方法模式更加侧重的是同一个产品等级

![](/16.png)





解决了工厂模式只支持生产一种产品的弊端

新增一个产品族，只需要增加一个新的具体工厂，不需要修改代码

对工厂模式进行了抽象

工厂方法模式：

每个抽象产品派生多个具体产品类，每个抽象工厂类派生多个具体工厂类，每个具体工厂类负责一个具体产品的实例创建

抽象工厂模式:

每个抽象产品派生多个具体产品类，每个抽象工厂派生多个具体工厂类，每个具体工厂负责一系列具体产品的实例创建



当更多类型的Controller被加入到工程时，

添加新产品时， 依旧违背开闭原则，增加系统复杂度

Spring：结合了工厂模式和反射机制的Spring IOC容器来解决



# 反射

允许程序在运行时来进行自我检查并且对内部的成员进行操作



反射主要是指程序可以访问，检测 和修改它本身状态或行为的一种能力，并能根据自身行为的状态和结果，

调整或修改应用所描述行为的状态和相关的语义



反射机制的作用：

* 在运行时判断任意一个对象所属的类
* 在运行时获取类的对象
* 在运行时候访问java对象的属性、方法、构造方法等



java.lang.reflect类库里面主要的类

Field：表示类中的成员变量

Method：表示类中的方法

Constructor：表示类的构造方法

Array：该类提供了动态创建数组和访问数组元素的静态方法

## 反射依赖的Class

用来表示运行时类型信息的对应类

Class类的特点：

Class类也是类的一种，class则是关键字

Class类只有一个私有的构造函数，只有JVM能够创建Class类的实例

JVM中只有唯一一个和类相对应的Class对象来描述其类型信息



### 获取Class对象的三种方式

Object ----> getClass()

任何数据类型 （包括基本数据类型）都有一个“静态”的Class属性

通过Class类的静态方法：forName(String className) 



 demo reflect 包



```java
package com.demo.reflect;

public class ReflectTarget {
    public static void main(String[] args) throws ClassNotFoundException {
        // 第一种方式获取Class对象
        ReflectTarget reflectTarget = new ReflectTarget();
        Class c1 = reflectTarget.getClass();
        System.out.println(c1.getName());
        // 第二种方式获取Class对象
        Class c2 = ReflectTarget.class;
        System.out.println(c2.getName());
        System.out.println(c1 == c2);
        // 第三种方式获取Class对象
        Class c3 = Class.forName("com.demo.reflect.ReflectTarget");
        System.out.println(c3.getName());

    }
}
```



在运行期间，一个类，只有一个与之相对应的Class对象产生



Class对象就像一面镜子，透过这面镜子可以看到**类的结构**



## 反射的主要用法

* 获取类的构造方法
* 获取类的成员变量
* 获取类的成员方法





### 获取类的构造方法

```java
package com.demo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * 通过Class对象可以获取某个类中的：构造方法；
 *
 * 获取构造方法：
 *         1).批量的方法：
 *             public Constructor[] getConstructors()：所有"公有的"构造方法
              public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)
 *         2).获取单个的方法，并调用：
 *             public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
 *             public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
 *
 *             调用构造方法：
 *             Constructor-->newInstance(Object... initargs)
 */
public class ConstructorCollector {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName("com.demo.reflect.ReflectTarget");
        //1.获取所有的公有构造方法
        System.out.println("**********************所有公有构造方法*********************************");
        Constructor[] conArray = clazz.getConstructors();
        for(Constructor c : conArray){
            System.out.println(c);
        }
        //2.获取所有构造方法
        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
        conArray = clazz.getDeclaredConstructors();
        for(Constructor c : conArray){
            System.out.println(c);
        }
        //3.获取单个带参数的公有方法
        System.out.println("*****************获取公有、有两个参数的构造方法*******************************");
        Constructor con = clazz.getConstructor(String.class, int.class);
        System.out.println("con = " + con);
        //4.获取单个私有的构造方法
        System.out.println("******************获取私有构造方法*******************************");
        con = clazz.getDeclaredConstructor(int.class);
        System.out.println("private con = " + con);
        System.out.println("******************调用私有构造方法创建实例*******************************");
        //暴力访问（忽略掉访问修饰符）
        con.setAccessible(true);
        ReflectTarget reflectTarget = (ReflectTarget) con.newInstance(1);
    }
}
```





### 获取类的成员变量

```java
package com.demo.reflect;

/*
 * 获取成员变量并调用：
 *
 * 1.批量的
 *      1).Field[] getFields():获取所有的"公有字段"
 *      2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
 * 2.获取单个的：
 *      1).public Field getField(String fieldName):获取某个"公有的"字段；
 *      2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
 *
 *   设置字段的值：
 *      Field --> public void set(Object obj,Object value):
 *                  参数说明：
 *                  1.obj:要设置的字段所在的对象；
 *                  2.value:要为字段设置的值；
 *
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class FieldCollector {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取Class对象
        Class reflectTargetClass = Class.forName("com.demo.reflect.ReflectTarget");
        //1.获取所有公有的字段
        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = reflectTargetClass.getFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }
        //2.获取所有的字段
        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
        fieldArray = reflectTargetClass.getDeclaredFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }
        //3.获取单个特定公有的field
        System.out.println("*************获取公有字段并调用***********************************");
        Field f = reflectTargetClass.getField("name");
        System.out.println("公有的field name : " + f);
        ReflectTarget reflectTarget = (ReflectTarget)reflectTargetClass.getConstructor().newInstance();
        //4.给获取到的field赋值
        f.set(reflectTarget, "待反射一号");
        //5.验证对应的值name
        System.out.println("验证name : " + reflectTarget.name);
        //6.获取单个私有的Field
        System.out.println("**************获取私有字段targetInfo并调用********************************");
        f = reflectTargetClass.getDeclaredField("targetInfo");
        System.out.println(f);
        f.setAccessible(true);
        f.set(reflectTarget, "13810592345");
        System.out.println("验证信息" + reflectTarget);
    }
}
```



### 获取类的成员方法

```java
package com.demo.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * 获取成员方法并调用：
 *
 * 1.批量的：
 *      public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
 *      public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
 * 2.获取单个的：
 *      public Method getMethod(String name,Class<?>... parameterTypes):
 *                  参数：
 *                      name : 方法名；
 *                      Class ... : 形参的Class类型对象
 *      public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
 *
 *   调用方法：
 *      Method --> public Object invoke(Object obj,Object... args):
 *                  参数说明：
 *                  obj : 要调用方法的对象；
 *                  args:调用方式时所传递的实参；
):
 */
public class MethodCollector {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //1、获取Class对象
        Class reflectTargetClass = Class.forName("com.demo.reflect.ReflectTarget");
        //2、获取所有公有方法
        System.out.println("***************获取所有的public方法，包括父类和Object*******************");
        Method[] methodArray = reflectTargetClass.getMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        //3、获取该类的所有方法
        System.out.println("***************获取所有的方法，包括私有的*******************");
        methodArray = reflectTargetClass.getDeclaredMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        //4、获取单个公有方法
        System.out.println("***************获取公有的show1()方法*******************");
        Method m = reflectTargetClass.getMethod("show1", String.class);
        System.out.println(m);
        //5、调用show1并执行
        ReflectTarget reflectTarget = (ReflectTarget)reflectTargetClass.getConstructor().newInstance();
        m.invoke(reflectTarget, "待反射方法一号");
        //6、获取一个私有的成员方法
        System.out.println("***************获取私有的show4()方法******************");
        m = reflectTargetClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);
        String result = String.valueOf(m.invoke(reflectTarget, 20));
        System.out.println("返回值 ： " + result);
    }
}
```



# 必知必会的注解

## 反射的获取源

用XML来保存类相关的信息以供反射调用

用注解来保存类相关的信息一共反射调用

## 注解

提供一种为程序元素设置元数据的方法

* 元数据是添加到程序元素如方法、字段、类和包上的额外信息
* 注解是一种分散式的元数据设置方式，XML是集中式的设置方式
* 注解不能直接干扰程序代码的运行



反编译 自定义注解

```java
package com.demo.ann;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface TestAnnotation {
}
```



javac TestAnnotation

javap -verbose TestAnnotation.class

D:\code\Spring\wudiSpring\src\main\java\com\demo\ann>javap -verbose TestAnnotation.class
Classfile /D:/code/Spring/wudiSpring/src/main/java/com/demo/ann/TestAnnotation.class
  Last modified 2021-1-5; size 393 bytes
  MD5 checksum fca8e9572a9f73442429ee0510d01647
  Compiled from "TestAnnotation.java"
**public interface com.demo.ann.TestAnnotation extends java.lang.annotation.Annotation**
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_INTERFACE, ACC_ABSTRACT, ACC_ANNOTATION
Constant pool:
   #1 = Class              #14            // com/demo/ann/TestAnnotation
   #2 = Class              #15            // java/lang/Object
   #3 = Class              #16            // java/lang/annotation/Annotation
   #4 = Utf8               SourceFile
   #5 = Utf8               TestAnnotation.java
   #6 = Utf8               RuntimeVisibleAnnotations
   #7 = Utf8               Ljava/lang/annotation/Target;
   #8 = Utf8               value
   #9 = Utf8               Ljava/lang/annotation/ElementType;
  #10 = Utf8               METHOD
  #11 = Utf8               Ljava/lang/annotation/Retention;
  #12 = Utf8               Ljava/lang/annotation/RetentionPolicy;
  #13 = Utf8               SOURCE
  #14 = Utf8               com/demo/ann/TestAnnotation
  #15 = Utf8               java/lang/Object
  #16 = Utf8               java/lang/annotation/Annotation
{
}
SourceFile: "TestAnnotation.java"
RuntimeVisibleAnnotations:
  0: #7(#8=[e#9.#10])
  1: #11(#8=e#12.#13)\



用到了annotation类



### 注解的功能

作为特定的标记，用于告诉编译器一些信息

编译时动态处理，如动态生成代码

运行时动态处理，作为额外信息的载体，如获取注解信息



### 注解的分类

标准注解：Override Deprecated SuppressWarnings

元注解： Retention Target Inherited Documented

自定义注解



元注解：修饰注解的注解、

Target 注解的作用目标

Retention：注解的生命周期

Documented 注解是否应当被包含在JavaDoc文档中

Inherited : 是否允许子类继承该注解

## Target

描述所修饰的注解的使用范围

packages types (类、接口、枚举、Annotation类型)

类型成员（方法、构造方法、成员变量、枚举值）

方法参数和本地变量（如循环变量、catch参数）

RequestParam--->ElementType.PARAMETER



@Retention 

标注注解被保留时间的长短

用于定义注解的生命周期



# 自定义注解

自定义注解 自动继承了Annotation

自定义注解的格式

![](/17.png)

## 注解属性支持的类型

int float boolean byte double char long short

所有基本数据类型

Enum类型

String 类型

Class类型

Annotation类型

以上所有类型的数组



```
//反射获取
@Retention(RetentionPolicy.RUNTIME)
```