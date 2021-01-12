---
typora-root-url: /images
---

# AOP功能实现

# 容器是OOP的高级工具

以低耦合低侵入的方式打通从上到下的开发通道

按部就班填充代码逻辑实现业务功能，每层逻辑都无缝替换

OOP将业务程序分解成各个层次的对象，通过对象联动完成业务

无法很好地处理分散在各业务里的通用系统需求



系统需求：

添加日志信息，为每个方法添加统计时间

添加系统权限校验，针对某些方法进行限制

OOP下必须得为每个方法都添加通用的逻辑工作，增加维护成本



## 关注点分离 Concern Separation

不同的问题交给不同的部分去解决，每部分专注解决自己的问题

Aspect Oriented Programming就是其中一种关注点分离技术

通用化功能的代码实现即切面Aspect

Aspect之于AOP，就相当于Class之于OOP Bean之于Spring

![](/30.png)





打开spring源代码

spring-demo

```
plugins {
    id 'java'
}

group 'org.springframework'
version '5.2.0.RELEASE'

repositories {
    mavenCentral()
}

dependencies {
    compile(project(":spring-context"))
    compile(project(":spring-aspects"))
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```



 

# AOP重要术语

切面Aspect： 将横切关注点逻辑进行模块化封装的实体对象

通知Advice：好比是Class里面的方法，还定义了织入逻辑的时机

连接点： JointPoint 允许使用Advice的地方

SpringAOP默认只支持方法级别的JointPoint

切入点： PointCut 定义一系列规则对JointPoint进行筛选

目标对象Target：符合PointCut条件，要被织入横切逻辑的对象

## Advice种类

BeforeAdvice： 在JoinPoint前被执行的Advice

AfterAdvice: 好比try...catch..finally 中的finally
AfterReturningAdvice: 在JointPoint执行流程正常返回后被执行

 AfterThrowingAdvice: JointPoint执行过程中抛出异常才会触发

AroundAdvice: 在JointPoint前和后都执行，最常用的Advice

## 单个Aspect的执行顺序

![](/31.png)



## 多个Aspect的执行顺序

![](/32.png)



所有Aspect的入操作都执行完了 然后xxx



## Introduction - 引入型 Advice

为目标类引入新接口，而不需要目标类做任何实现

使得目标类在使用的过程中转型成新街口对象，调用新街口的方法





AOP需要oop理解自己的语义，所以并不像单独使用这么灵活

织入： 将Aspect模块化的横切关注点集成到OOP里

织入器： 完成织入过程的执行者，如ajc 

Spring AOP则会使用一组类来作为织入器以完成最终的织入操作



# Spring AOP的实现原理

## 代理模式

![](/33.png)



具体代码见wudiSpring demo pattern 

 

# Spring AOP的实现

在加载器阶段主要完成几件事情：

通过带有包名的类来获取对应class文件的二进制字节流

根据读取的字节流，将代表的静态存储结构转化为运行时数据结构

在内存中生成一个代表该类的Class对象，作为方法区该类的数据访问入口



## 改进的切入点

根据一定规则去改动或者生成新的字节流，将切面逻辑织入其中

行之有效的方案就是取代被代理类的动态代理机制

根据接口或者目标类，计算出代理类的字节码并加载到jvm中

 



# SpringAOP 之JDK动态代理 

* 程序运行时动态生成类的字节码 ，并加载到JVM中
* 要求被代理的类，必须实现接口
* 并不要求代理对象去实现接口，所以可以复用代理对象的逻辑



大概流程

为借口创建代理类的字节码文件

使用ClassLoader将字节码文件加载到JVM

创建代理类实例对象，执行对象的目标方法



代理对象：增强后的对象

目标对象：被增强的对象

https://zhuanlan.zhihu.com/p/60219913

https://www.cnblogs.com/yeyang/p/10087293.html

问题：

\1. 这个代理对象是由谁且怎么生成的？

Proxy getProxyClass0()

\2. invoke方法是怎么调用的？

\3. invoke和add方法有什么对应关系？

\4. 生成的代理对象是什么样子的



# Spring AOP之CLIGB动态代理



代码生成库：Code Generation Library

不要求被代理类实现接口

内部主要封装了ASM Java字节码操控框架

动态生成子类以覆盖非final的方法，绑定钩子回调自定义拦截器

重要的类

```
MethodInterceptor
```

```
public class Enhancer extends AbstractClassGenerator
```



## 实现机制

JDK动态代理： 基于反射机制实现，要求业务类必须实现接口

优势：JDK原生，在jvm里运行较为可靠

平滑支持jdk版本的升级

CGLIB：基于ASM机制实现，生成业务类的子类作为代理类

优势：被代理对象无需实现接口，能实现代理类的无侵入



**Spring AOP底层机制**

CGLIB+JDK动态代理 共存

默认策略：Bean实现了接口则用JDK ，否则使用CGLIB



# WudiSpring Aop实现



AOP1.0

使用CGLIB来实现，不需要业务类实现接口，相对灵活

解决标记的问题，定义横切逻辑的骨架

定义Aspect横切逻辑以及被代理方法的执行顺序

将横切逻辑织入到被代理的对象以生成动态代理对象



## 实现Aspect横切逻辑以及被代理方法的定序执行

创建MethodInterceptor的实现类

定义必要的成员变量---被代理的类以及Aspect列表

按照Order对Aspect进行排序

实现对横切逻辑以及被代理对象方法的定序执行



需要改进的地方：

Aspect只支持对被某个标签标记的类进行横切逻辑的织入

需要AspectJ的帮助



## 



## Spring AOP的发展史

Spring AOP1.0完全是Spring自己研发的

使用起来不方便，需要实现各种各样的接口，并继承指定的类

Spring AOP2.0继承了AspectJ，复用AspectJ的语法树



## AspectJ

提供了完整的AOP解决方案，是AOP的java实现版本

定义了切面语法以及切面语法的解析机制

提供了强大的织入工具

 ![](/34.png)

**所以AspectJ是AOP的一套完整的解决方案**





```
/** Class, interface (including annotation type), or enum declaration */
TYPE,

/** Field declaration (includes enum constants) */
FIELD,

/** Method declaration */
METHOD,
```





AspectJ官网：

http://www.eclipse.org/aspectj/

AspectJ框架的织入时机：静态织入和LTW

编译时织入： 利用ajc，将切面逻辑织入到类里生成class文件

编译后织入：利用ajc，修改javac编译出来的class文件

类加载期织入：利用java agent ，在类加载的时候织入切面逻辑

对于Spring AOP2.0，仅仅用到了AspectJ的切面语法，并没有用到ajc编译工具

避免增加用户的学习成本

只是默认不适用，如果还想使用ajc还是可以使用的

织入机制沿用自己的CGLIB和jdk动态代理机制



## 改造



改造框架：

让Pointcut更加灵活

只需要引入AspectJ的切面表达式和相关的定位解析机制

PointcutParser

PointcutExpression



# 复习

这块比前面IOC难很多

uml

https://www.pianshen.com/article/40021538722/

https://astah-community.software.informer.com/



## Cglib动态代理实例

Cglib

**AlipayMethodInterceptor(MethodInterceptor):**

@Override 

intercept(Object obj, Method method, Object[] args, MethodProxy proxy):

```
beforePay();
Object result = proxy.invokeSuper(obj, args);
afterPay();
return result;
```

**CglibUtil**

createProxy(T targetObject, MethodIntercept methodIntercept) :

```
(T)Enhancer.create(targetObject.getClass(), methodInterceptor);
```



```java
// Cglib动态代理
CommonPayment commonPayment = new CommonPayment();
// AlipayInvocationHandler alipayInvocationHandler = new AlipayInvocationHandler(commonPayment);
// CommonPayment commonPaymentProxy = JdkDynamicProxyUtil.newProxyInstance(commonPayment, alipayInvocationHandler);
// 方法拦截器
MethodInterceptor methodInterceptor = new AlipayMethodInterceptor();
CommonPayment commonPaymentProxy = CglibUtil.creteProxy(commonPayment, methodInterceptor);
commonPaymentProxy.pay();

ToCPayment toCPayment = new ToCPaymentImpl();
ToCPayment proxy = CglibUtil.creteProxy(toCPayment, methodInterceptor);
proxy.pay();
```

















