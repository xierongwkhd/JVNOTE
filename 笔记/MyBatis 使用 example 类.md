---
title: MyBatis 使用 example 类
date: 2019-07-07 10:20:05
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/94897840](https://blog.csdn.net/MOKEXFDGH/article/details/94897840)   
    
  ### 文章目录


      * [MyBatis 的 Example](#MyBatis__Example_3)
      * [Criteria 中的常用方法](#Criteria__21)
      * [案例](#_47)  


 
--------
 
### []()MyBatis 的 Example

 在 [逆向工程](https://blog.csdn.net/MOKEXFDGH/article/details/87120116#_1033) 中，我们可以根据数据库的表自动生产 MyBatis 所需的 mapper.java、mapper.xml、po.java、poExample.java。前三个我们已经非常熟悉了，而未使用过 poExample 类，直到最近在项目中碰到了，在这里总结一下。

 **Example**  
 Example类指定了如何构建一个动态的 where 子句. 表中的每个表的列可以被包括在 where 子句中。主要用于简化我们 sql 语句的编写。  
 Example类包含一个内部静态类 Criteria，而 Criteria中 的方法是定义 SQL 语句 where 后的查询条件。Criteria 对象可以通过 example 对象的 createCriteria 方法创建。

 要生成 Example，需要注意下 generatorConfig.xml 里的配置：

 
```
		<table tableName="user" domainObjectName="User">
				<!--
                enableCountByExample="false" enableUpdateByExample="false"
                enableDeleteByExample="false" enableSelectByExample="false"
                selectByExampleQueryId="false">
                -->
        </table>

```
 
--------
 
### []()Criteria 中的常用方法

 注：一下方法都是添加 where 条件。

 
     方法                                | 描述                         
     --------------------------------- | --------------------------- 
     example调用                         |                            
     setOrderByClause(“字段名 xxx”)       | 升序ASC，降序DESC               
     setDistinct(x)                    | 是否去除重复true/false           
     and(Criteria criteria)            | 为example添加criteria查询条件，关系为与
     or(Criteria criteria)             | 为example添加criteria查询条件，关系为或
     criteria调用                        |                            
     andXxxIsNull                      | 字段xxx为null                 
     andXxxIsNotNull                   | 字段xxx不为null                
     andXxxEqualTo(value)              | xxx字段等于value               
     andXxxNotEqualTo(value)           | xxx字段不等于value              
     andXxxGreaterThan(value)          | xxx字段大于value               
     andXxxGreaterThanOrEqualTo(value) | xxx字段大于等于value             
     andXxxLessThan(value)             | xxx字段小于value               
     andXxxLessThanOrEqualTo(value)    | xxx字段小于等于value             
     andXxxIn(List&lt;？&gt;)           | xxx字段值在List&lt;？&gt;       
     andXxxNotIn(List&lt;？&gt;)        | xxx字段值不在List&lt;？&gt;      
     andXxxLike(“%”+value+”%”)         | xxx字段值为value的模糊查询          
     andXxxNotLike(“%”+value+”%”)      | xxx字段值不为value的模糊查询         
     andXxxBetween(value1,value2)      | xxx字段值在value1和value2之间     
     andXxxNotBetween(value1,value2)   | xxx字段值不在value1和value2之间    


--------
 
### []()案例

  
  2. 使用 criteria：  
```
		UserExample example =new UserExample();//由逆向工程生成
		Criteria criteria = example.createCriteria();
		criteria.setOrderByClause("id des");//按 id 字段降序
		criteria.andUsernameEqualTo("zhangsan");
		List<User> list = userMapper.selectByExample(example);
		//sql：select * from t_user where name=zhangsan order by id des		

```
  
  2. 不使用 criteria：  
```
		UserExample example = new UserExample(); 
		example.and() 
			.andEqualTo("id", id) 
			.andEqualTo("name", name); //example.and()底层还是返回的criteria
		List<User> list = userMapper.selectByExample(example);

```
  
  2. and 和 or  
```
		UserExample example = new UserExample(); 
		Criteria cri1 = example.createCriteria(); 
		Criteria cri2 = example.createCriteria(); 
		cri1.andIn("id", ids); 
		cri2.orLike("des", "%" + des + "%"); 
		cri2.orLike("name", "%" + name + "%"); 
		example.and(cri2);//example.or(cri2);
		//where (id in ids) and (name like %des% or des like %name%)

```
   
  