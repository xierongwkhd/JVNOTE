---
title: MVC和前后端分离
date: 2019-08-05 17:53:23
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/98493433](https://blog.csdn.net/MOKEXFDGH/article/details/98493433)   
    
  _参考:[孤独烟](https://www.cnblogs.com/rjzheng/p/9185502.html)_

 
--------
 **早期JSP+SERVLET：**  
 也就是我们传统的 MVC 开发模式。  
 ![1](https://img-blog.csdnimg.cn/20190805155605964.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 缺点：

  
  * 视图依赖于模型，如果没有模型，视图也无法呈现出最终的效果； 
  * 在服务端完成视图的渲染，浏览器解析的是带有模型数据的视图（JSP）  
--------
 **前后端分离：**  
 使用较多的开发方式，基于 [RESTful](https://blog.csdn.net/MOKEXFDGH/article/details/87378554#RESTful_1165) 设计风格的后端提供相应的接口，浏览器发送AJAX请求，然后服务端接受该请求并将 JSON 数据返回给浏览器，页面解析 JSON 数据，通过 dom 操作渲染页面。  
 ![2](https://img-blog.csdnimg.cn/20190805160643838.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
--------
 **node.js**  
 因为没有参与过，所以这里只进行简单的了解。  
 node.js 可以作为一个中间层。所谓中间层就是在前端与后端之间加多一层。而这个主要是将原来由后端来做的页面动态化工作交给了前端来做，即：  
 前端：负责View和Controller层  
 后端：只负责Model层，业务处理/数据等  
 ![3](https://img-blog.csdnimg.cn/20190805162054788.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 node作为中间层，可以将页面所需要的多个后端数据（请求），直接在内网阶段就拼装好，再统一返回给前端，会得到更好的性能。  
 一般大公司才会使用，因为小公司没有这样的前端资源来支撑这样的架构，同时增加了一个接口制定流程和前后端联调流程，放慢了迭代周期，不利于中小公司的产品迭代。  
 不同于ajax方式：

  
  * ajax：前端只需要调用后端根据不同功能需求已经写好了的接口（后端编写的 Controller），在前端页面直接进行渲染。 
  * node：前端可以根据需求编写 node 调用后端若干接口实现相应的功能需求（此时 node 对于页面来说是 Controller 层），在页面调用 node 的接口（前端编写的 Controller）    
  