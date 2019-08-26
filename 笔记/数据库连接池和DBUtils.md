---
title: 数据库连接池和DBUtils
date: 2019-02-01 17:08:49
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86742979](https://blog.csdn.net/MOKEXFDGH/article/details/86742979)   
    
  ### 文章目录


    * [数据库连接池](#_4)
      * [概述](#_6)
      * [DBCP连接池](#DBCP_22)
      * [装饰者模式](#_44)
      * [C3P0连接池](#C3P0_76)
      * [JdbcUtils小工具2.0](#JdbcUtils20_142)
      * [Tomcat配置连接池](#Tomcat_157)
    * [ThreadLocal](#ThreadLocal_188)
    * [DBUtils](#DBUtils_235)
      * [QueryRunner](#QueryRunner_240)
      * [ResultSetHandler](#ResultSetHandler_259)
      * [批处理](#_281)
      * [BaseServlet](#BaseServlet_293)  


 
--------
 
## []()数据库连接池

 
### []()概述

 1.用池来管理Connection，这可以重复使用Connection  
 连接池也是使用四大连接参数来创建连接对象

 2.池参数（所有池参数都有默认值）  
 初始大小：10个  
 最小空闲连接数：3个  
 增量：一次创建的最小单位（5个）  
 最大空闲连接数：12个  
 最大连接数：20个  
 最大的等待时间：1000毫秒

 3.实现的接口  
 连接池必须实现：javax.sql.DataSource接口  
 连接池返回的Connection对象，调用它的close()不是关闭，而是把连接归还给池

 
### []()DBCP连接池

 DBCP是Apache提供的一款开源免费的数据库连接池  
 需要使用两个jar包：commons-dbcp-1.4.jar和commons-pool-1.3.jar（以及mysql驱动包）  
 使用方法：

 
```
	BasicDataSource dataSource = new BasicDataSource();//创建连接池对象
	//配置四大参数
	dataSource.setUsername("root");
	dataSource.setPassword("123");
	dataSource.setUrl("jdbc:mysql://localhost:3306/mydb3");
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	//设置池参数
	dataSource.setMaxActive(20);//最大连接数
	dataSource.setMaxIdle(10);//最大空闲连接数
	dataSource.setInitialSize(10);//初始化连接数
	dataSource.setMinIdle(2);//最小空闲连接数
	dataSource.setMaxWait(1000);//最大等待毫秒数
	Connection con = dataSource.getConnection();//得到连接对象
	con.close();//把连接归还给池，dbcp对mysql中connection的close()方法进行增强（装饰设计模式）
	//con对象方法，只有close()方法是dbcp自己的

```
 
### []()装饰者模式

 1.对象增强的手段  
 （1）继承：会使类增多  
 被增强的对象固定的  
 增强的内容也是固定的  
 （2）装饰者模式  
 被增强的对象是可以切换的  
 增强的内容是固定的  
 （3）动态代理（AOP）  
 被增强的对象可以切换：Service  
 增强的内容也可以切换：事务处理

 2.学过的装饰类  
 BufferedInputStream：装饰流！创建我是一定要给我一个底层对象，然后我不管你给我的是什么流，我都会给它添加缓冲区！

 
```
	class MyConnection implements Connection {
		private Connection con;//底层对象，被增强对象

		public MyConnection(Connection con){//通过构造器传递底层对象！
	    		this.con = con;
		}

		public Statement createStatement() {依赖被增强对象
			return con.createStatement();
		}
		// 增强点
		public void close(){
			把当前连接归还给池！
		}
	}

```
 
### []()C3P0连接池

 1.C3PO使用方法（常用）：  
 需要两个jar包：c3p0-0.9.2-pre1.jar和mchange-commons-0.2.jar（以及mysql驱动包）  
 区别于DBCP：dbcp底层依赖装饰模式，而C3P0依赖动态代理（APO）

 
```
	ComboPooledDataSource ds = new ComboPooledDataSource();
	//基本配置
	ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb1");
	ds.setUser("root");
	ds.setPassword("123");
	ds.setDriverClass("com.mysql.jdbc.Driver");	
	//池配置
	ds.setAcquireIncrement(5);//每次的增量为5
	ds.setInitialPoolSize(20);//初始化连接数
	ds.setMinPoolSize(2);//最少连接数
	ds.setMaxPoolSize(50);//最多连接数
	
	Connection con = ds.getConnection();
	System.out.println(con);
	con.close();

```
 2.c3p0也可以指定配置文件，而且配置文件可以是properties，也是xml的  
 注：c3p0的配置文件名必须为c3p0-config.xml，且必须放在类路径下(src)

 
```
	<?xml version="1.0" encoding="UTF-8"?>
	<c3p0-config>
		<default-config><!-- 默认配置 -->
			<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
			<property name="driverClass">com.mysql.jdbc.Driver</property>
			<property name="user">root</property>
			<property name="password">123</property>
			<property name="acquireIncrement">3</property>
			<property name="initialPoolSize">10</property>
			<property name="minPoolSize">2</property>
			<property name="maxPoolSize">10</property>
		</default-config>
		<named-config name="oracle-config"><!-- 命名配置 -->
			<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
			<property name="driverClass">com.mysql.jdbc.Driver</property>
			<property name="user">root</property>
			<property name="password">123</property>
			<property name="acquireIncrement">3</property>
			<property name="initialPoolSize">10</property>
			<property name="minPoolSize">2</property>
			<property name="maxPoolSize">10</property>
		</named-config>
	</c3p0-config>

```
 
```
	//使用默认配置
	public void fun2() throws PropertyVetoException, SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
	}
	//使用oracle-config配置
	public void fun2() throws PropertyVetoException, SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource("orcale-config");//自动加载配置
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
	}

```
 
### []()JdbcUtils小工具2.0

 
```
	public class JdbcUtils{
		private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		public static Connection getConnection() throws SQLException{
			return dataSource.getConnection;
		}
		
		publuc static DataSource getDataSource(){
			return dataSource;
		}
	}

```
 
### []()Tomcat配置连接池

 1.t配置JNDI资源  
 JNDI（Java Naming and Directory Interface），Java命名和目录接口  
 JNDI的作用就是：在服务器上配置资源，然后通过统一的方式来获取配置的资源  
 配置JNDI资源需要到元素中配置子元素，例如配置c3p0连接池：

 
```
	<Context>  
  		<Resource name="myc3p0" 
			type="com.mchange.v2.c3p0.ComboPooledDataSource"
			factory="org.apache.naming.factory.BeanFactory"
			user="root" 
			password="123" 
			classDriver="com.mysql.jdbc.Driver"    
			jdbcUrl="jdbc:mysql://127.0.0.1/mydb1"
			maxPoolSize="20"
			minPoolSize ="5"
			initialPoolSize="10"
			acquireIncrement="2"/>
	</Context>  

```
 2.获取资源

 
```
	Context cxt = new InitialContext(); 
	DataSource ds = (DataSource)cxt.lookup("java:/comp/env/mydbcp");
	Connection con = ds.getConnection();
	System.out.println(con);
	con.close();

```
 
--------
 
## []()ThreadLocal

 ThreadLocal通常当作一个类的成员，当多个线程访问这个类时就访问ThreadLocal，每个线程都有自己的副本互不干扰  
 Spring把Connection放到ThreadLocal中  
 1.多线程读写问题，两种解决方法：  
 （1）synchronized

 
```
	public class demo1{
		private User user = new User();
		public void synchronized fun1(){
			user...
		}
		public void synchronized fun2(){
			user...
		}
	}

```
 （2）ThreadLocal

 
```
	public class demo2{
		ThreadLocal<User> user = new ThreadLocal<User>();
		public void fun1(){
			user.set("1");//存
			String s = user.get();//取
			user.remove();//删
		}
	}

```
 2.ThreadLocal的内部是Map

 
```
	class MyThreadLocal<T> {
		private Map<Thread,T> map = new HashMap<Thread,T>();
		public void set(T value) {
			map.put(Thread.currentThread(), value);
		}
	
		public void remove() {
			map.remove(Thread.currentThread());
		}
	
		public T get() {
			return map.get(Thread.currentThread());
		}
	}

```
 
--------
 
## []()DBUtils

 作用：对JDBC的简单封装，简化JDBC代码  
 DBUtils的Jar包：commons-dbutils-1.4.jar  
 核心类：QueryRunner、ResultSetHandler

 
### []()QueryRunner

 1.三个主要方法：  
 update()：执行insert、update、delete  
 query()：执行select语句  
 batch()：执行批处理

 2.增删改操作QueryRunner、update

 
```
	public void fun1() throws SQLException {
		QueryRunner qr = new QueryRunner();
		/*
		QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
		qr.update(sql,params);
		*/
		String sql = "insert into user values(?,?,?)";//insert、update、delete
		qr.update(JdbcUtils.getConnection(), sql, "u1", "zhangSan", "123");//可以控制事务
	}

```
 
### []()ResultSetHandler

 DBUtils提供了一个接口ResultSetHandler，它就是用来ResultSet转换成目标类型的工具  
 可以把ResultSet的数据放到一个List中，也可能想把数据放到一个Map中，或是一个Bean中  
 1.查询操作QueryRunner、query、BeanHandler（结果集处理器，实现了ResultSetHandler；把rs中的数据封装到指定类型的javabean中，，返回javabean）

 
```
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	String sql = "select * from t_stu where sid=''";
	Object[] params = {1001};
	// 把结果集转换成Bean（单行）
	Stu stu = qr.query(sql, new BeanHandler<Stu>(Stu.class),params);
	// 把结果集转换成Bean的List（多行，每行对应一个Stu）
	List<Stu> list = qr.query(sql, new BeanListHandler<Stu>(Stu.class));	
	// 把结果集转换成Map（单行）
	Map<String,Object> map = qr.query(sql, new MapHandler());
	// 把结果集转换成List<Map>（多行，每行对应一个map）
	List<Map<String,Object>> list = qr.query(sql, new MapListHandler() );
	// 把结果集转换成一列的List
	List<Object> list = qr.query(sql, new ColumnListHandler("name"));
	// 把结果转换成单行单列的值（通常用于select count(*) from t_stu->多少行记录）
	Number number = (Number)qr.query(sql, new ScalarHandler());

```
 
### []()批处理

 
```
	DataSource ds = JdbcUtils.getDataSource();
	QueryRunner qr = new QueryRunner(ds);
	String sql = "insert into tab_student values(?,?,?,?)";
	Object[][] params = new Object[10][]; //表示 要插入10行记录
	for(int i = 0; i < params.length; i++) {
		params[i] = new Object[]{"S_300" + i, "name" + i, 30 + i, i%2==0?"男":"女"};
	}
	qr.batch (sql, params);

```
 
### []()BaseServlet

 1.一个Servlet中有多个请求处理方法  
 方法：  
 （1）客户端发送请求时，必须多给出一个参数（method），用来说明要调用的方法，例如：[http://lcoalhost:8080/xxx/AServlet?m=addUser](http://lcoalhost:8080/xxx/AServlet?m=addUser)  
 （2）请求处理方法的签名必须与service相同，即返回值和参数，以及声明的异常都相同，Servlet：

 
```
	public class AServlet extends HttpServlet {
		public void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException{
			String methodName = request.getParameter("method");//获取参数，用来识别用户想请求的方法
			/*
			if(methodName.equals("addUser")){
				addUser(request,response);
			}else if(methodName.equals("editUser")){
				editUser(request,response);
			}else if(methodName.equals("deleteUser")){
				deleteUser(request,response);
			}
			*/
			if(methodName == null || methodName.trim().isEmpty()){
				throw new RuntimeException("没有传递method参数！");
			}
			Class c = this.getClass();//通过反射实现
			Method method = null;
			try{
				method = c.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
			}catch(Exception e){
				throw new RuntimeException("调用的方法不存在");
			}
			try{
				method.invoke(this,request,response);//调用方法
			}catch(Exception e1){
				System.out.println("调用的方法抛出异常");
				throw new RuntimeException(e1);
			}
		}
		
    		public void addUser(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
        		System.out.println("addUser...");
    		}
		public void editUser(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
			System.out.println("editUser...");
    		}
		protected void deleteUser(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
			System.out.println("deleteUser...");
    		}
	}

```
 2.BaseServlet完整版本（多请求+处理重定向或转发）:以后的Servlet都可以通过继承这个类实现简化代码（只用写处理方法）

 
```
	public abstract class BaseServlet extends HttpServlet {
    	public void service(HttpServletRequest request, HttpServletResponse response)
            	throws ServletException, IOException {
        	String methodName = request.getParameter("method");//获取参数，用来识别用户想请求的方法
        	if (methodName == null || methodName.trim().isEmpty()) {
            		throw new RuntimeException("没有传递method参数！");
        	}
        	Class c = this.getClass();//通过反射实现
        	Method method = null;
        	try {
            		method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        	} catch (Exception e) {
            		throw new RuntimeException("调用的方法不存在");
        	}
        	try {
            		String result = (String)method.invoke(this, request, response);
            		if(result == null || result.trim().isEmpty()) {//判断返回的值，是否要转发或重定向
                		return;
            		}
            		if(result.contains(":")){
                		int index = result.indexOf(":");
                		String begin = result.substring(0,index);
                		String path = result.substring(index+1);
                		if(begin.equalsIgnoreCase("r")){//"r:/index.jsp"
                    			response.sendRedirect(request.getContextPath()+path);
                	}else if(begin.equalsIgnoreCase("f")){
                    		request.getRequestDispatcher(result).forward(request,response);
                	}else{
                    		throw new RuntimeException("你指定的操作"+begin+"，无法支持");
                	}
            	}else{//没有冒号默认转发
                	request.getRequestDispatcher(result).forward(request,response);//"/index.jsp"
            	}
        	} catch (Exception e1) {
            		System.out.println("调用的方法抛出异常");
            		throw new RuntimeException(e1);
        	}
    	}
	}

```
   
  