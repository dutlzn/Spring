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