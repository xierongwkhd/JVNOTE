---
title: java基础知识补充2
date: 2019-02-05 15:00:17
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/86765540](https://blog.csdn.net/MOKEXFDGH/article/details/86765540)   
    
  ### 文章目录


    * [基础补充2.0](#20_3)
      * [泛型](#_5)
      * [通配符](#_27)
      * [注解](#_56)
      * [反射泛型](#_128)
      * [反射注解](#_149)
      * [BaseDao](#BaseDao_179)  


 
--------
 
## []()基础补充2.0

 
### []()泛型

 1.泛型的使用：

 
```
	class A<T> {
 		private T bean;//泛型可在成员变量上使用
  		public T fun(T t) {}//泛型可以在类中的方法上（返回值和参数类型）使用！

  		public void fun2() {//泛型还可以在局部变量的引用类型上使用
    			T b = ...
    			new T();//不行的！
  		}
	}

```
 2.泛型的继承和实现  
 （1）继承：①子类不是泛型类，需要给父类传递类型常量；②子类是泛型类，可以给父类传递类型常量或者变量

 
```
	class A<T> {}
	class AA extends A<String> {}
	class AA2<E1,E2> extends A<E1>

```
 
### []()通配符

 1.通配符分类  
 无界通配：?  
 子类限定：? extends Object  
 父类限定：? super Integer

 2.常用场景：泛型  
 注：泛型引用和创建的两端，给出的泛型变量必须相同

 
```
	Object[] strs = new String[10];
	List<Object> objList = new ArrayList<String>();//报错
	public void print(List<Object> list){}//报错，无法打印所有类型的List集合
	//解决方法：通配符
	List<? extends Object> list = new ArrayList<String>();//不能再new的一方使用通配符
	public void print(List<?> list){}	

```
 3.通配符缺点*  
 使变量使用上不再方便  
 无界：参数和返回值为泛型的方法，都不能使用  
 子类：参数为泛型的方法不能使用，返回值为泛型的方法可用  
 父类：返回值为泛型的方法不能使用，参数为泛型的方法可用

 4.比较通配符

 
```
	boolean addAll(Collection<E> c);
	boolean addAll(Collection<? extends E> c);  

```
 
### []()注解

  
  2. 注解的作用：替代xml配置文件  
      （1）语法：@注解名称  
      （2）servlet3.0中，就可以不再使用web.xml文件，而是所有配置都使用注解  
      （3）注解是由框架来读取使用的  
      （4）java中使用过的注解：  
      @Overrid:作用在方法上，当方法不是重写父类的方法时回报错  
      @Deprecated:作用在方法上，标记该方法为作废的方法  
      @SuppressWarnings:作用在方法上，压制警告
     
       
  4. 注解的使用  
      （1）定义注解类：框架的工作  
      （2）使用注解：我们的工作  
      （3）读取注解（反射）：框架的工作
     
        3.定义注解：

 
```
	 public @interface MyAnn{}//所有的注解都是Annotation的子类

```
 4.使用注解的位置，类、方法、构造器、参数、局部变量、包，如：

 
```
	@MyAnn
	class demo{}//使用自己定义的注解

```
 5.注解的属性  
 （1）定义属性: 类型 名称()

 
```
	@interface MyAnno1 {
		int age() default 25;//设置age默认值为25
		String name();
    	}

```
 （2）使用注解时给属性赋值

 
```
	@MyAnno1(age=20, name="moke")

```
 （3）value属性的特权：当使用注解时，如果只给名为value的属性赋值时，如：@MyAnno1(“hello”)//给MyAnno1的注解定义的String类型的value赋值  
 （4）注解属性的类型：8种基本类型、String、Enum、Class、注解类型、以上类型的**一维**数组类型

 
```
	@MyAnno{
		a=MyEnumu.A,
		b=String.class,
		c=@MyAnno2(),
		d={"1","2"},
		f=100
	}
	@interface MyAnno{
		MyEnumu a();
		Class b();
		MyAnno2 c();
		String[] d();
		int[] f();
	}

```
 6.注解的作用目标限定与保存策略限定  
 （1）能作用在类上，不能作用类中方法

 
```
	@Target(value={ElementType.METHOD})//Target中的枚举ElementType,多种限定,可省略
	@interface MyAnno{}

```
 （2）三种保留策略：  
 源代码文件（SOURCE）：注解只在源代码中存在，当编译时就被忽略了  
 字节码文件（CLASS）：注解在源代码中存在，然后编译时会把注解信息放到了class文件，但JVM在加载类时，会忽略注解  
 JVM中（RUNTIME）：注解在源代码、字节码文件中存在，并且在JVM加载类时，会把注解加载到JVM内存中（唯一可反射的注解）

 
```
	@Retention(RetentiomPolocy.RUNTIME)
	@interface MyAnno{}

```
 
### []()反射泛型

 
```
	abstract class A<T> {
		public A() {
		/*
		 * 在这里获取子类传递的泛型信息(String)，要得到一个Class！
		 */
			Class clazz = this.getClass();//得到子类的类型
			Type type = clazz.getGenericSuperclass();//获取传递给父类参数化类型
			ParameterizedType pType = (ParameterizedType)type;//它就是A<String>				
			Type[] types = 	Type.getActualTypeArguments();//它就是一个Class数组
			Class c = (Class)types[0];//它就是String
			/*
			Class c = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			*/
			System.out.println(c.getName());
		}
	}
	class B extends A<String> {}

```
 
### []()反射注解

 1.前提：保留策略必须是RUNTIME  
 2.四种作用目标：  
 （1）类上的注解，需要使用Class来获取  
 （2）方法上的注解，需要Method来获取  
 （3）构造器上的注解，需要Construcator来获取  
 （4）成员上的，需要使用Field来获取  
 注：Method、Construcator、Field有共同的父类：AccessibleObject

 3.反射注解步骤  
 （1）获取作用目标

 
```
	@MyAnno(name="zhangsan",age=20)
	class A{
		@MyAnno(name="wangwu",age=25)
		public void fun(){}
	}
	
	public class demo{
		public void test(){
			Class<A> c = A.class;
			MyAnno myAnno = c.getAnnotation(MyAnno.class);//通过Class的方法获取注解
			Method  method = c.getMethod("fun");
			MyAnno myAnno = method.getAnnotation(MyAnno.class);//通过method获取注解
			/*不知道类型，可以使用getAnnotations()，即返回目标上所有注解
			*/
		}
	}

```
 
### []()BaseDao

 1.BaseDao（初步）

 
```
	class BaseDao<T> {
		private QueryRunner qr = new TxQunner();
		private Class<T> beanClass;
		Field[] fs;
		Table table;
		Column column;
		
		public BaseDao(){
			beanClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			//获取泛型的参数
		}
		
		public void add(T bean) throw SQLException{
			fs = beanClass.getDeclaredFields();//获取该类的所有声明的字段
			table = beanClass.getAnnotation(Table.class);
			String sql = "insert into "+ table.value()+ "value(";//通过注解获取表名
			for(int i=0;i<fs.length;i++){
				sql +="?";
				if(i<fs.length-1){
					sql += ",";
				}
			}
			Object[] params = new Object[fs.length];
			ID id = beanClass.getAnnotation(ID.class);
			column = beanClass.getAnnotation(Column.class);
			params[0] = id.value();
			for(int j=1;j<params.length;j++0){
				params[j] = column.get(j-1);
			}
			try{
				qr.update(sql,params);
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
			//类似的还可写得update,delete,load,findAll等方法
		}
		
	}

```
 2.注解类：

 
```
	@Table("tb_user")//值为当前类对应的表
	public class User{
		@ID("u_id")//当前属性对应的列名，且是主键
		private String uuid;
		@Column("uname")
		private String username;
		@Column("pwd")
		private String password;
	}
	UserDao extends BaseDAO<User>{
		public void addUser(User user) {
			super.add(user);
		}
	}

```
   
  