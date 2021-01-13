---
typora-root-url: /images
---



# MVC实现

# 思路

**实现DispatcherServlet**

解析请求路径和请求方法

依赖容器，建立并维护Controller方法与请求的映射

用合适的Controller方法去处理特定的请求

MVC架构草图

![](/36.png)





只实现 get 和 post 方法的 http 请求

PreRequestProcessor 请求的编码和预处理





# DispatcherServlet的重构

