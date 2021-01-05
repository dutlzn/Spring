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