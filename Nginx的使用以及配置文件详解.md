---
title: Nginx的使用以及配置文件详解
date: 2019-07-24 14:34:16
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/97016425](https://blog.csdn.net/MOKEXFDGH/article/details/97016425)   
    
  ### 文章目录


      * [概述](#_3)
      * [nginx.conf 配置文件与反向代理](#nginxconf__17)
      * [负载均衡](#_119)
      * [HTTP服务器](#HTTP_170)  


 
--------
 
### []()概述

 Nginx (Engine x) 是一个高性能的HTTP和反向代理web服务器，同时也提供了IMAP/POP3/SMTP服务。

 主要用途：

  
  * **反向代理**  
     代理服务器接收请求，然后将请求转发给**内部网络**上的服务器，并将结果返回给客户端，此时代理服务器就作为一个反向代理服务器。 
  * **负载均衡**  
     当有2台或以上服务器时，根据规则随机的将请求分发到指定的服务器上处理，负载均衡配置一般都需要同时配置反向代理，通过反向代理跳转到负载均衡。 
  * **HTTP服务器（动静分离）**  
     动静分离是让动态网站里的动态网页根据一定规则把不变的资源和经常变的资源区分开来，而我们可以使用 Nginx 作为静态资源服务器。 
  * **正向代理**  
     代理服务器位于客户端和原始服务器之间，客户端向代理发送一个请求并指定目标(原始服务器)，然后代理向原始服务器**转交请求**并将获得的内容返回给客户端（DNS服务器、VPN等）。  
--------
 
### []()nginx.conf 配置文件与反向代理

 在我们安装完 nginx 后，在 /nginx/conf/ 目录下有一个 nginx 的配置文件 nginx.conf：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190723181307728.png)  
 其结构如下：

 
```
		#全局块
		...             
		#events块 
		events {         
		   ...
		}
		#http块
		http     
		{
		    #http全局块
		    ... 
		    #server块（多个）
		    server       
		    { 
		        #server全局块
		        ...
		        #location块（多个）
		        location [PATTERN]   
		        {
		            ...
		        }
		    }
		}

```
  
  * 全局块：配置影响nginx全局的指令。 
  * events块：配置影响nginx服务器或与用户的网络连接。 
  * http块：可以嵌套多个server，配置代理，缓存，日志定义等绝大多数功能和第三方模块的配置。 
  * server块：配置虚拟主机的相关参数。 
  * location块：配置请求的路由，以及各种页面的处理情况。  参考本人在阿里云中具体的配置文件：

 
```
		user  root;  #配置用户或者组，默认为nobody
		worker_processes  1;  #允许生成的进程数，默认为1

		#制定日志路径，级别：debug、info、notice、warn、error、crit、alert、emerg
		#error_log  logs/error.log; 
		#error_log  logs/error.log  notice;
		#error_log  logs/error.log  info;
		
		#pid  logs/nginx.pid; #指定nginx进程运行文件存放地址
		
		events {
		    worker_connections  1024;  #最大连接数，默认为512
		}
		
		http {
		    include       mime.types; #文件扩展名与文件类型映射表
		    default_type  application/octet-stream; #默认文件类型，指定默认处理的文件类型可以是二进制
		
		    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
		    #                  '$status $body_bytes_sent "$http_referer" '
		    #                  '"$http_user_agent" "$http_x_forwarded_for"';
		
		    #access_log  logs/access.log  main; #本虚拟主机的访问日志
		
		    sendfile        on;  #允许sendfile方式传输文件
		    #tcp_nopush     on;# 让nginx在一个数据包中发送所有的头文件，而不是一个一个单独发
		
		    #keepalive_timeout  0;
		    keepalive_timeout  65; #连接超时时间，默认为75s
		
		    #gzip  on; #是否开启静态文件压缩，加快加载速度
			error_page   500 502 503 504  /50x.html;  #错误页

			server {
		        listen       80;  #监听端口
		        server_name  moke.work; #指定ip地址或者域名
		
		        #charset koi8-r; #设置www/路径中配置的网页的默认编码格式
		
		        #access_log  logs/host.access.log  main; #定义本虚拟主机的访问日志
				
				#配置 php 解析
		        location ~ \.php$ { 
		            root  html;
		            fastcgi_pass   127.0.0.1:9000;
		            fastcgi_index  index.php;
		            fastcgi_param  SCRIPT_FILENAME  /var/www/html$fastcgi_script_name;
		            include        fastcgi_params;
		        }
		        
				#location xxx：对 xxx 启用代理
		        location / {
		           root  /var/www/html; #站点根目录，
		           index  index.html portal.php index.htm index.php info.php; #首页排序
		        }
		        location /hello/{
		             proxy_pass http://127.0.0.1:8080/; 
		        }
		        location /api{
		        	proxy_pass http://127.0.0.1:8080/api/index;
		        	proxy_cookie_path  /api/index  /api; #将/api/index的cookie输出到/api，避免session丢失
		        }
		}

```
 
--------
 
### []()负载均衡

 反向代理在我上面的配置文件中有例子，这里看下负载均衡和HTTP服务器的配置例子。

 负载均衡配置一般都需要同时配置反向代理，通过反向代理跳转到负载均衡。而Nginx目前支持自带3种负载均衡策略。

  
  2. RR  
      nginx的默认策略，每个请求按时间顺序逐一分配到不同的后端服务器，自动过滤无法访问的服务器。  
      在 nginx.conf 的http块中加入：  
```
		upstream  test  {
			server  localhost:8080;
			server  localhost:8081;
		}

```
  
  2. 权重  
      指定每个服务器的权重，权重越高接收的请求越多，用于后端服务器性能不均。  
```
		upstream  test  {
			server  localhost:8080 weight=10;
			server  localhost:8081 weight=9;
		}

```
  
  2. ip_hash  
      对于携带cookie的请求，我们可能需要保证一个会话只访问一个服务器（登录状态），就需要使用 ip_hash，解决 session 问题。  
```
		# upstream  用于进行负载均衡的配置
		upstream  test  {
			ip_hash;
			server  localhost:8080;
			server  localhost:8081;
		}

```
 我们还可以为配置的每个服务器设置状态值：

 
```
		upstream test {
		    ip_hash;
		    server  localhost:8080;
		    server  localhost:8081 down;
		    server  localhost:8082 max_fails=3;
		    server  localhost:8083 fail_timeout=20s;
		    server  localhost:8084 backup;
		    # 也可以配置多个状态值
		    server  localhost:8085 max_fails=3 fail_timeout=20s;
		}

```
  
  * down：表示该主机暂停服务 
  * max_fails：表示失败最大次数，若超过失败最大次数暂停服务 
  * fail_timeout：表示如果请求受理失败，暂停指定的时间之后重新发起请求 
  * backup：非backup机器 down 或者忙的时候，才会请求 backup 机器 
  * wegiht：权重也是设置状态值的一种  
--------
 
### []()HTTP服务器

 在上面的配置文件中，有如下配置：

 
```
		location / {
		           root  /var/www/html; #站点根目录，
		           index  index.html portal.php index.htm index.php info.php; #首页排序
		}

```
 如果我们远程浏览器访问 [http://外网ip](http://xn--ip-3s9cz36l)，就可以访问到 /var/www/html 目录下的 index 的页面文件，因此我们可以这样实现动静分离：

 
```
		# 使用正则表达式匹配不同的请求
		# 静态资源的访问交给 nginx 处理
		location ~ \.(gif | jpg | jpeg | png | bmp | css | js)$ { 
				root /var/resources;
		}
		# 动态资源的访问交给 tomcat 处理
		location ~ \.(jsp)$ {
				proxy_pass http://localhost:8080;
		}

```
 root 和 alias 的区别：root与alias主要区别在于nginx如何解释location配置的uri。

 
```
		location /a/ {
				root /data/work1/;
		}
		location /b/ {
		  		alias /data/work2/;
		}

```
  
  * 请求 url：[http://ip/a/1.jpg，](http://ip/a/1.jpg%EF%BC%8C) root 对应的 url 是 root 路径＋location 路径，即 [http://ip/data/work1/1.jpg](http://ip/data/work1/1.jpg) 
  * 请求 url：[http://ip/b/1.jpg，](http://ip/b/1.jpg%EF%BC%8C) alias 则是用 alias 的路径替换 location 路径，即 /b/ 相当于 /data/work2/ 
  * alias 后面必须要用 / 结束，否则会找不到文件，而 root 则可有可无    
  