---
title: 数据库概念与应用（mysql）
date: 2019-02-01 10:37:45
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86704720](https://blog.csdn.net/MOKEXFDGH/article/details/86704720)   
    
  ### 文章目录


    * [数据库管理系统](#_3)
      * [概述](#_5)
      * [数据库管理系统(DBMS)的概述](#DBMS_10)
    * [SQL语言](#SQL_22)
      * [概述](#_24)
      * [SQL语法](#SQL_34)
      * [SQL语句分类](#SQL_39)
    * [DDL](#DDL_48)
      * [库](#_50)
      * [常用数据类型](#_72)
      * [表](#_84)
    * [DML](#DML_140)
      * [插入数据](#_142)
      * [修改数据](#_153)
      * [删除数据](#_164)
    * [DCL(了解)](#DCL_176)
      * [创建用户](#_178)
      * [给用户授权](#_188)
      * [撤销授权](#_204)
      * [查看权限和删除用户](#_215)
    * [DQL](#DQL_222)
      * [基本查询](#_224)
      * [条件控制](#_256)
      * [排序](#_276)
      * [聚合函数](#_290)
      * [分组查询](#_317)
      * [limit子句](#limit_328)
    * [编码](#_335)
    * [数据库备份和恢复](#_360)
    * [约束](#_372)
      * [主键约束](#_374)
      * [主键自增长](#_401)
      * [常用约束](#_421)
      * [外键约束](#_441)
    * [概念模型](#_468)
      * [对象模型](#_472)
      * [关系模型](#_505)
    * [多表查询](#_540)
      * [合并结果集](#_542)
      * [连接查询](#_552)
      * [子查询](#_594)
    * [JDBC](#JDBC_623)
      * [JDBC原理](#JDBC_625)
      * [连接MySQL方法](#MySQL_630)
      * [JDBC实现增删改查](#JDBC_652)
      * [规范化代码](#_683)
      * [JDBC常用对象](#JDBC_711)
      * [结果集光标与元数据](#_742)
      * [结果集的特性（了解）](#_768)
      * [PreparedStatement](#PreparedStatement_788)
      * [JdbcUtils小工具1.0](#JdbcUtils10_824)
    * [面向接口编程](#_852)
    * [时间类型](#_912)
      * [utils包和SQL包的Date](#utilsSQLDate_914)
      * [时间类型的转换](#_925)
    * [大数据](#_940)
      * [SQL提供的数据类型](#SQL_942)
      * [在mysql中读写MP3文件](#mysqlMP3_957)
    * [批处理](#_1006)
      * [Statement批处理](#Statement_1013)
      * [PreparedStatement批处理](#PreparedStatement_1029)
    * [事务](#_1047)
      * [四大特性（ACID）](#ACID_1050)
      * [MySQL中的事务](#MySQL_1058)
      * [JDBC中的事务](#JDBC_1071)
      * [事务隔离级别](#_1088)  


 
--------
 
## []()数据库管理系统

 
### []()概述

 1.数据库的类型：层次结构模型数据库、网状结构模型数据库、关系结构模型数据库（*）  
 2.我们常说的数据库指：关系型数据库管理系统（数据库系统：数据库、数据库管理系统、数据库管理员…）  
 3.常见数据库管理系统：Oracle(*)、DB2、SQL Server、Sybase、MySQL(*)

 
### []()数据库管理系统(DBMS)的概述

  
  2. 什么是DBMS：数据的仓库  
> 方便查询  
>  可存储的数据量大  
>  保证数据的完整、一致  
>  安全可靠
> 
>  
  
  2. DBMS = 管理程序 + 多个数据库(DB) 
  4. DB = 多个table(不只是table，但这里先不介绍其他组成部分) 
  6. table的结构(即表结构)和table的记录(即表记录)的区别！ 
  8. 应用程序与DBMS：应用程序使用DBMS来存储数据！  
--------
 
## []()SQL语言

 
### []()概述

  
  2. 什么是SQL：结构化查询语言(Structured Query Language) 
  4. SQL的作用：客户端使用SQL来操作服务器  
> 启动mysql.exe，连接服务器后，就可以使用sql来操作服务器了  
>  将来会使用Java程序连接服务器，然后使用sql来操作服务器
> 
>  
  
  2. SQL标准(例如SQL99，即1999年制定的标准)：  
> 由国际标准化组织(ISO)制定的，对DBMS的统一操作方式(例如相同的语句可以操作：mysql、oracle等)
> 
>  
  
  2. SQL方言  
> 某种DBMS不只会支持SQL标准，而且还会有一些自己独有的语法，这就称之为方言！例如limit语句只在MySQL中可以使用
> 
>  
 
### []()SQL语法

  
  2. SQL语句可以在单行或多行书写，以分号结尾 
  4. 可使用空格和缩进来增强语句的可读性  
      3.MySQL不区别大小写，建议使用大写  
### []()SQL语句分类

  
  2. DDL（Data Definition Language）：数据定义语言，用来定义数据库对象：库、表、列等  
> 创建、删除、修改：库和表**结构**
> 
>  
  
  2. DML（Data Manipulation Language）：数据操作语言，用来定义数据库记录（数据）  
> 增、删、改：表**记录**
> 
>  
  
  2. DCL（Data Control Language）：数据控制语言，用来定义访问权限和安全级别 
  4. DQL（Data Query Language）：数据查询语言，用来查询记录（数据）  
--------
 
## []()DDL

 
### []()库

 1.查看所有数据库

 
```
    	SHOW DATABASES;

```
 2.切换（选择要操作的）数据库

 
```
    	USE 数据库名;

```
 3.创建数据库

 
```
    	CREATE DATABASE [IF NOT EXISTS] mydb1 [CHARSET=utf8];

```
 4.删除数据库

 
```
    	DROP DATABASE [IF EXISTS] mydb1;

```
 5.修改数据库编码

 
```
    	ALTER DATABASE mydb1 CHARACTER SET utf8;

```
 
### []()常用数据类型

 int：整型  
 double：浮点型，例如double(5,2)表示最多5位，其中必须有2位小数，即最大值为999.99；  
 decimal：浮点型，在表单钱方面使用该类型，因为不会出现精度缺失问题；  
 char：固定长度字符串类型； char(255)，数据的长度不足指定长度，补足到指定长度！  
 varchar：可变长度字符串类型； varchar(65535)  
 text(clob)：字符串类型；前缀：很小(tiny)、小(无)、中(mediumblob)、大(long)  
 blob：字节类型；前缀和text一样  
 date：日期类型，格式为：yyyy-MM-dd；  
 time：时间类型，格式为：hh:mm:ss  
 timestamp：时间戳类型；

 
### []()表

 1.创建表：

 
```
    	CREATE TABLE [IF NOT EXISTS] 表名(
      		列名 列类型,
      		列名 列类型,
      		...
      		列名 列类型
     	);

```
 2.查看当前数据库中所有表名称

 
```
    	SHOW TABLES;

```
 3.查看指定表的创建语句

 
```
    	SHOW CREATE TABLE 表名;

```
 4.查看表结构

 
```
    	DESC 表名;

```
 5.删除表

 
```
    	DROP TABLE 表名;

```
 6.修改表：

 
```
    	ALTER TABLE 表名;

```
 （1）修改之添加列

 
```
    	ALTER TABLE 表名 ADD (
      		列名 列类型,
      		列名 列类型,
      		...
    	);

```
 (2)修改之修改列类型(如果被修改的列已存在数据，那么新的类型可能会影响到已存在数据)：

 
```
    	ALTER TABLE 表名 MODIFY 列名 列类型;

```
 (3)修改之修改列名

 
```
    	ALTER TABLE 表名 CHANGE 原列名 新列名 列类型;

```
 (4)修改之删除列

 
```
    	ALTER TABLE 表名 DROP 列名;

```
 (5)修改表名称

 
```
    	ALTER TABLE 原表名 RENAME TO 新表名;

```
 
--------
 
## []()DML

 
### []()插入数据

 1.在表名后给出要插入的列名，其他没有指定的列等同与插入null值。所以插入记录总是插入一行，不可能是半行。  
 在VALUES后给出列值，值的顺序和个数必须与前面指定的列对应

 
```
    	INTERT INTO 表名(列名1,列名2, ...) VALUES(列值1, 列值2, ...);

```
 2.没有给出要插入的列，那么表示插入所有列。值的个数必须是该表列的个数。值的顺序，必须与表创建时给出的列的顺序相同。

 
```
    	INTERT INTO 表名 VALUES(列值1, 列值2);

```
 
### []()修改数据

 
```
    	UPDATE 表名 SET 列名1=列值1, 列名2=列值2, ... [WHERE 条件];

```
 1.条件(条件可选的),条件必须是一个boolean类型的值或表达式：

 
```
    	UPDATE t_person SET gender='男', age=age+1 WHERE sid='1';

```
 条件运算符：=、!=、<>、>、<、>=、<=、BETWEEN…AND、IN(…)、IS NULL、NOT、OR、AND  
 注：在数据库中所有的字符串类型，必须使用单引，不能使用双引！日期类型也要使用单引！

 
### []()删除数据

  
  2.   
```
    	DELETE FROM 表名 [WHERE 条件];

```
  
  2.   
```
    	TRUNCATE TABLE 表名;

```
 注：TRUNCATE是DDL语句，它是先删除drop该表，再create该表，无法回滚

 
--------
 
## []()DCL(了解)

 
### []()创建用户

 1.用户只能在指定的IP地址上登录

 
```
    	CREATE USER 用户名@IP地址 IDENTIFIED BY '密码';

```
 2.用户可以在任意IP地址上登录

 
```
    	CREATE USER 用户名@'%' IDENTIFIED BY '密码';

```
 
### []()给用户授权

 1.给用户分派在指定的数据库上的指定的权限

 
```
  	GRANT 权限1, … , 权限n ON 数据库.* TO 用户名@IP地址

```
 例如:

 
```
    	GRANT CREATE,ALTER,DROP,INSERT,UPDATE,DELETE,SELECT ON mydb1.* TO user1@localhost;

```
 给user1用户分派在mydb1数据库上的create、alter、drop、insert、update、delete、select权限

 2.给用户分派指定数据库上的所有权限

 
```
    	GRANT ALL ON 数据库.* TO 用户名@IP地址;

```
 
### []()撤销授权

 撤消指定用户在指定数据库上的指定权限

 
```
    	REVOKE 权限1, … , 权限n ON 数据库.* FROM 用户名@IP地址;

```
 例如:

 
```
    	REVOKE CREATE,ALTER,DROP ON mydb1.* FROM user1@localhost;

```
 撤消user1用户在mydb1数据库上的create、alter、drop权限

 
### []()查看权限和删除用户

 
```
    	SHOW GRANTS FOR 用户名@IP地址
    	DROP USER 用户名@IP地址

```
 
--------
 
## []()DQL

 
### []()基本查询

  
  2. 查询所有列  
```
		SELECT * FROM 表名;

```
 2.查询指定列

 
```
 		SELECT 列1 [, 列2, ... 列N] FROM 表名;

```
 3.重复的记录只显示一次

 
```
		SELECT DISTINCT * | 列1 [, 列2, ... 列N] FROM 表名;

```
 4.列运算  
 (1)数量类型的列可以做加、减、乘、除运算

 
```
   		SELECT sal*1.5 FROM emp;

```
 (2)字符串类型可以做连续运算

 
```
   		SELECT CONCAT('$', sal) FROM emp;

```
 (3)转换NULL值:有时需要把NULL转换成其它值，例如com+1000时，如果com列存在NULL值，那么NULL+1000还是NULL，而我们这时希望把NULL当前0来运算

 
```
   		SELECT IFNULL(comm, 0)+1000 FROM emp;

```
 (4)给列起别名:当使用列运算后，查询出的结果集中的列名称很不好看，这时我们需要给列名起个别名

 
```
   		SELECT IFNULL(comm, 0)+1000 AS 奖金 FROM emp;

```
 
### []()条件控制

 1.条件查询:与前面介绍的UPDATE和DELETE语句一样，SELECT语句也可以使用WHERE子句来控制记录  
 2.模糊查询  
 (1)当你想查询姓张，并且姓名一共两个字的员工时，这时就可以使用模糊查询

 
```
		SELECT * FROM emp WHERE ename LIKE '张_';//三个字：'张__'

```
 (2)查询所有姓张的员工时就要使用“%”,其中%匹配0~N个任意字符

 
```
		SELECT * FROM emp WHERE ename LIKE '张%';

```
 (3)查询ename中含有阿字的所有员工（包括首尾）

 
```
		SELECT * FROM emp WHERE ename LIKE '%阿%';

```
 (4) 条件等同与不存在，但如果ename为NULL的查询不出来

 
```
		SELECT * FROM emp WHERE ename LIKE '%';

```
 
### []()排序

 1.升序(ASC是可以省略的)

 
```
  		SELECT * FROM WHERE emp ORDER BY sal ASC;

```
 2.降序(DESC不能省略)

 
```
  		SELECT * FROM WHERE emp ORDER BY comm DESC;

```
 3.使用多列作为排序条件(使用sal升序排，如果sal相同时，使用comm的降序排)

 
```
  		SELECT * FROM WHERE emp ORDER BY sal ASC, comm DESC;

```
 
### []()聚合函数

 1.COUNT  
 (1)计算emp表中所有列都不为NULL的记录的行数

 
```
  		SELECT COUNT(*) FROM emp;

```
 (2)计算emp表中comm列不为NULL的记录的行数

 
```
  		SELECT COUNT(comm) FROM emp;

```
 2.MAX:查询最高工资

 
```
  		SELECT MAX(sal) FROM emp;

```
 3.MIN:查询最低工资

 
```
 		SELECT MIN(sal) FROM emp;

```
 4.SUM:查询工资合

 
```
  		SELECT SUM(sal) FROM emp;

```
 5.AVG:查询平均工资

 
```
  		SELECT AVG(sal) FROM emp;

```
 
### []()分组查询

 1.GROUP BY [HAVING COUNT(*)]  
 (2)分组查询是把记录使用某一列进行分组，然后查询组信息,如查看所有部门的记录数：

 
```
		SELECT deptno, COUNT(*) FROM emp GROUP BY deptno;

```
 (2)以部门分组，查询每组记录数。条件为记录数大于3:

 
```
  		SELECT deptno, COUNT(*) FROM emp GROUP BY deptno HAVING COUNT(*) > 3;

```
 
### []()limit子句

 LIMIT用来限定查询结果的起始行，以及总行数，用于分页查询，如：查询起始行为第5行（从0开始），一共查询3行记录:

 
```
		SELECT * FROM emp LIMIT 4, 3;

```
 
--------
 
## []()编码

  
  2. 查看MySQL数据库编码  
```
		SHOW VARIABLES LIKE 'char%';

```
  
  2. 各编码解释  
      character_set_client：MySQL使用该编码来解读客户端发送过来的数据  
      character_set_results：MySQL会把数据转换成该编码后，再发送给客户端  
      其它编码只要支持中文即可
     
       
  4. 控制台乱码问题  
      （1）插入或修改时出现乱码：  
      这时因为cmd下默认使用GBK，而character_set_client不是GBK的原因。我们只需让这两个编码相同即可。  
      因为修改cmd的编码不方便，所以我们去设置character_set_client为GBK即可。  
      （2）查询出的数据为乱码：  
      这是因为character_set_results不是GBK，而cmd默认使用GBK的原因。我们只需让这两个编码相同即可。  
      因为修改cmd的编码不方便，所以我们去设置character_set_results为GBK即可。  
      （3） 设置变量的语句：
     
        
```
		set character_set_client=gbk;  
		set character_set_results=gbk;  

```
 注：设置变量只对当前连接有效，当退出窗口后，再次登录mysql，还需要再次设置变量。为了一劳永逸，可以在my.ini中设置default-character-set=gbk即可

 
--------
 
## []()数据库备份和恢复

 1.数据库–>sql：备份mydb3的内容（不是数据库）

 
```
		mysqldump -uroot -p123 mydb3>c:/a.sql

```
 2.sql–>数据库：恢复mydb3(两种方法)

 
```
		mysql -uroot -p123 mydb3<c:/a.sql
		source c:/a.sql

```
 
--------
 
## []()约束

 
### []()主键约束

 约束：对列的约束；主键：唯一标识、非空、可以被引用  
 1.创建表时指定主键的两种方式(为sid列添加主键约束)PRIMARY KEY：

 
```
		CREATE TABLE stu(
			sid	CHAR(6) PRIMARY KEY,
			sname	VARCHAR(20),
			age	INT,
			gender	VARCHAR(10) 
    	);
		CREATE TABLE stu(
			sid	CHAR(6),
			sname	VARCHAR(20),
			age	INT,
			gender	VARCHAR(10),
			PRIMARY KEY(sid)
    	);

```
 2.修改表时指定主键：

 
```
		ALTER TABLE stu ADD PRIMARY KEY(sid);

```
 3.删除主键：

 
```
		ALTER TABLE stu DROP PRIMARY KEY;

```
 
### []()主键自增长

 作用：指定主键类型为整型，然后设置其自动增长，这样可以保证在插入数据时主键列的唯一和非空特性  
 1.创建表时指定主键自增长AUTO_INCREMENT

 
```
		CREATE TABLE stu(
			sid INT PRIMARY KEY AUTO_INCREMENT,
			sname	VARCHAR(20),
			age	INT,
			gender	VARCHAR(10)
 		);

```
  
  2. 修改表时设置主键自增长：  
```
		ALTER TABLE stu CHANGE sid INT AUTO_INCREMENT;

```
 3.修改表时删除主键自增长：

 
```
		ALTER TABLE stu CHANGE sid INT;

```
 
### []()常用约束

 1.非空约束NOT NULL：

 
```
		CREATE TABLE stu(
			sid INT PRIMARY KEY AUTO_INCREMENT,
			sname	VARCHAR(20) NOT NULL,
			age	INT,
			gender	VARCHAR(10)
  		);

```
 2.唯一约束UNIQUE

 
```
		CREATE TABLE stu(
			sid INT PRIMARY KEY AUTO_INCREMENT,
			sname	VARCHAR(20) NOT NULL UNIQUE,
			age		INT,
			gender	VARCHAR(10)
  		);

```
 
### []()外键约束

 1.特点：  
 外键必须是另一表的主键的值(外键要引用主键！)  
 外键可以重复  
 外键可以为空  
 一张表中可以有多个外键

 2.CONSTRAINT 约束名称 FOREIGN KEY(外键列名) REFERENCES 关联表(关联表的主键)

 
```
		create talbe emp (
    		empno int primary key,
    		...
    		deptno int,
    		CONSTRAINT fk_emp FOREIGN KEY(mgr) REFERENCES emp(empno);
  		);

```
 3.修改表时添加外键约束

 
```
  		ALERT TABLE emp 
		ADD CONSTRAINT fk_emp_deptno FOREIGN KEY(deptno) REFERENCES dept(deptno);

```
 4.修改表时删除外键约束

 
```
  		ALTER TABLE emp
  		DROP FOREIGN KEY fk_emp_deptno;/*约束名称*/

```
 
## []()概念模型

 概念模型：当我们要完成一个软件系统时，需要把系统中的实体抽取出来，形成概念模型  
 实体之间还存在着关系，关系有三种：1对多、1对1、多对多

 
### []()对象模型

 可以双向关联，而且引用的是对象，而不是一个主键（概念模型在java形成实体类：domain，即javabean）  
 类就使用成员变量来完成关系，一般都是双向关联！多对一双向中关联，即员工关联部门，部门也关联员工

 
```
	//一对多
  	class Employee {//多方关联一方
     		...
     		private Department department;
  	}
 	class Department {//一方关联多方
     		...
     		private List<Employee> employees;
  	}
	//一对一
	class Husband {
     		...
     		private Wife wife;
  	}
  	class Wife {
     		...
     		private Husband husband;
  	}
	//多对多
	class Student {
     		...
     		private List<Teacher> teachers
  	}
  	class Teacher {
     		...
     		private List<Student> students;
  	}

```
 
### []()关系模型

 只能多方引用一方，而且引用的只是主键，而不是一整行记录（概念模型在数据库中形成表）  
 1.数据库一对一关系:在表中建立一对一关系比较特殊，需要让其中一张表的主键，即是主键又是外键

 
```
	create table husband(
    		hid int PRIMARY KEY,
    		...
  	);
  	create table wife(
    		wid int PRIMARY KEY,
    		...
    		ADD CONSTRAINT fk_wife_wid FOREIGN KEY(wid) REFERENCES husband(hid)
  	);

```
 其中wife表的wid即是主键，又是相对husband表的外键

 2.数据库多对多关系:在表中建立多对多关系需要使用中间表，即需要三张表，在中间表中使用两个外键，分别引用其他两个表的主键

 
```
	create table student(
    		sid int PRIMARY KEY,
    		...
  	);
  	create table teacher(
    		tid int PRIMARY KEY,
    		...
  	);
  	create table stu_tea(
    		sid int,
    		tid int,
    		ADD CONSTRAINT fk_stu_tea_sid FOREIGN KEY(sid) REFERENCES student(sid),
    		ADD CONSTRAINT fk_stu_tea_tid FOREIGN KEY(tid) REFERENCES teacher(tid)
  	);

```
 
--------
 
## []()多表查询

 
### []()合并结果集

 1.要求被合并的表中，列的类型和列数相同  
 2.UNION，去除重复行  
 3.UNION ALL，不去除重复行

 
```
	SELECT * FROM cd
	UNION ALL
	SELECT * FROM ab;

```
 
### []()连接查询

  
  2. 分类  
      （1）内连接  
      （2）外连接  
      左外连接  
      右外连接  
      全外连接(MySQL不支持)  
      (3)自然连接（属于一种简化方式）  
      用于多表查询，多张表连接成一张表，表中的记录为多张表的笛卡儿积  2.内连接  
 （1）方言：

 
```
	SELECT * FROM 表1 别名1, 表2 别名2 WHERE 别名1.xx=别名2.xx;

```
 （2）标准内连接：

 
```
	SELECT * FROM 表1 别名1 INNER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;

```
 （3）自然连接：

 
```
	SELECT * FROM 表1 别名1 NATURAL JOIN 表2 别名2;

```
 3.外连接  
 （1）左外连接：左表记录无论是否满足条件都会查询出来，而右表只有满足条件才能出来。左表中不满足条件的记录，右表部分都为NULL

 
```
	SELECT * FROM 表1 别名1 LEFT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;

```
 (2)左外自然：

 
```
	SELECT * FROM 表1 别名1 NATURAL LEFT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;

```
 (3)右外连接：右表记录无论是否满足条件都会查询出来，而左表只有满足条件才能出来。右表不满足条件的记录，其左表部分都为NULL

 
```
	SELECT * FROM 表1 别名1 RIGHT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;

```
 (4)右外自然：

 
```
	SELECT * FROM 表1 别名1 NATURAL RIGHT OUTER JOIN 表2 别名2 ON 别名1.xx=别名2.xx;

```
 
### []()子查询

 1.子查询，即嵌套查询，查询中还有查询

 2.出现的位置：  
 where后作为条件存在  
 from后作为表存在(多行多列)

 3.条件  
 （1）单行单列：

 
```
	SELECT * FROM 表1 别名1 
	WHERE 列1 [=、>、<、>=、<=、!=] (SELECT 列 FROM 表2 别名2 WHERE 条件)

```
 （2）多行单列：

 
```
	SELECT * FROM 表1 别名1 
	WHERE 列1 [IN, ALL, ANY] (SELECT 列 FROM 表2 别名2 WHERE 条件)

```
 （3）单行多列：

 
```
	SELECT * FROM 表1 别名1 
	WHERE (列1,列2) IN (SELECT 列1, 列2 FROM 表2 别名2 WHERE 条件)

```
 （4）多行多列：

 
```
	SELECT * FROM 表1 别名1 , (SELECT ....) 别名2 WHERE 条件

```
 
--------
 
## []()JDBC

 
### []()JDBC原理

 SUN公司提供的规范命名为JDBC，而各个厂商提供的，遵循了JDBC规范的，可以访问自己数据库的API被称之为驱动  
 JDBC是接口，而JDBC驱动才是接口的实现  
 JDBC：java数据库连接，即用java语言来操作数据库（发送SQL语句）

 
### []()连接MySQL方法

 1.导入jar包，即驱动：mysql-connector-java  
 2.加载驱动类，Class.forName(“类名”);  
 3.给出url、username、password

 
```
	Class.forName("com.mysql.jdbc.Driver");//加载驱动类（注册驱动）
	/*
	相当于：
	com.mysql.jdbc.Driber driver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(driver);
	所有的数据库驱动类都提供了static代码块，可以直接通过反射调用将自己注册到驱动中
	*/
        String url = "jdbc:mysql://localhost:3306/mydb3";
        String username = "root";
        String password = "123";

        Connection con = DriverManager.getConnection(url,username,password);
        /*
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb3","root","123");
         */

```
 
### []()JDBC实现增删改查

 1.增删改：Statement、executeUpdate

 
```
	Class.forName("com.mysql.jdbc.Driver");//加载驱动类
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb3","root","123");//获取连接对象	
        Statement stmt = con.createStatement();//创建statement对象给数据库发送sql语句
	//sql语句
        String sql1 = "INSERT INTO stu VALUES('ITCAST_003','WANGWU','25','male')";
	String sql2 = "UPDATE stu SER name='zhaoliu',age='22',gerder='female' WHERE number='ITCAST_003'";
	String sql3 = "DELETE FROM stu";
        stmt.executeUpdate(sql1);//发送添加语句
        stmt.executeUpdate(sql2);//发送修改语句
        stmt.executeUpdate(sql3);//发送删除语句

```
 2.查询：Statement、executeQuery、ResultSet

 
```
	Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb3","root","123");
	Statement stmt = con.createStatement();
	String sql ="SELECT * FROM emp";
	ResultSet rs = stmt.executeQuery(sql);//ResultSet内有行光标，默认位置在beforeFirst：第一行之前；AfterLast：最后一行之后
	while(rs.next()){//next方法可以将行光标下移一行，且提供了一系列get方法：getInt(),getString,getDouble()...
		int empno = rs.getInt(1);//通过列编号来获取第一列
		String ename = rs.getString("ename");//通过列名ename获取
		doublie sal = rs.getDouble("sal");
	}
	rs.close();
	stmt.close();
	con.close();//连接必须要关

```
 
### []()规范化代码

 所谓规范化代码就是无论是否出现异常，都要关闭ResultSet、Statement，以及Connection

 
```
	public void query() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();[在try内为对象实例化]
			stmt = con.createStatement();
			String sql = "select * from user";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				System.out.println(username + ", " + password);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			} catch(SQLException e) {}
	}

```
 
### []()JDBC常用对象

 1.DriverManager  
 只需要会用DriverManager的getConnection()方法即可  
 对于DriverManager.registerDriver()方法了解即可

 2.Connection  
 Connection最为重要的方法就是获取Statement：

 
```
	Statement stmt = con.createStatement(); 

```
 后面在学习ResultSet方法时，还要学习一下下面的方法：

 
```
	Statement stmt = con.createStatement(int,int);//这两个参数是用来确定创建的Statement能生成什么样的结果集

```
 3.Statement

 
```
	int executeUpdate(String sql)

```
 执行更新操作，即执行insert、update、delete语句，其实这个方法也可以执行create table、alter table，以及drop table等语句，但我们很少会使用JDBC来执行这些语句

 
```
	ResultSet executeQuery(String sql)

```
 执行查询操作，执行查询操作会返回ResultSet，即结果集

 
```
	boolean execute(String sql)

```
 可以用来执行增、删、改、查所有SQL语句。该方法返回的**是boolean类型**，表示SQL语句是否有结果集  
 如果使用execute()方法执行的是更新语句，那么还要调用int getUpdateCount()来获取insert、update、delete语句所影响的行数  
 如果使用execute()方法执行的是查询语句，那么还要调用ResultSet getResultSet()来获取select语句的查询结果

 
### []()结果集光标与元数据

 1.滚动结果集：ResultSet提供了一系列的方法来移动或判断光标位置  
 （1）判断光标位置  
 boolean isBeforeFirst()：当前光标位置是否在第一行前面；  
 boolean isAfterLast()：当前光标位置是否在最后一行的后面；  
 boolean isFirst()：当前光标位置是否在第一行上；  
 boolean isLast()：当前光标位置是否在最后一行上；  
 （2）移动光标  
 void beforeFirst()：把光标放到第一行的前面，这也是光标默认的位置；  
 void afterLast()：把光标放到最后一行的后面；  
 boolean first()：把光标放到第一行的位置上，返回值表示调控光标是否成功；  
 boolean last()：把光标放到最后一行的位置上；

 boolean previous()：把光标向上挪一行；  
 boolean next()：把光标向下挪一行；**常用**  
 boolean relative(int row)：相对位移，当row为正数时，表示向下移动row行，为负数时表示向上移动row行；  
 boolean absolute(int row)：绝对位移，把光标移动到指定的行上；  
 int getRow()：返回当前光标所有行。  
 注：默认结果集为不可滚动，即行光标只能执行下一行方法

 2.获取结果集元数据  
 （1）得到元数据：rs.getMetaData()，返回值为ResultSetMetaData  
 （2）获取结果集列数：int getColumnCount()  
 （3）获取指定列的列名：String getColumnName(int colIndex)

 
### []()结果集的特性（了解）

 1.三个特性：是否可滚动、是否敏感、是否可更新

 2.con.createSttement()：生成的结果集：不滚动、不敏感、不可更新

 3.con.createStatement(int,int)：  
 （1）第一个参数：  
 ResultSet.TYPE_FORWARD_ONLY：不滚动结果集  
 ResultSet.TYPE_SCROLL_INSENSITIVE：滚动结果集，但结果集数据不会再跟随数据库而变化  
 ResultSet.TYPE_SCROLL_SENSITIVE：滚动结果集，但结果集数据不会再跟随数据库而变化（敏感特性目前没有数据库驱动支持）  
 （2）第二个参数：  
 CONCUR_READ_ONLY：结果集是只读的，不能通过修改结果集而反向影响数据库  
 CONCUR_UPDATABLE：结果集是可更新的，对结果集的更新可以反向影响数据库（支持，但一般不使用）

 4.一般获取滚动结果集的方法：

 
```
	Connection con = Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);

```
 
### []()PreparedStatement

 1.PreparedStatement是Statement的子接口  
 特点：防SQL攻击、提高代码的可读性和可维护性、提高效率  
 SQL攻击,用以下sql语句可查出表上所有的记录：

 
```
	SELECT * FROM tab_user WHERE username='a' or 'a'='a' and password='a' or 'a'='a';

```
 2.使用方法：  
 （1）给出SQL模板  
 （2）调用Connection的PreparedStatement prepareStatement(String sql模板)  
 （3）调用pstmt的setXxx()系列方法给sql模板中的？赋值  
 （4）调用pstmt的executeUpdate()或executeQuery()

 
```
	Class.forName(com.mysql.jdbc.Driver);
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb3","root","123");
	String sql = "select * from t_user where username=? and password=?";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setString(1,username);//给第一个问号赋值（登陆查询的username和password，不是数据库的）
	pstmt.setString(2,password);//给第二个问号赋值
	ResultSet rs = pstmt.executeQuery();

```
 3.预处理的原理  
 （1）数据库工作：检验sql语句的语法、编译、执行  
 （2）PreparedStatement叫预编译声明  
 前提：连接的数据库必须支持预处理（基本都支持）  
 每个pstmt都与一个sql模板绑定在一起，先把sql模板给数据库，数据库先进行校验，再进行编译。执行时只是把参数传递过去而已  
 若二次执行时，就不用再次校验语法，也不用再次编译！直接执行  
 注：mysql的预编译默认是关闭的  
 打开方法：

 
```
	String url = "jdbc:mysql://localhost:3306/test?useServerPrepStmts=true&cachePrepStmts=true"

```
 
### []()JdbcUtils小工具1.0

 
```
	public class JdbcUtils {
   		private static Properties props = new Properties();
    		static {
        		try{
            			InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");
            			Properties props = new Properties();
            			props.load(in);
        		}catch (IOException e){
            			throw new RuntimeException(e);
        		}

        		try{
            			Class.forName(props.getProperty("driverClassName"));
        		}catch (ClassNotFoundException e){
            			throw new RuntimeException(e);
        		}

    	}
    		public static Connection getConnection() throws  SQLException {
        		return DriverManager.getConnection(props.getProperty("url"),
			props.getProperty("username"),props.getProperty("password"));
    		}
	}

```
 
--------
 
## []()面向接口编程

 1.把UserDao修改为接口，然后把原来的UserDao修改类名为UserDaoImpl

 
```
	public interface UserDao {
    		public void add(User form);
    		public  User findByUsername(String username);
	}

```
 
```
	public class UserDaoImp implements UserDao {
    		private String Path = "G:/demo-login/users.xml";//依赖数据文件
	    	public User findByUsername(String username){
        		...
    		}
    		public void add(User user){
        		...
    		}
	}

```
 2.修改UserService中对UserDao的实例化：private UserDao userDao = DaoFactory.getUserDao()//依赖接口

 
```
	public class UserService {
    		private UserDao userDao = DaoFactory.getUserDao();
    		public void regist(User user) throws UserException {
        		...
    		}
    		public User login(User form) throws UserException{
			...
       			return user;
    		}
}

```
 3.创建DaoFactory，提供getUserDao()//通过配置文件加载实现类名称，再通过反射创建实例

 
```
	public class DaoFactory {
    		private static  Properties props = null;
    		static{
        		try{
            			InputStream in = DaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
				//定位本类路径下的配置文件
            			props = new Properties();
            			props.load(in);
        		}catch (Exception e){
            			throw new RuntimeException(e);
        		}
    		}

    		public static UserDao getUserDao()  {
        		String daoClassName = props.getProperty("cn.itcast.user.dao.UserDao");
        		try {
            			Class clazz = Class.forName(daoClassName);
            			return (UserDao) clazz.newInstance();
        		}catch (Exception e){
            			throw new RuntimeException(e);
        		}
    		}
	}

```
 
--------
 
## []()时间类型

 
### []()utils包和SQL包的Date

 数据库类型与java中类型的对应关系（java.sql包下给出三个时间类型是java.util.Date的子类）：  
 DATE->java.sql.Date  
 TIME->va.sql.Time  
 TIMESTAMP->va.sql.Timestamp  
 注：  
 ①领域对象（domain）中的所有属性不能出现java.sql包下的东西！即不能使用java.sql.Date  
 ②ResultSet#getDate()返回的是java.sql.Date()  
 ③PreparedStatement#setDate(int, Date)，其中第二个参数也是java.sql.Date

 
### []()时间类型的转换

 1.java.util.Date->java.sql.Date、Time、Timestamp  
 把util的Date转换成毫秒值  
 使用毫秒值创建sql的Date、Time、Timestamp，例如Date转换

 
```
	java.util.Date date = new java.util.Date();
	long l = date.getTime();
	java.sql.Date sqlDate = new java.sql.Date(l);

```
 2.java.sql.Date、Time、Timestamp->java.util.Date  
 这一步不需要处理了：因为java.sql.Date是java.util.Datede的子类

 
--------
 
## []()大数据

 
### []()SQL提供的数据类型

 大数据：大的字节数据或大的字符数据，标准SQL中提供了如下类型保存大数据：  
 tinyblob:28–1B（256B）  
 blob:216-1B（64K）  
 mediumblob:224-1B（16M）  
 longblob"232-1B（4G）  
 tinyclob:28–1B（256B）  
 clob:216-1B（64K）  
 mediumclob:224-1B（16M）  
 longclob:232-1B（4G）  
 而mysql中没有提供clob的四种类型，而是使用如下四种类型来处理文本大数据：  
 tinytext:28–1B（256B）  
 text:mediumtext:224-1B（16M）  
 longtext:232-1B（4G）

 
### []()在mysql中读写MP3文件

 1.创建表mediumblob：

 
```
	CREATE TABLE tab_bin(
		id 		INT 	PRIMARY KEY AUTO_INCREMENT,
		filename	VARCHAR(100),
		data 		MEDIUMBLOB
	);

```
 2.向数据库写入二进制数据（两种方法）

 
```
	Connection con = JdbcUtils.getConnection();
	String sql = "insert into tab_bin values(?,?,?)";
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setInt(1, 1);
	pstmt.setString(2,"歌曲.mp3")
	/*
	byte[] bytes = IOUtils.toByteArray(new new FileInputStream("f:/歌曲.mp3"));//使用commons-io包将mp3转成字节数组
	Blob blob = new SerialBlob(bytes);
	pstmt.setBlob(blob);
	*/
	InputStream in = new FileInputStream("f:/歌曲.mp3");
	pstmt.setBinaryStream(3, in);//直接使用setBinaryStream方法
	pstmt.executeUpdate();

```
 注：mysql默认不支持大数据，需要在my.ini中添加配置：max_allowed_packet=10485760

 3.读取数据库二进制数据

 
```
	Connection con = JdbcUtils.getConnection();
	String sql = "select * from tab_bin";
	PreparedStatement pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
	if(rs.next()){
			
	OutputStream out = new FileOutputStream("F:/gequ.mp3")//使用文件名来创建输出流对象
	/*
	Blob blob = rs.getBlob("data");
	InputStream in = blob.getBinaryStream();
	*/
	InputStream in = rs.getBinaryStream("data");//读取输入流对象
	IOUtils.copy(in, out);//使用commons-io包把in中的数据写入到out中
	}
	out.close();

```
 
--------
 
## []()批处理

 批处理就是一批一批的处理，而不是一个一个的处理  
 当你有10条SQL语句要执行时，一次向服务器发送一条SQL语句，这么做效率上很差  
 处理的方案是使用批处理，即一次向服务器发送多条SQL语句，然后由服务器一次性处理  
 注：批处理只针对更新（增、删、改）语句  
 mysql批处理也需要在my.ini中配置餐宿：rewriteBatchedStatements=true

 
### []()Statement批处理

 多次调用Statement类的addBatch(String sql)方法，把需要执行的所有SQL语句添加到一个“批”中  
 然后调用Statement类的executeBatch()方法来执行当前“批”中的语句  
 注： clearBatch()可以清空“批”中的所有语句

 
```
	for(int i = 0; i < 10; i++) {
		String number = "S_10" + i;
		String name = "stu" + i;
		int age = 20 + i;
		String gender = i % 2 == 0 ? "male" : "female";
		String sql = "insert into stu values('" + number + "', '" + name + "', " + age + ", '" + gender + "')";
		stmt.addBatch(sql);
	}
	stmt.executeBatch();

```
 
### []()PreparedStatement批处理

 PreparedStatement的批处理有所不同，因为每个PreparedStatement对象都绑定一条SQL模板  
 所以向PreparedStatement中添加的不是SQL语句，而是给“?”赋值

 
```
	Connection con = JdbcUtils.getConnection();
	String sql = "insert into stu values(?,?,?,?)";
	PreparedStatement pstmt = con.prepareStatement(sql);
	for(int i = 0; i < 10; i++) {
		pstmt.setString(1, "S_10" + i);
		pstmt.setString(2, "stu" + i);
		pstmt.setInt(3, 20 + i);
		pstmt.setString(4, i % 2 == 0 ? "male" : "female");
		pstmt.addBatch();//添加到“批”中
	}
	pstmt.executeBatch();//执行“批”中所有的sql语句

```
 
--------
 
## []()事务

 数据库事务：多个对数据库的操作绑定为一个事务，其要么完整地执行，要么完全地不执行

 
### []()四大特性（ACID）

 事务的四大特性是：  
 原子性（Atomicity）：事务中所有操作是不可再分割的原子单位。事务中所有操作要么全部执行成功，要么全部执行失败  
 一致性（Consistency）：事务执行后，数据库状态与其它业务规则保持一致。如转账业务，无论事务执行成功与否，参与转账的两个账号余额之和应该是不变的  
 隔离性（Isolation）：隔离性是指在并发操作中，不同事务之间应该隔离开来，使每个并发中的事务不会相互干扰  
 持久性（Durability）：一旦事务提交成功，事务中所有的数据操作都必须被持久化到数据库中，即使提交事务后，数据库马上崩溃，在数据库重启时，也必须能保证通过某种机制恢复数据  
 注：其他特性都是为了一致性服务的

 
### []()MySQL中的事务

 默认情况下，MySQL每执行一条SQL语句就是一个单独的事务，如果需要在一个事务中包含多条SQL语句，则需要开启事务  
 开启事务：starttransaction  
 结束事务：commit或rollback

 
```
	START TRANSACTION;
	UPDATE account SET balance=balance-10000 WHERE id=1;
	UPDATE account SET balance=balance+10000 WHERE id=2;
	COMMIT;

```
 注：在执行SQL语句之前，先执行starttransaction，在多条SQL语句之后要结束事务，则执行commit表示SQL语句所做出的影响  
 会持久化到数据库中；或者执行rollback表示回滚，即事务所有操作被撤销

 
### []()JDBC中的事务

 在jdbc中处理事务都是通过Connection完成，一个事务中的所有操作，都在同一个Connection对象里  
 **注**：为了Service中实现的功能是同一个事务，需要将Connection定义在Service，而对Connection的操作必须隐藏起来，详见后头  
 Connection与事务相关的三个方法：

 
```
	Connection con = null;
	try {
		con = JdbcUtils.getConnection();
  		con.setAutoCommit(false);//开启事务；如果设置为true则为自动提交，即每执行一条语句就提交
  		…
  		…
  		con.commit();//try的最后提交事务
	} catch() {
  		con.rollback();//回滚事务
	}

```
 
### []()事务隔离级别

 1.事务的**三个**并发读问题：  
 脏读：读取到另一个事务未提交数据  
 不可重复读：两次读取不一致，因为另一事务对该记录做了修改  
 幻读（虚读）：读到另一事务已提交数据

 2.**四大**隔离级别：  
 （1）SERIALIZABLE（串行化）：不会出现任何并发问题，因为它是对同一数据的访问是串行的，非并发访问的；性能最差  
 （2）REPEATABLE READ（可重复读）：防止脏读和不可重复读，不能处理幻读问题；性能比SERIALIZABLE好（MySQL默认使用）  
 （3）READ COMMITTED（读已提交数据）：防止脏读，没有处理不可重复读，也没有处理幻读；性能比REPEATABLE READ好（Oracle）  
 （4）READ UNCOMMITTED（读未提交数据）：可能出现任何事务并发问题；性能最好

 3.MySQL隔离级别  
 (1)查看数据库隔离级别：

 
```
	select @@tx_isolation;

```
 (2)设置隔离级别：

 
```
	set transaction isolationlevel [4先1];

```
 4.JDBC设置隔离级别：

 
```
	con. setTransactionIsolation(int level)
	/*四个可选参数
	Connection.TRANSACTION_READ_UNCOMMITTED；
	Connection.TRANSACTION_READ_COMMITTED；
	Connection.TRANSACTION_REPEATABLE_READ；
	Connection.TRANSACTION_SERIALIZABLE。
	*/

```
   
  