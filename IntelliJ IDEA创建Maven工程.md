---
title: IntelliJ IDEA创建Maven工程
date: 2019-02-20 15:32:09
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/87800213](https://blog.csdn.net/MOKEXFDGH/article/details/87800213)   
    
  ### 文章目录


      * [Maven简介](#Maven_3)
      * [下载并配置环境变量](#_23)
      * [创建父子工程](#_34)
      * [仓库](#_64)
      * [pom.xml](#pomxml_81)  


 
--------
 
### []()Maven简介

 Maven 是一个项目管理工具，通过 pom.xml 文件中的配置可以引入相关依赖 Jar 包。通过 Maven 相关命令可进行项目的清理、测试、编译、打包、安装等操作。Maven 常用命令如下：

 
```
		mvn archetype:generate 创建Maven项目
		mvn compile 编译源代码
		mvn deploy 发布项目
		mvn test-compile 编译测试源代码
		mvn test 运行应用程序中的单元测试
		mvn site 生成项目相关信息的网站
		mvn clean 清除项目目录中的生成结果
		mvn package 根据项目生成的 Jar
		mvn install 在本地 Repository 中安装 Jar
		mvn eclipse:eclipse 生成 Eclipse 项目文件
		mvn jetty:run 启动 Jetty 服务
		mvn tomcat:run 启动 Tomcat 服务
		mvn clean package 
		-Dmaven.test.skip=true  清除以前的包后重新打包，跳过测试类

```
 
--------
 
### []()下载并配置环境变量

 1.确保JDK已经配置好，Maven下载并配置环境变量：[下载地址](http://maven.apache.org/download.cgi)  
 （1）新建环境变量：  
 变量名：MAVEN_HOME，变量值：D:\softs\java\apache-maven-3.3.9  
 （2）在 path 变量中加入 MAVEN_HOME：  
 %MAVEN_HOME%\bin  
 （3）查看 Maven版本，如出现 Maven 版本信息，说明配置成功：  
 打开 cmd 命令提示符，键入：mvn -version

 
--------
 
### []()创建父子工程

 1.创建父工程：File -> New -> Project -> Maven；父工程就是空的工程，直接点击 next  
 ![1](https://img-blog.csdnimg.cn/20190220144934555.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 2.填写好GroupId、ArtifactId、Version 并点击 next  
 GroupId：是项目组织唯一的标识符，如cn.moke.test  
 ArtifactId：是项目的唯一的标识符，如testDemo，即项目的名称  
 ![2](https://img-blog.csdnimg.cn/20190220145710926.png)  
 3.接着填写项目名，点击 Finish，父工程就创建好了  
 ![3](https://img-blog.csdnimg.cn/20190220145757848.png)  
 4.创建子工程：  
 点击刚创建的父工程，然后进行如下操作：File -> New -> Moudle -> Maven  
 勾选 Web 骨架后点击 Next，填写好 ArtifactId 后点击 Next  
 ![4](https://img-blog.csdnimg.cn/20190220150036751.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 5.选择自己的maven目录、配置文件目录、本地仓库  
 添加一个属性，让该 Maven 项目的骨架不要到远程下载而是从本地获取，以提高加载速度  
 ![5](https://img-blog.csdnimg.cn/20190220150224809.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 6.子工程中没有 java、resources 和 test 目录，我们可以手动创建一下：  
 （1）New -> Directory  
 ![6](https://img-blog.csdnimg.cn/20190220150627714.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 main目录下创建java、resources  
 src目录下创建test，并在test目录下创建java、resources  
 （2）右键选择 Mark Diretory as  
 ![7](https://img-blog.csdnimg.cn/20190220150722506.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 main中的java->Sources Root、resources->Resources Root  
 test中的java->Test Sources Root、resources->Test Resources Root

 到此父子工程创建完毕

 
--------
 
### []()仓库

 1.概述  
 Maven 仓库是 Maven 管理 jar 包的地方，有本地仓库，远程仓库和中央仓库之分  
 中央仓库为 Maven 的仓库，不配置远程仓库，默认从中央仓库下载 jar 依赖  
 然而中央仓库在国外，下载起来速度会很慢，所以一般会选择配置阿里云远程仓库

 2.配置方法：  
 apache-maven目录中conf下的 settings.xml 中配置  
 （1）配置本地仓库：  
 ![a](https://img-blog.csdnimg.cn/201902201525298.png)  
 （2）配置远程仓库：  
 ![s](https://img-blog.csdnimg.cn/20190220152559606.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 （3）idea配置：  
 file -> settings添加 Maven 的路径以及 Maven 配置文件 settings.xml 的路径，本地仓库路径  
 ![d](https://img-blog.csdnimg.cn/20190220152839403.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
--------
 
### []()pom.xml

 一个基本的pom.xml至少由三部分组成  
 1.项目坐标，信息描述…

 
```
		<parent>
	        <artifactId>testDemo</artifactId>
	        <groupId>cn.moke.test</groupId>
	        <version>1.0-SNAPSHOT</version>
	    </parent>
	    <modelVersion>4.0.0</modelVersion>
	
	    <artifactId>test</artifactId>
	    <packaging>war</packaging>
	
	    <name>test Maven Webapp</name>
	    <!-- FIXME change it to the project's website -->
	    <url>http://www.example.com</url>

```
 modelVersion：pom文件的模型版本  
 group id：com.公司名.项目名  
 artifact id：功能模块名  
 packaging：项目打包的后缀，war是web项目发布用的，默认为jar  
 version: artifact模块的版本  
 name和url：相当于项目描述

 2.依赖，即引入jar包  
 注：第一次引入jar包会从远程仓库下载，并保存在本地仓库

 
```
		<dependencies>
	        <dependency>
	            <groupId>junit</groupId>
	            <artifactId>junit</artifactId>
	            <version>4.11</version>
	            <scope>test</scope>
	        </dependency>
	    </dependencies>

```
 依赖查询：[https://mvnrepository.com/](https://mvnrepository.com/)  
 ![8](https://img-blog.csdnimg.cn/20190220152148172.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 3.构建项目

 
```
		<build>
        <finalName>test</finalName>
        <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
            <plugins>
                <plugin>
                    <artifactId>maven-clean-plugin</artifactId>
                    <version>3.1.0</version>
                </plugin>
                ...
            </plugins>
        </pluginManagement>
    </build>

```
 build：项目构建时的配置  
 finalName：在浏览器中的访问路径  
 plugins：插件  
 configuration：设置插件的参数值

   
  