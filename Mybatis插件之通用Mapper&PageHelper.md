---
title: Mybatis插件之通用Mapper&PageHelper
date: 2019-02-20 17:07:04
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/87807364](https://blog.csdn.net/MOKEXFDGH/article/details/87807364)   
    
  ### 文章目录


    * [通用Mapper](#Mapper_4)
      * [使用方法](#_10)
      * [实体类注解](#_73)
      * [通用方法详解](#_103)
    * [分页插件](#_144)
      * [简单使用](#_148)
      * [PageInfo](#PageInfo_177)  


 
--------
 
## []()通用Mapper

 通用 Mapper 是基于 MyBatis 的一个插件，它实现了大部分常用的增删改查方法  
 只需要继承它就能拥有它所有的通用方法，可以有效减少对XML中SQL语句的编写  
 然而对于复杂的查询语句还是需要手写 XML

 
--------
 
### []()使用方法

 1.maven依赖（引入jar包）

 
```
		<!-- 通用Mapper -->
		<dependency>
		  <groupId>tk.mybatis</groupId>
		  <artifactId>mapper</artifactId>
		  <version>3.3.9</version>
		</dependency>

```
 2.配置文件配置（两种方式）：  
 （1）Spring配置：替换原本的mapper.java扫描

 
```
		<bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- org.mybatis.spring.mapper.MapperScannerConfigurer -->
	        <property name="basePackage" value="cn.moke.demo.mapper"/>
	        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
	        <property name="properties">
	            <value>
	                mappers=tk.mybatis.mapper.common.Mapper
	            </value>
	        </property>
	    </bean>

```
 （2）mybatis配置

 
```
		<plugin interceptor="tk.mybatis.mapper.mapperhelper.MapperInterceptor">
	    <!--================================================-->
	    <!--可配置参数说明(一般无需修改)-->
	    <!--================================================-->
	    <!--UUID生成策略-->
	    <!--配置UUID生成策略需要使用OGNL表达式-->
	    <!--默认值32位长度:@java.util.UUID@randomUUID().toString().replace("-", "")-->
	    <!--<property name="UUID" value="@java.util.UUID@randomUUID().toString()"/>-->
	    <!--主键自增回写方法,默认值MYSQL,详细说明请看文档-->
	    <property name="IDENTITY" value="HSQLDB"/>
	    <!--序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval-->
	    <!--可选参数一共3个，对应0,1,2,分别为SequenceName，ColumnName,PropertyName-->
	    <property name="seqFormat" value="{0}.nextval"/>
	    <!--主键自增回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)-->
	    <!--<property name="ORDER" value="AFTER"/>-->
	    <!--通用Mapper接口，多个通用接口用逗号隔开-->
	    <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
	</plugin>

```
 3.自定义mapper继承通用mapper，继承的Mapper就拥有了Mapper 所有的通用方法

 
```
		public UserMapper extends Mapper<User>{}//User为对应数据库表的实体类

```
 4.定义UserService接口及其实现类UserServiceImpl

 
```
		@Service
	    public class UserServiceImpl implements UserService {
		    @Autowired
		    private UserMapper userMapper;//注入mapper
		
		    public int regist(User user) {
		       return userMapper.insert(user);//调用通用方法insert
		}

```
 
--------
 
### []()实体类注解

 实体类需要按照如下规则和数据库表进行转换（注解是JPA中的注解，需要在maven中添加依赖）：

 1.表名默认使用类名，驼峰转下划线，如UserInfo默认对应的表名为user_info

 2.表名可以使用@Table(name = “tableName”)进行指定  
 注：对不符合第一条默认规则的可以通过这种方式指定表名

 3.字段默认和@Column一样，都会作为表字段，表字段默认为Java对象的Field名字驼峰转下划线形式  
 注：可以使用@Column(name = “fieldName”)指定不符合第3条规则的字段名

 4.使用 **@Transient** 注解可以忽略字段，添加该注解的字段不会作为表字段使用

 5.使用 **@Id** 注解作为主键的字段，可以有多个@Id注解的字段作为联合主键；默认情况下，实体类中如果不存在包含@Id注解的字段，所有的字段都会作为主键字段进行使用(这种效率极低，且通用方法会受限)

 6.JPA通用策略生成器 **@GeneratedValue**  
 四种标准用法为:  
 TABLE：使用一个特定的数据库表格来保存主键  
 SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列  
 IDENTITY：主键由数据库自动生成（主要是自动增长型）  
 AUTO：主键由程序控制

 
```
		public class User {
			    @Id//设置为主键
			    @GeneratedValue(strategy = GenerationType.IDENTITY)//设置主键自增长
			    private Long id;
		}

```
 
--------
 
### []()通用方法详解

 1.Select

 
```
		List<T> select(T record);//根据实体中的属性值进行查询，查询条件使用等号
		T selectByPrimaryKey(Object key);//根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
		List<T> selectAll();//查询全部结果，select(null)方法能达到同样的效果
		T selectOne(T record);//根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
		int selectCount(T record);//根据实体中的属性查询总数，查询条件使用等号

```
 2.Insert

 
```
		int insert(T record);//保存一个实体，null的属性也会保存，不会使用数据库默认值
		int insertSelective(T record);//保存一个实体，null的属性不会保存，会使用数据库默认值

```
 3.Update

 
```
		int updateByPrimaryKey(T record);//根据主键更新实体全部字段，null值会被更新
		int updateByPrimaryKeySelective(T record);//根据主键更新属性不为null的值

```
 4.Delete

 
```
		int delete(T record);//根据实体属性作为条件进行删除，查询条件使用等号
		int deleteByPrimaryKey(Object key);//根据主键字段进行删除，方法参数必须包含完整的主键属性

```
 5.Example方法

 
```
		List<T> selectByExample(Object example);//根据Example条件进行查询
		//这个查询支持通过 Example 类指定查询列，通过 selectProperties 方法指定查询列
		
		int selectCountByExample(Object example);//根据Example条件进行查询总数
		
		int updateByExample(@Param("record") T record, @Param("example") Object example); 
		//根据Example条件更新实体 record 包含的全部属性，null值会被更新
		
		int updateByExampleSelective(@Param("record") T record, @Param("example") Object example); 
		//根据Example条件更新实体 record 包含的不是null的属性值
		
		int deleteByExample(Object example); 
		//根据Example条件删除数据

```
 
--------
 
## []()分页插件

 相应开源项目：[PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)

 
--------
 
### []()简单使用

 1.加载依赖：

 
```
		<dependency>
		    <groupId>com.github.pagehelper</groupId>
		    <artifactId>pagehelper</artifactId>
		    <version>x.x.x</version>
		</dependency>	

```
 2.mybatis配置文件引入插件：

 
```
		<!-- 配置分页插件 -->
	    <plugins>
	        <plugin interceptor="com.github.pagehelper.PageInterceptor">
	            <!-- 设置数据库类型 Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL六种数据库-->
	            <property name="helperDialect" value="mysql"/>
	        </plugin>
	    </plugins>

```
 3.可以在controller层或者service层使用即可：

 
```
		//获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(1, 10);//开始分页
        List<UserContent> list = userContentMapper.findByJoin(content);//紧跟着的第一个select方法会被分页
        Page endPage = PageHelper.endPage();//分页结束
        List<UserContent> result = endPage.getResult();

```
 
--------
 
### []()PageInfo

 可以使用PageInfo对上面查询出的结果进行包装，PageInfo中包含了非常全面的分页属性：

 
```
		PageInfo page = new PageInfo(list);
		//assertEquals方法:junit中的测试方法
		//可以判断 actualValue（实际值）与expectedValue（期望值）是否一致，不一致抛出异常
		assertEquals(1, page.getPageNum());
		assertEquals(10, page.getPageSize());
		assertEquals(1, page.getStartRow());
		assertEquals(10, page.getEndRow());
		assertEquals(183, page.getTotal());
		assertEquals(19, page.getPages());
		assertEquals(1, page.getFirstPage());
		assertEquals(8, page.getLastPage());
		assertEquals(true, page.isFirstPage());
		assertEquals(false, page.isLastPage());
		assertEquals(false, page.isHasPreviousPage());
		assertEquals(true, page.isHasNextPage());

```
   
  