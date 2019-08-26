---
title: Maven多环境配置
date: 2019-07-23 17:54:07
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/96427522](https://blog.csdn.net/MOKEXFDGH/article/details/96427522)   
    
  ### 文章目录


      * [pom.xml](#pomxml_7)
      * [准备不同的配置文件](#_44)
      * [配置build标签](#build_50)
      * [使用与问题](#_76)  


 首先介绍一下什么是多环境？  
 一般我们项目开发的流程是：开发->测试->上线，根据不同的环境我们可能会有不同的配置文件（连接地址等），因此需要根据不同的环境打包出拥有不同配置文件的包。而为了打包出不同的包，我们可以在 maven 的 pom.xml 中进行相关的配置。

 
--------
 
### []()pom.xml

 在pom.xml中添加如下profile的配置：

 
```
		<profiles>
			<!-- 本地环境 -->
			<profile>
				<id>local</id>
				<properties>
					<packageMode>local</packageMode>
				</properties>
				<activation>
					<activeByDefault>true</activeByDefault>
				</activation>
			</profile>
			<!-- 测试环境 -->
			<profile>
				<id>test</id>
				<properties>
					<packageMode>test</packageMode>
				</properties>
				<activation>
					<activeByDefault>false</activeByDefault>
				</activation>
			</profile>
			<!-- 生产环境 -->
			<profile>
				<id>product</id>
				<properties>
					<packageMode>product</packageMode>
				</properties>
				<activation>
					<activeByDefault>false</activeByDefault>
				</activation>
			</profile>
		</profiles>

```
 
--------
 
### []()准备不同的配置文件

 在 src/main/resources，如下图：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190723173601287.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 不同环境的配置文件都在 profile 目录的子目录中，公用的配置文件直接放在 profile 目录下。

 
--------
 
### []()配置build标签

 在build标签里面添加resources标签：

 
```
		<finalName>${artifactId}-${packageMode}</finalName><!-- 按照我们的要求设置打包后的名字 -->
		<resources>
			<!-- 1. 排除profile文件 -->
			<resource>
				<directory>src/main/resources</directory>
				<excludes>
					<exclude>profile/**</exclude>
				</excludes>
			</resource>
			<!-- 2. 添加激活的profile文件 -->
			<resource>
				<directory>src/main/resources/profile/${packageMode}</directory>
			</resource>
			<!-- 3. 添加公共的profile文件 -->
			<resource>
				<directory>src/main/resources/profile</directory>
				<includes>
					<include>*</include>
				</includes>
			</resource>
		</resources>

```
 
--------
 
### []()使用与问题

 **使用**  
 在打包之前，我们就可以通过修改 profile->activation->activeByDefault 的值，打包出具有不同配置文件的包了。

 **问题**  
 父子Maven工程打包时，Maven报错:Non-resolvable parent POM for XXX，两种可能：

  
  2. 子pom文件的parent relativePath 写错，我的并没有错  
```
		<parent>
			<groupId>com.moke</groupId>
			<artifactId>demo-parent</artifactId>
			<version>0.0.1</version>
			<relativePath>../demo-parent/pom.xml</relativePath><!-- 相对路径 -->
		</parent>

```
  
  2. 父pom文件设置了 modules 没有先 install 一次会报错，注意父pom文件在install前需要把modules注释掉，在install后再解开，就可以 build 子项目了。  
```
		<modules>
			<module>../../videodemo</module>
		</modules>

```
   
  