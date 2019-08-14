---
title: Java基础学习笔记（完整版）
date: 2018-05-12 15:46:15
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/80292015](https://blog.csdn.net/MOKEXFDGH/article/details/80292015)   
    
  github：[https://github.com/xierongwkhd/JVNOTE](https://github.com/xierongwkhd/JVNOTE)  
 目前情况  
 暂定学习路线：  
 基础√-接口√-常用类√-GUI√-异常处理√-集合框架√-IO流√-反射√-网络编程√-JavaWeb-…  
 最近在做一个简单的人事管理系统（swing + 一点mysql）

 注：上面说的练习小项目写完了，在我的github上，希望大家一起学习交流

 

### 文章目录


    * [基础](#_11)
    * [设计模式](#_270)
    * [接口](#_300)
    * [常用类](#_318)
    * [异常](#_479)
    * [正则表达式](#_563)
    * [集合](#_588)
    * [泛型](#_756)
    * [IO流](#IO_813)
    * [多线程](#_1239)
    * [网络编程](#_1629)
    * [反射机制](#_1880)  


 
--------
 
## []()基础

 1.HelloWorld

 
```
public class HelloWorld{
	
	public static void main(String[] args){
		
		System.out.println("Hello World!");//ln换行->println换行输出
	}
}

```
 CMD编译：javac xxx.java->xxx.class(字节码文件)【C/C++编译后为可执行文件】  
 运行：java HelloWorld(xxx)  
 字节码：一套在JVM中执行的高度优化的指令集->可移植性

 2.Java基本元素：  
 空白分隔符->空格，tab键，换行符  
 关键字（50）->访问控制

 
```
	private protecred public

```
 类，方法和变量修饰符

 
```
	abstract class extends final implements interface
	native new static strictfp synchronized transient volatile

```
 程序控制

 
```
	break continue return do while if else for instanceof switch case default

```
 错误处理

 
```
	try catch throw throws

```
 包相关

 
```
	imprt package

```
 基本类型

 
```
	boolean byte double float int long short
	(null true false不是关键字，是单独的标识类型)

```
 变量引用

 
```
	super this void

```
 保留字

 
```
	goto const

```
 标识符->类，方法，变量的名字  
 注释-> 单行：//xxx 注释块： /* xxx _/ 文档注释： /_* xxx */  
 分隔符  
 ()圆括号 定义方法的参数表。定义表达式的优先级。  
 {}花括号 初始化数组，定义程序块，类，方法。  
 []方括号 声明数组类型。  
 ; 分号 表示一个语句的结束。  
 , 逗号 变量声明时用于分隔多个变量。  
 . 点 用于软件包和子包或类，对象和变量或方法分隔。

 3.Eclipse  
 src：存放源文件  
 bin：存放编译后的字节码文件  
 .classpath .project:项目配置文件

 4.八大数据类型  
 整形：byte short int long  
 字符型：char（Unicode）  
 浮点型：float(单精度 4字节) double(双精度 8字节)  
 布尔型：boolean(ture/false)  
 定义常量关键字：final（习惯上常量用大写）

 5.数据类型转换  
 自动转换：条件->目的类型比原来的类型要大  
 两种类型是相互兼容的  
 byte->short  
 short->int  
 char->int  
 int->long  
 int->double  
 float->double  
 强制类型转换：double a=3.55555  
 int b=(int) a  
 b=3

 6.赋值float类型 float xxx->xxx=3094.5F  
 局部变量->必须手动进行初始化  
 类变量（static）、实例变量->编译器自动初始化

 10.字符串  
 字符串变量：String类（String xxx=“Hello World!” System.out.println(xxx)）>存在常量池  
 字符串的连接： 字符串1 + 字符串2（用+连接两个字符串）  
 ①Sysytem.out.println(“xxx1”+“xxx2”)  
 ②String xxx3=xxx1+xxx2 （String xxx3=" “+xxx1+”\n"+xxx2）  
 看字符串长度：System.out.println(xxx.length())  
 转义字符：\n \t \r

 字符串处理：  
 ①求子串String xxx1=" "->xxx2=xxx1.substring( , )【前闭后开】  
 ②测试字符串是否相等

 
```
Object equals：比较内存地址（相当于等号==）s1==s2
String equals：比较内容  s1.equals（s2）
String a="xxx"; String b="xxx";  a==b(都在常量池里面，同一内存地址)
String c=new String("xxx") 
（new出来的都在堆内存，且都是【新】对象，有不同的堆内存地址）

```
 ③字符串编辑->字符串内容不可变，改变的是引用  
 ④字符串其他操作：查看API

 11.运算符  
 基本算术运算符、自增自减运算符 # i++先用后加 ++i先加后用  
 模运算符-> %求两个整数相除的余数 #包含浮点类型取模不准确，精度高的类型用BigDecimal类型  
 关系运算符(== != > < )、逻辑运算符(&逻辑与 |逻辑或 !逻辑非 &&短路与 ||短路或)  
 三元运算符： (条件)?(符合):(不符合)  
 运算符优先级 #()优先级最高

 12.流程控制

 
```
switch(表达式){
    case value1:
	break;
    case value2:
	break;
    default:
}//value值和表达式类型一致

```
 循环结构：while/for for(初始化;条件;迭代运算){循环语句;}  
 break：强制当前循环终止  
 continue：停止本次循环，继续执行剩下的循环  
 return：从当前的方法中推出。执行后，方法内剩余的代码都不会执行

 12.数组 //一组相同类型的数的集合  
 创建数组： ①//声明数组  
 a.类型 数组名 [];-------- double array1[length]  
 b.类型[] 数组名;--------- String[] array2,array3 （常用）

 
```
array1=new double[5];
array1[0]=1;//数组元素赋值

```
 初始化数组： ①元素一个个赋值  
 ②int[] array1={1,2,3,4};  
 ③多维数组运用多重循环对数组进行赋值

 获取数组长度：system.out.println(array1.length);  
 #创建一个Scanner类的对象，用它来获得用户的输入

 
```
import java.util.Scanner;				//使用Scanner之前先导包
Scanner sc=new Scanner(System.in);			//创建
for(student=0;student<temp.length;student++){
temp[student]=sc.nextDouble();			//输入
sum+=temp[student];
}

```
 数组的复制：通过赋值操作（array1=array2），两个引用指向同一个数组（地址）  
 多维数组：  
 *Java中只存在一维数组，多维数组则是数组中的数组（数组中的元素存放另一数组）

 
```
int[][] n=new int[5][5];
int[][] n1={
{1,2,3,9}
{4,5,6,9}
{7,8,9,9}	
};
不规则数组：数组的各个元素存放不同长度的数组

```
 13.类的一般形式  
 类：事物的集合和抽象。代表这类事物所共有的一些行为和属性  
 class 类名{ //类  
 类型 变量名; //属性  
 类型 方法名（参数）{ //方法  
 方法内容  
 }  
 ···  
 }  
 修饰符：private-----------只有在本类中可以看见  
 protected---------在本类或者是同一个包里面可见  
 public------------对于所有类都可见  
 默认（无修饰符）–在本类或者是一个包里面可见//有别于protected  
 父类=超类=基类、子类=派生类

 14.方法  
 无返回值，定义必须写void  
 有返回值，则定义的方法类型必须和方法体内返回值的类型相同  
 例： public String returnString(){  
 return “返回值是字符串类型”; }  
 主方法：程序的入口。public static void ain(String[] args){ }  
 构造方法：  
 用于初始化参数。所有的数字变量全部设置为0。所有的boolean类型全部设置为false  
 所有的对象类型赋值为null。  
 People p=new People(); //People()->构造方法，编译器自动初始化People类内的变量  
 也可以自定义构造方法（People()）没有返回值,且编译器不提供默认的构造方法（自写）  
 有参的构造方法：

 
```
 People(String name,String sex,int age){
	this.name=name;
	this.sex=sex;
	this.age=age;		//this:当前类
 }
 People p=new People(" "," ", );

```
 方法的重载：  
 具有相同的方法名称，但具有不同的参数列表（参数的数量、类型、次序等）  
 构造方法重载：//简化->this(name,age); //调用本类中其他构造器

 
```
	 super();//调用副类构造器（object类）	

```
 15.对象（类的实例）

 
```
       com=new Computer();
       即Computer com=new Computer();

```
 对象类型的参数传递：引用类型的传递  
 基本数据类型作为参数直接操作，引用类型作为参数，操作的是引用指向的堆内存中的对象

 
```
Animal a= new Animal();
prinftAnimal(a);//声明的prinftAnimal(Animal a)函数

```
 16.关键字  
 static：静态变量->属于类的变量//可以直接访问 类名.变量名  
 静态方法->用static修饰的方法，访问同静态变量，不能访问非静态变量//非静态方法可以访问静态变量  
 静态常量-> public static final int x=1;  
 final：  
 ①修饰恒定不变的变量（不可改变且大写）  
 ②修饰方法->任何继承类无法重写覆盖该方法，但可以重载 #继承class xxx extends xxx  
 ③修饰类->该类不能作为任何类的父类，且类中的方法全被定义为final类型

 包：命名包/未命名包（一般不用）

 17.三大特性封装、继承、多态  
 封装：通过private对信息进行封装，通过setXXX()方法进行访问  
 //方法中可以加入条件，即对用户开放的信息进行限制

 继承：class xxx1 extends xxx2 //extends只能继承一个类，JAVA不支持多重继承  
 在xxx1中可以调用父类的方法，可以重写继承类（父类）xxx2中的方法，也可以增加自己的方法  
 子类继承父类，编译器默认加上 super(); 调用父类的无参构造器（先父类再子类）  
 #调用有参构造方法必须自己加 super（参数）;  
 #super();必须放在方法第一个语句中

 
```
		       Tiger a=new Tiger()----Animal a=new Tiger()

```
 18.抽象类->为子类提供一个规范（子类可定义不同的方法体）  
 public abstract class 类名{类体};/抽象类，抽象类中至少有一个抽象方法（可以有普通方法）  
 public abstract void test();//抽象方法没有方法体，即规范（由继承的子类定义方法）  
 应用：  
 @Override//检测是否重写成功  
 一个类继承了抽象类，就必须重写抽象类中的所有抽象方法（如果为抽象类继承抽象类则不用）  
 #Animal instanceof mouse 判断该继承Animal的类是否为mouse类（instanceof）

 19.内部类：一个类被嵌套定义在另一个类中，即为内部类，包含内部类的类为外部类  
 内部类相当于外部类的成员变量  
 Outer out=new Outer();//先构造外部类  
 Outer.Iner in=out.new Inner();//才能构造内部类  
 匿名内部类：实现类的时候没有名字**************继承父类并重写方法，即为子类  
 局部内部类：在方法等局部位置的类，局部内部类可以访问局部（方法中）的final类型的变量和外部类所有成员变量【为什么只能访问final类？？？】  
 静态内部类：内部类有static修饰符的内部类，  
 内部可以声明static成员变量（非静态内部类不可以）  
 不可使用外部类的非静态成员变量  
 创建对象时，不需要其外部类的对象#StaticInner.Inner i=new StaticInner.Inner  
 ??为什么内部类可以使用外部类的成员变量？？  
 引用外部类的对象：内部类：this.count 外部类：Outer.this.count  
 内部类的继承：

 
```
   public class Test extends A.B{
		public Test(A a){
		a.super();   }         } //先对外部类进行实例化

```
 
--------
 
## []()设计模式

 15*.设计模式  
 设计模式：解决某一种问题的一种优化的思想，是一种行之有效的解决方式//总共有23钟模式

 单例设计模式：  
 解决的问题->保证一个类的对象在内存中的唯一性  
 应用场景->多个程序都在操作同一个配置文件时，需要程序A操作后的结果，程序B要知道并继续基于A操作后的结果进行操作  
 //前提：数据都存储在配置文件对象中，要求程序A和程序B操作的配置文件对象时同一个

 
```
	/*饿汉模式*/
 class Single{
 	private static final Single s= new Single();//创建本类对象
 	private Single(){}//构造函数私有化,使外部无法通过new创建其对象
 	public static Single getInstance(){//定义一个返回该对象的方法，使程序可以获取(为了可控)
 		return s;
 	}
 }
 /*另一种形式，延迟加载方式（懒汉模式）*/
 class Single{
 	private static final Single s= null;//创建本类对象
 	private Single(){}//构造函数私有化,使外部无法通过new创建其对象
 	public static Single getInstance(){//定义一个返回该对象的方法，使程序可以获取(为了可控)
 		if(s==null)
 			s = new Single();
 		return s;
 	}
 }

```
 
--------
 
## []()接口

 20.接口（完全抽象，里面只能有抽象方法和常量）  
 interface 定义接口的修饰符（interface xxx）只能被默认或public修饰  
 int i=1------>public static final int i=1(变量会被设置成公有的静态的常量)  
 方法的修饰符->public abstract即抽象修饰符  
 实现：class 类名 implemets 接口1,接口2,接口3{ 方法 };//区别于extends，  
 #如果这几个接口都有相同的方法和变量，那么通过 接口名.变量名 的形式访问  
 相同的方法将被其中的一个接口使用（只重写一次）  
 注1.实现的子类为接口中所有的方法具体实现  
 2.重写时：  
 ①子类的重写方法不能抛出更大的异常*  
 ②子类的重写方法不能有更小的访问范围，即public->protect  
 3.保持相同的返回类型  
 *接口类型引用调用test2 t=new test2(); Jia mJia=t;//从对象t中  
 通过创建mJia接口调用Jia方法

 
--------
 
## []()常用类

 21.常用类(对象API)  
 Object类：子类初始化构造（super()）->父类  
 父类初始化->Object//所有类的根类，具备所有对象都具备的共性内容  
 #常用共性方法：①equals()两种对象我是否相等 [//p1.equals](//p1.equals)(p2) ->比较地址<-  
 **equals()一般都会覆盖上述方法，根据对象的特有内容，建立判断对象是否相同的依据  
 （自0己建立比较形式）  
 ②hashCode()->重写equals()时需要重写此方法，相等对象必须具备相等的哈希码  
 ③getclass()->获取当前对象的字节码文件对象Person.class  
 Class class1 = p.getClass();  
 ④toString()->getClass().getName()+"@"+Integer.toHexString(p.hashCode())  
 //通常重写

 String类：  
 ①字符串对象一旦被初始化就不会改变，储存在字符串常量池中//s1=xxx;s2=xxx->s1 == s2  
 ②String s = “abc”–String s1 = new String(“abc”)->s与s1不同地址，""可以共享，new在堆内存（特点）  
 ③字符串对象中用equals()比较的是内容  
 ④构造函数（API）  
 常见功能：  
 1.获取->获取字符串的长度

 
```
int length();

```
 根据位置获取字符

 
```
char charAt(int index)

```
 根据字符获取字符串中的位置

 
```
int indexof(int ch)   
int indexof(int ch,int fromIndex)//从指定位置进行ch查找
int indexof(String atr)·····//API中查看

```
 2.转换->将字符串变成字符串数组

 
```
String[] split(String regex)//正则表达式

```
 将字符串变成字符 数组

 
```
char[] toCharArray()

```
 将字符串变成字节 数组

 
```
byte[] bytes getBytes()//s = " "

```
 将字符串这种的内容替换

 
```
replace(char oldchar,char newchar)

```
 将字符串两端的空格去除

 
```
String trim()//连接String concat(string)->同+

```
 3.比较->compareTo---------------intern方法  
 判断字符串是否相同：  
 boolean equals(Object obj);  
 boolean equlasIgnoreCase(String str);//忽略大小写比较字符串内容

 判断字符串是否包含：  
 boolean contains(String str);

 判断字符串是否以指定字符串开头或结尾：  
 boolean startsWith(String str);  
 boolean endsWith(String str);

 #SringBuffer类#：字符串缓冲区，用于存储数据的容器  
 特点： ①长度可变（初始为16个字符，不够则自动增加，可以通过构造器自定义初始化长度）  
 ②可以存储不同类型数据  
 ③添加到对象中都转成字符串  
 ④可以对字符串进行修改

 常用功能：  
 添加：append(data);//data->除了byte和short的其他数据类型

 
```
 StringBuffer sb = new StringBuffer();//创建缓冲区对象
 sb.append(4).append(false).apend("haha");//append返回的还是StringBuffer对象

```
 插入：insert(index,data);  
 删除：StringBuffer delete(start,end);//包含头，不包含尾 0-length  
 StringBuffer deleteCharAt(int index);  
 查找：char charAt(index);  
 int indexOf(String);  
 int lastIndexOf(String);  
 修改：StringBuffer replace(start,end,String);  
 void setCharAt(index,char);  
 其他方法：  
 setLength(len);设置缓冲区的大小  
 reverse();//y数据反转（首位对调）

 #SringBuilder类#：与StringBuffer作用相同，但是线程是不同步的，速度更快，主要用于单线程

 #System类#：不能被实例化，都是静态方法  
 System.err;//字段摘要，“标准”错误输出流  
 System.out;//返回的是PrintStream对象，所以可以调用其方法->System.out.println();  
 [System.in](http://System.in);//InputStream

 常见方法：  
 long currentTimeMillis();//返回以毫秒为单位的当前时间，常用于计算程序或方法执行时间  
 getProperties();//获取系统所有的属性信息（getPorperty(String):通过指定的键获取系统信息），并以Properties集合（详见IO流）返回

 
```
 Properties prop = System.getProperties();
 Set<String> nameSet = prop.stringPropertyName();
 for(String value : nameset){
 	String value = prop.getProperty(name);
 	System.out.println(name+"::"+value);
 }

```
 line.separator;//系统行分隔符，适用于任何系统

 
```
 System.out.println("hello"+System.getProperty("line.separator")+"world");
 private final static String line_separator =  System.getProperty("line.separator");//通常用法，在使用时调用

```
 setProperty(String str1,String str2);//给系统设置一些属性信息，其它程序都可以使用

 #Runtime类#：没有构造方法，该类不能实例化，且有非静态方法，则说明提供了返回该类对象的静态方法(单例设计模式)

 
```
 Runtime r = Runtime.getRuntime();//获得程序中的Runtime对象
 Process p = r.exec("notepad.exe c:\\abc.java");//可以开启硬盘上的执行文件并解析abc.java文件（有IO异常），返回一个进程
 p.destroy();//只能摧毁Runtime对象创建的线程

```
 #Math类#：提供了操作数学运算的方法，都是静态的  
 常用方法：  
 double cell(double);//返回大于参数的最小整数  
 double floor(double);//返回小于参数的最大整数  
 double round(double);//返回四舍五入的整数  
 int max(int a, int b);//取最大，可以是其它数据类型  
 pow(a,b);//a的b次方  
 double random();//返回带正号的double，该值大于等于0.0且小于1.0（伪随机）  
 同时还有随机数对象：Random r = new Random();

 #Data类#：日期对象  
 构造方法： Data();和Data(long time);//time为某一时间的毫秒值（可以由System类获得）  
 日期对象和毫秒值之间的转换：

 
```
 //毫秒值->日期对象（可以对日期的年月日秒等字段进行操作）
 new Data(timeMillis);
 setTime();
 //日期对象->毫秒值（可以通过具体数值进行运算）
 getTime();

```
 对日期对象进行格式化：DataFormat类

 
```
 Date date = new Date();
 DateFormat dateFormat = DateFormat.getDateInstance();//2018-10-15->MEDIUM（默认风格）
 dateFormat = DateFormat.getDateTimeInstance();////2018-10-15 14:17:30
 dateFormat = DateFormat.getDateTimeInstance(DataFormat.LONG,DataFormat.LONG);//分别指定日期和时间的风格
 dateFormat = DateFormat.getDateInstance(DataFormat.LONG);//2018年10月15号
 dateFormat = DateFormat.getDateInstance(DataFormat.FULL);//2018年10月15号 星期一
 dateFormat = DateFormat.getDateInstance(DataFormat.short);//18-10-15
 /*自定义风格：用到DataFormat的子类 SimpleDateFormat*/
 dateFormat = new SimpleDateFormat("yyyy--MM--dd");//2018--10--15
 
 String str_date = dateFormat.format(date);//将日期对象转换成日期格式的字符串
 /*将日期格式的字符串转换成日期对象*/
 String str = "2018-10-15";
 DataFormat dateFormat = DataFormat.getDateInstance();//根据字符串的风格来定义
 Date date = dateFormat.parse(str);

```
 操作日历字段提供方法：Calendar类

 
```
 Calendar c = Calendar.getInstance();
 int year = c.get(Calendar.YEAR);//MONTH（从零开始）,DAY_OF_MONTH,DAY_OF-WEEK等同理，为键值对的形式
 c.set(2018,10,15);//设置时间，不设置则默认为系统时间
 c.add(Calendar.YEAR,2);//在原本的年份上加两年

```
 
--------
 
## []()异常

 22.异常：运行时期发生的不正常情况  
 处理异常方式：用类的形式对不正常情况进行描述和封装对象，描述不正常情况的类则称为异常类,不同的问题用不同的异常类进行具体描述（描述的类很多，将其共性向上提取，形成体系即继承，父类Throwable）

 对异常进行捕捉或声明，使其抛出  
 所有类分成两大类：Error类（不可处理），Exception（可处理）  
 抛出异常：前提–Throwable父类下的子类都可抛且应该抛出  
 凡是可以被throws，throw关键字操作的类和对象都具有可抛性  
 #数组角标越界throw new ArrayIndexOutOfBoundsException(index)//默认抛出  
 自己定义

 
```
throw new ArrayIndexOutOfBoundsException(" "+index)

```
 已存在异常直接抛出，如

 
```
throw new ArrayIndexOutOfBoundsException

```
 自定义异常：

 
```
class FuShuIndex extends Exception

```
 声明： 抛出异常时，必须先声明【定义处理的方法或调用的方法都得声明throws FuShuIndex】  
 throw抛出异常对象，throws抛出异常类【可抛出多个】  
 throw new FuShuIndex(" "+index)->自定义异常类中创建构造方法（参数为String类）  
 并super();//调用父类构造器

 
```
    class FuShuIndex extends Exception
    {
	FuShuIndex(){};
	FuShuIndex(String mag){ super();}
     }

```
 ★编译时不检测异常（运行时异常）：Exception中的RuntimeException和其子类  
 自定义异常时继承Exception或RuntimeException  
 捕捉：对异常进行针对性处理（自己能处理的异常）  
 格式：

 
```
 try
		 {  //需要被检测异常的代码
		  }
		 catch(异常类 变量)//该变量用于接收发生的异常对象，异常类为throws出的类
		 {  //处理异常的代码
		  }
		 finally
		 {  //一定会被执行的代码*
		  }

```
 ??对象当作字符串打印 对象.toString()  
 多catch情况->throws几个异常就用多少catch进行捕捉处理  
 #catch(Exception e)–>处理未知异常  
 多catch时父类的catch放在最后

 异常处理原则：  
 1.函数内部如果抛出需要检测的异常，那么函数上必须要声明。  
 否则必须在函数内用trycatch捕捉，否则编译失败。  
 2.如果调用到了声明异常的函数，要么trycatch要么throws，否则编译失败  
 3.什么时候catch，什么时候trows？  
 功能内部可以解决，用catch  
 解决不了，用throws告诉调用者，由调用者解决  
 4.一个功能如果抛出多个异常，那么调用时，必须有多个catch进行针对性处理  
 finally代码块：连接数据库-查询-关闭连接->通常用于关闭（释放）资源   
 //退出虚拟机 System.exit(0)  
 *特点：组合try catch finally  
 try finally->异常无法直接catch处理，但是资源需要关闭  
 异常转换：catch一个异常抛另一个异常//异常的封装

 在数据库中的应用：

 
```
 void addData(Data d)throws SQLexception
		{
			连接数据库
			try
			{
			添加数据//出现异常 SQLExcption();
			}
			catch(SQLException e)
			{}
			关闭数据库
		}

```
 异常注意事项：  
 1.子类在覆盖父类方法时，父类的方法如果抛出了异常，那么子类的方法只能抛出父类的  
 异常或该异常的子类。  
 2.如果父类抛出多个异常，那么子类只能抛出父类异常的子集  
 //如果父类的方法没有抛出异常，那么子类覆盖时绝对不能抛，只能try

 
--------
 
## []()正则表达式

 23.正则表达式//用于操作字符串数据【特定的符号来体现】  
 ①定义正则表达式规则：String regex = “[1-9][0-9]{4,14}”;//正则表达式:第一个数1-9，4-14位数0-9  
 ②boolean b = qq.matches(regex);//验证字符串qq是否符合规则：matches(regex)  
 ※各种符号意义（API）  
 正则表达式对字符串的常见操作：  
 1.匹配->

 
```
		   matches方法   /*   "1[359]\\d{9}"   */

```
 2.切割->

 
```
		   splift方法    split(String regex)   //组：((A)(B(C)))

```
 3.替换->

 
```
           replaceALL()方法    replaceALL(String1,String2)

```
 4.获取->

 
```
		  Patern p = Pattern.compile("a*b");//将正则规则进行对象的封装
		  Matcher m = p.matcher("aaaaab");
		  //通过正则对象的matcher方法字符串相关联。
		  获取对字符串操作的匹配器对象Matcher
		  boolean b = m.matcher();//通过Matcher匹配器对象的方法对字符串进行操作 

```
 
--------
 
## []()集合

 24.集合  
 1.存放多个对象（封装特有数据）的容器  
 2.集合可变长度即存放的对象个数不确定//区别于数组  
 3.不能存储基本数据类型值（数组）

 集合容器因为内部数据结构的不同，有多种具体容器，不断的向上抽取，就形成了集合框架  
 框架的顶层为：Collection接口(根接口)//使用时要导包import  
 其常见方法：

 
```
	 添加 boolean add(Object obj);
	     boolean addAll(Collection coll);
	 删除 remove(Object obj);//改变集合长度（删除元素，返回boolean）
	     removeAll(Collection coll);
	     //c1.removeAll(c2)将c1,c2集合中相同元素从c1集合中删除
	     void clear();//清空集合
	 判断 boolean contains(Object obj);//是否在obj中存在相同元素
	     boolean containsAll(Collection coll);
	     boolean isEmpty();//判断是否为空
	 获取 int size();
		 Iterator iterator();//迭代器：取出元素（Collection子接口的对象通用）
	 其他 boolean retainALL(Collection coll);//取交集
	      Object[] toArray{};//将集合转成数组
	 等共性方法API  ##必须掌握##

```
 迭代器的使用：

 
```
   Collection coll = new ArrayList();
   coll.add("abc1");
   coll.add("abc2");
   coll.add("abc3");
   Iterator it = coll.iterator();//获取集合中的迭代器对象
   System.out.println(it.next());//abc1
   System.out.println(it.next());//abc2------->next() /* 区别于直接print coll */
  /*while(it.hasNext()){
	System.out.println(it.next());
     }//abc1
	abc2------>hasNext() */ 取出元素后，无法在用next()取出 */

```
 （基本原理）：  
 取出元素的方法封装成对象（即iterator），通过此对象中的方法（next(),hasNext()）对元素进行操作  
 //迭代器对象是在容器中进行内部实现的Collectiuon下的子接口：List,Set//常用，也可以自己定义链表等各种数据结构  
 ①List：有序（存入和取出的顺序一致），元素都有索引（角标），元素可以重复  
 ②Set：元素不能重复，无序。  
 List特有的常见方法（特点是可以操作角标）:

 
```
void add(index,element);
void add(index,collection);//添加
Object remove(index);//删除
Object set(index,element);//修改
Object get(index);//获取
listIterator(); ListIterator it = list.listIterator()-->获取列表迭代器对象

```
 //在迭代过程中不能使用集合操作，此时使用Iterator的子接口ListYterator来操作迭代元素  
 **List集合**常用对象：  
 ①Vector:内部是数组数据结构。是同步的。 //特有方法：带有elements字样的方法  
 Vector中的枚举elements()方法（接口：Enumeration）,与迭代器（接口：Iterator）功能重复  
 ②ArrayList：内部是数组数据结构。是不同步的。–>替代Vector//查询速度快  
 ArrayList存自定义对象( add(new 对象) )时，迭代器it.next()需要转化----(person)it.next()  
 //初始容量为10，****拆箱装箱  
 ③LinkedList：内部是链表数据结构。是不同步的。//增删元素的速度很快  
 数据结构：a.堆栈（先进后出），b.队列（先进先出）  
 //用LinkedList中的方法可以实现这两种数据结构，即构建一个相同功能的容器

 
```
addFirst() addLast() removeFirst() removeLast()
getFirst()-----如果链表为空，则抛出异常//区别于peekFirst()---如果链表为空，则返回null
offerFirst(),pollFirst()

```
 **Set集合**(不包含重复元素，无序)：  
 接口中的方法和Collection一致//重复编译无错，取出时只有一个  
 常用对象：  
 ①HashSet：内部数据结构是哈希表，是不同步的  
 =>哈希算法–哈希值-（哈希表）-作为元素的引用（查询快//通过算法可以直接得到位置）  
 HashCode方法↑ //元素的哈希值相同，则 **才会** 继续判断内容（equals()）  
 存储自定义对象时，需要自定义（重写）HashCode方法和equals方法：

 
```
			public int hashCode(){
				return name.hashCode()+age*37;//调用String类型的name的哈希hashCode方法
				}
			public boolean equals（Object obj）{
				if(this == obj)
					return ture;
				if(!(obj instanceof Person))
					throw new ClassCastException("类型错误")
				person p =(person)obj;
					return this.name.equals.(p.name)&&this.age == p.age;
				}

```
 ****ArrayList: 使用equals（contains/remove）判断元素是否相同  
 HashCode: 使用hashCode+equals来判断  
 ②TreeSet(数据结构：二叉树):可以对Set集合中的元素进行排序  
 // 判断元素唯一性的方法，根据比较方法的返回结果是否是零，是零则相同，不是则不同  
 =>排序方法（一）【对象默认排序方式】  
 让元素自身具备比较功能，就必须实现Comparable接口(implements Comparable)，  
 重写int comparableTo(Object o)方法

 *如果不要按照对象中具备的自然排序（返回1，-1，0）进行排序。如果对象中不具备自然顺序  
 =>排序方法（二）【比较器】  
 让集合自身具备比较功能，定义类 实现Comparator接口，覆盖compare方法。  
 将该类对象作为参数传递给TreeSet集合的构造函数

 
```
	TreeSet test = new TreeSet(new  定义的类名());

```
 **Map集合(**接口):  
 区别于Collection->

  
  2. Map一次添加一对元素（双列集合），Collection（单列集合） 
  4. 集合中必须保证键的唯一性  
      其常见方法：  
```
		添加 	value put(key,value)
			  //返回前一个和key关联的值，如果没有返回null
			  相同键（key），值（value）会覆盖
		删除 	void clear()//清空
			  value remove(key)//根据指定key删除这个键值对
		判断 	  boolean contains(key);//是否包含
	（和CL一样）	boolean containsAll(value);	
				boolean isEmpty();//判断是否为空
		获取	  value get(key)//通过键值获取，如果没有该键返回空
				int size();//键值对个数

```
 *取出map所有元素的方法//区别于Collection  
 第一种：   
 ①通过keySet方法获取map中所有的键所在的Set集合  
 ②通过Set的迭代器获取到每一个键  
 ③再对每一个键通过map集合的get方法获取其对应的值

 
```
例：	Map<Integer,String> map
		map.put(......)....
		Set<Integer> keySet = map.keySet();
		Iterator<Integer> it = keySet.iterator();
		while(it.hasNext()){
			Integer key = it.next();
			String value = map.get(key);
			System.out.println(key);
		}

```
 第二种：   
 entrySet方法//将Map转换为Set进行迭代，将键和值的映射关系作为对象存储到Set集合中

 
```
 例：	Set<Map.Entry<Integer,String>> entrySet = map.entrySet();
		Iterator<Map.Entry<Integer,String>> it = entrySet.iterator();
		//Entry：Map接口的内部接口（内部嵌套类），键和值的关系对象
		while(it.hasNext()){
			Map.Entry<Integer,String> me = it.next();
			Integer key = me.getKey();
			String value = me.getValue();//Map.Entry对象的方法
			System.out.println(key);
		}

```
 第三种(只取值)：Map的values()方法

 
```
例：	  Collection<String> values = map.values();//对values进行迭代
		  Iterator<String> it = values.iterator();

```
 常用子类对象：  
 ①Hashtable（哈希表，同步//不允许nulll作为键或值）  
 ------Properties：用来存储键值对型的配置文件的信息//IO  
 ②HashMap（哈希表，不同步//允许nulll作为键或值）—>HashSet是HashMap的实例

 
```
HashMap<Student,String> hm = HashMap<Student,String>();
/*存储自定义对象时和HashSet一样，在自定义对象的类中重写HashCode和equles方法
 *取出方法同取出Map元素
 *LinkedHashMap//存和取的顺序是一样的
*/

```
 ③TreeMap（二叉树，不同步//对Map中的键进行排序）

 
--------
 
## []()泛型

 **（安全机制）  
 用于接收具体引用数据类型的参数范围  
 优点：  
 1.将运行时期的ClassCastException转到了编译时期---->用于编译时期，确保了类型的安全  
 2.避免了强制转换的麻烦

 
```
ArrayList<String> test = new ArrayList<String>;
//规定ArrayList集合中元素的类型为String

```
 注：  
 擦除->运行时会将泛型去掉，既生成的class文件中不带泛型，称为泛型的擦除//为了兼容运行的类加载器  
 补偿->在运行时，通过获取元素的类型进行转换动作，不用使用者再进行强制转换  
 *TreeSet设置泛型时，注意实现比较接口时，指定比较类型

 
```
		implements Comparable<Person>//比较的是Person类
并重写： int comparableTo(Person p)//第二种排序的方法同理

```
 自定义泛型类：  
 当类中的操作的引用数据类型不确定的时候，就使用泛型类表示

 
```
	 class tool<test> {}->tool<String> tl = new tool<String>();

```
 泛型方法：

 
```
     public <test> void show(test test1){}
     //在有具体数据类型的泛型类中，方法可以操作其他数据类型

```
 *当方法静态时，不能访问类上定义的泛型，只能将泛型定义在方法上  
 泛型接口：

 
```
                interface Inter<T>{
				public void test(T t);
			  }//可以在定义实现接口的类时指定数据类型，也可以在创建对象时再明确数据类型

```
 泛型的通配符： ？ //未知类型

 
```
	例：	public static void printCollection(Collection<?> a){
					Iterator<?> it = a.iterator();					
					while(it.hasNext()){
					System.out.println(it.next());
					}
				}//迭代并打印任意类型集合的任意类型的元素

```
 泛型对类型的限定：

 
```
	 上限：?  extends E  
	  例	  Collection<? extends person>
		  //只能传person或者person子类的对象

```
 体现–> 一般在存储元素的时候都是用上限，因为取出时都是按照上限类型(person)来运算的，不会出现类型安全隐患

 
```
	下限：？ super E    //接收E类型或者E的父类型的对象

```
 体现–> 通常对集合中的元素进行取出操作时，可以是用下限

 
--------
 
## []()IO流

 23.IO流（Input Output）  
 用于操作流的对象：IO包  
 流按操作数据分为：字节流 字符流//字符流->字节流读取文字字节数据后，查找指定编码表获取对应文字，在对文字进行操作  
 流按数据流向分为：输入流 输出流（相对于内存 读 写）  
 一个流关联一个文件

 子类都以父类名作为后缀，而子类名的前缀就是该对象的功能  
 字节流的抽象基类：  
 · InputStream · OutputStream  
 字符流的抽象基类（操作文字数据）：  
 ·Reader ·Writer

 ①字符流例：  
 FileReader:

 
```
	/*FileReader->输入流对象，读取字符文件到内存，显示到控制台
	 *创建对象是，必须明确读取的文件且是存在的
	*/
	FileReader fr = new FileReader("demo.txt");//demo.txt中为"ab"
	
	/*第一种读取方式 read()*/
	int ch = fr.read();//用read()方法读取字符,返回int类型
	System.out.println(ch);//显示97->a
	int ch1 = fr.read();
	System.out.println(ch1);//显示98->b
	int ch2 = fr.read();
	System.out.println(ch2);//显示-1->流结束的标识符#
	/*所以可以写为如下*/
	int ch = 0;
	while((ch=fr.read()) != -1){
		System.out.println((char)ch);//转换为char类型
	}
	
	/*第二种读取方式 read(char[]) demo.txt中为"abcde"*/
	char[] buf = new chr[3];//相当于缓冲区
	int num = fr.read(buf);
	System.out.println(num,new String(buf));//num=3 "abc" 
	int num1 = fr.read(buf);
	System.out.println(num1,new String(buf));//num=2 "dec"
	int num2 = fr.read(buf);
	System.out.println(num2,new String(buf));//num=-1 "dec"
	/*所以可以写为如下*/
	int len = 0;
	while(len=fr.read(buf) != -1){
		System.out.println(new String(buf,0,len));
	}
	 
	fr.close();

```
 FileWriter:

 
```
  	/*FileWriter->输出流对象，往文件（硬盘）中写入字符数据
	 *明确存储数据的目的地，如果文件不存在会自动创建，存在则会被覆盖9
	 *在构造函数中加入true，可以实现对文件进行续写
	*/'
	FileWriter fw = null;
	try{//IO异常的处理
	fw = new FileWriter("demo.txt",true);//有IO异常->throws IOException
	fw.write(String test);//调用Writer对象中的write()方法，写入到了 临时 存储的缓冲区（流）中
	fw.flush();//进行刷新，将数据写入到目的地中（demo.txt）
	fw.close();//先刷新（调用flush()）它后，再关闭此流
	}catch(IOException e){
		System.out.println(e.toString());
	}finally{
		if(fw != null)//否则fw没创建出来，会出现空 指针异常
		try{
			fw.close();
		}catch(IOexception e){
			//
			throw new RuntimeException("关闭失败");
		}
	}

```
 字符流的缓冲区：提高了对数据的读写效率  
 缓冲区实质是一个封装的数组，并对外提供了更多的方法对数组进行访问。  
 缓冲的原理是从源中获取一批数据装进缓冲区中，然后从缓冲区中取出一个一个的数据。  
 （readLline方法为用另一个临时缓冲区存储取出的一行数据，再一个个取出）

 对应的类：·BufferedWriter &emsp ·BUfferedReader  
 BufferedWriter：

 
```
	/* 将文本写入字符输出流，缓冲各个字符，从而提供单个字符，数组和字符串的高效写入*/
	FileWriter fw = new FileWriter("buf.txt");
	BufferedWriter bufw = new BufferedWriter(fw);//创建一个指定流的字符写入流缓冲对象
	bufw.write("abcdef");//将数据写入到缓冲区中
	bufw.newLine();//换行
	bufw.flush();//将缓冲区的数据刷到目的地
	bufw.close();//关闭缓冲区（fw流）

```
 BufferedReader：

 
```
	/*实现字符，数组和行的高效读取*/
	FileReader fr = new FileReader("buf.txt");
	BufferedReader bufr = new BufferedReader(fr);
	String line = null；
	while(line = bufr.readLine() != null){
		System.out.println(line);
	}//readLine()->读取文本的行的全部元素
	bufr.colse();

```
 装饰设计模式：对一组对象的功能进行增强，装饰类和被装饰类都必须所属用一个接口或者父类（如：BuffereWriter和BuffereReader）  
 和继承的区别：不需要和所需要用到的类产生关系（继承），所以更加的灵活

 
```
	class Person{void test(){}}
	class NewPreson{
		private Person p;
		NewPerson(Person p){
			this.p = p;
		}
		void test(){...; p.test(); ...;}//对Person对象中的test功能方法进行增强
	}
	
	Person p = new Person();
	NewPerson p1= new NewPerson(p);

```
 LineNubmberReader：也是装饰类

 
```
	FileReader fr = new FileReader("demo.txt");
	LineNumberReader lnr = new LineNumberReader(fr);
	String lin = null;
	lnr.setLineNubmber(10);//设置行号从10开始，默认从1开始
	while(lin=lnr.read() != null){
		System.out.println(Inr.getLineNumber() + " :" + line);//显示行号和内容
	}
	lnr.close();

```
 ②字节流例：

 
```
	/*FileOutputStream写入流对象*/
	FileOutputStream fos = new FileOutStream("bytedemo.txt");
	fos.write("abc".getBytes());//String不能直接写入，将字符串变成字符型数据；不用刷新，直接写入到目的地中
	fos.close();//同样要进行关闭的动作
	
	/*FileInputStream读取流对象*/
	FileInputStream fis = new FileInputStream("bytedemo.txt");
	int ch = 0;
	while(ch=fis.read() != -1){
		System.out.println((char)ch);
	}//同样可以用数组√
	
	System.out.println(fis.available());//字节大小
	byte[] buf = new byte[fis.available];//定义一个刚好的byte数组,少用容易内存溢出或先判断文件大小分段
	fis.read(buf);
	System.out.println(new String(buf));

```
 字节流的缓冲区：·BufferedInputStream ·BufferedOutputStream

 键盘录入和转换流（InputStreamReader OutputStreamWriter）：

 
```
	InputStream in = System.in;//System类，输入流不用关
	int ch = in.read();//in未读到数据，read()方法会一直处于阻塞状态
	System.out.println(ch);
	
	/*转换流
	 *InputStreamReader：字节流通向字符流的桥梁，由编码表得到其字符
	*/
	InputStreamReader isr = new InputStreamReader(in);
	BufferdReader bufr = new BufferdReader(isr);
	String line =null;
	while((line=bufr.readline()) != null){
		if("over".equals(line)
			break;//判断键盘输入的是否为over，是的话就结束运行
		System.out.println(line.toUpperCase);//输出其大写
	)
	}
	/*OutputStreamWriter:字符流通向字节流的桥梁，通过字符编程成字节*/
	BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
	//字节->字符（键盘输入的数据为字节数据，转为字符流进行操作）
	//处理中文时，转为字符流比较好操作(GBK中一个中文对应两个字节)
	BufferrdWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));//System可以是字节文件输入输出流
	//字符->字节（控制台显示的数据为字节数据，操作完后显示）

```
 流的操作规律：  
 ①明确源和目的  
 源：InputStream Reader  
 目的：OutputStream Writer  
 ②明确数据是否是纯文本数据  
 源：是->Reader 否->InputStream  
 目的：是->Writer &emsp；否->OutputSteam  
 由①②6可知需求中具体要用的体系  
 ③明确具体的设备  
 源设备：·硬盘-File ·[键盘-System.in](http://xn---System-231rf89s.in) ·内存-数组 ·网络-Socket流  
 目的设备：·硬盘-File ·控制台-System.out ·内存-数组 ·网络-Socket流  
 ④是否需要额外功能  
 高效-缓冲区-Buffer…  
 转换流

 需求：将一个中文字符串数据按照指定的编码表写入到文本文件中

 
```
	FileWrite fw = new FileWriter("demo.txt");
	fw.write("你好");//默认编码表
	fw.close();
	/*转换流的编码和解码，能指定编码表
	 *OutputStreamWriter InputStreamReader
	*/ 
	OutputStreamWriter osw = new OutputStreamWriter(new FileOutoutStream("gbk.txt"),"GBK");//utf-8
	FileWriter fw = new FileWriter("gbk.txt");
	//两句的功能相同，便捷类，FileWriter->操作字节流+本机默认的编码表
	

```
 IO流中的流类只能操作文件数据，而类似文件夹，文件属性等都不能操作，于是提供了非流类->File类  
 File：用于将文件或者文件夹封装成对象，方便对文件与文件夹的属性信息进行操作，可以作为参数传递给流的构造函数  
 File构造函数

 
```
	/*将a.txt文件封装成File对象*/
	File file = new File("C:\\a.txt");//->("C:\\","a.txt")
	File f = new File("C:\\");//路径分隔符：\\ -> File.separator
	File file2 = new File(f,"a.txt");	

```
 File常见方法：

 
```
	File file = new File("file.txt");
	//1.获取
		file.getName();//文件名称
		file.getAbsolutePath()//文件绝对路径
		file.getPath();//相对路径
		file.length()//文件大小
		long time = file.lastNodified//文件修改时间
		Data data = new Data(time);
		DataFormat dataFormat = DataFormat.getDataTimeInstance(DataFormat.LONG,DataFormat.LONG);
		String str_time = dataFormat.format(data);//显示时间
		get...();//更多get方法详见API
	//2.创建与删除（File创建-IO流操作-File删除）有IOException
		boolean b = file.createNewFile();//创建file.txt文件
		/*如果文件不存在则创建，存在则不创建并返回false*/
		File dir = new File("abc");->("abc\\a\\b\\c")
		boolean b = dir.mkdir();//创建名为abc的文件夹（单极目录）->dir.mkdirs();//多级目录
		boolean b = file.delete();//删除
	//3.判断
		file.exists();//是否存在，不存在则以下都是false
		file.isFile();//是否为文件	file.isDirectory();//是否为目录
	//4.重命名
		File file2 = new File("file2.txt");
		file.renameTo(file2);//将file.txt文件重命名为file2.txt
	//5.系统根目录和
		File[] files = File.listRoots();
		for(File file : files)//增强for循环
			System.out.println(file);
	//6.获取目录列表内容（名称）:文件，文件夹，隐藏文件
		File file = new file("C:\\");//调用list方法的File对象中封装的必须是目录，否则会发生空指针异常
		String[] names = file.list();//目录中没有内容依然返回空数组
		for(String name:names)
			System.out.println(name);
	//7.过滤器
		/*FilenameFilter->接口*/
		class FilterTest implements FilenameFilter{
			public boolean accept(File dir,String name){
				return name.endsWith(".java");//名称过滤
			}
		}//遍历数组，过滤后取出符合的元素
		String[] names = file.list(new FilterTest());//获得相应目录下为java文件的目录列表(名称)
	-> 获取文件列表：listFiles();//方法
	       文件过滤：boolean accept(File pname){ return pname.isHidden();}//过滤隐藏文件

```
 补：递归  
 一个功能在被重复使用，并每次使用时，参与运算的结果和上一次调用有关，这时可以用递归来解决问题  
 注：  
 递归一定要明确条件，否则容易发生栈溢出异常  
 递归的次数，栈只进不出，也会发生栈溢出异常

 HashTable-Properties集合（特点）：  
 ①该集合中的键和值都是字符串类型  
 ②集合钟的数据可以保存到【流】中，或者从流中获取数据  
 //一般用于操作一键值对形式存在的配置文件

 
```
  Properties prop =new Properties();
  prop.setProperty(String a,String b);//存储元素
  prop.stringPropertyNames();//取出元素
  /*PrintStream:字节流,主要用于调试
   *list方法->list(PrintStream out);
  */
  prop.list(System.out);//可以知道集合中的元素，便于调试

```
 持久化：将数据写到硬盘（存起来）

 
```
  //store(OutputStream out,String comments)-store(Writer writer,String comments)[不能用中文]
  FileOutputStream fos = new FileOutputStream("info.txt");
  prop.store(fos,"info");//String为第一行注释 #info
  fos.close();

```
 集合中的数据来自一个文件，文件中的数据必须时键值对

 
```
  FileInputStream fis = new FileInputStream("info.txt");
  prop.load(fis);//load(InputStream/Reader)

```
 打印流:PrintStream PrintWriter  
 为其他输出流添加了功能，还提供了其他两项功能（字符流对象用PrintWriter）  
 ①提供了多种打印方法可以对多种数据类型值进行打印。并保持数据的表示形式  
 ②不会抛出IOException  
 构造方法接收三种类型的值：字符串，File对象，字节输出流 (字符输出流)

 
```
	PrintStream out = new PrintStream("a.txt");
	out.write(610);//只写最低8位及一个字节
	out.print(97);//将97变为字符保持原样将数据打印到目的地
	out.close();
	//用于保持数据的原样性，但不能保持数据的大小

```
 序列流:SequenceInputStream  
 将多个源（流）合并成一个源（流）

 
```
	/*将1.txt 2.txt 3.txt文件中的数据合并到一个文件中*/
	Vector<FileInputStream> v = new Vector<FileInputStream>();
	v.add(new FileInputStream("1.txt"));
	v.add(new FileInputStream("2.txt"));
	v.add(new FileInputStream("3.txt"));
	Enumeration<FileInputStream> en = v.elements();//集合vector的枚举（vector特有的取出方式，详见补充）
	SequenceInputStream sis = new SequenceInputStream(en);//当只需合并两个源时，可以->(InputStream a,InputStream b)
	//之后用输出流FileOutputStream写入sis.read(buf)读到的数据4.txt

```
 补：  
 ArrayList a = ArrayList();//ArrayList中没有枚举  
 Enumeration en = Collections.enumeration(a);//用工具类中的方法返回ArrayList的枚举（枚举中运用了迭代方法）

 操作对象的字节流：·ObjectInputStream ·ObjectOutputStream

 
```
	/*ObjectOutputStream作用：将类的对象存储到硬盘，对其进行持久化（序列化），便于下次的使用，不用解析*/
	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("obj.txt"));
	oos.writreObject(new obj());//写入一个obj对象,且obj类必需implements Serializable
	oos.close();
	/*ObjectInputSrream：读取为对象*/
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("obj.txt"));
	Person ps = (Person)ois.readObject();//可以进行强转（读出来的操作被称为对象的反序列化）

```
 Serializable接口补充：  
 对象序列化时，会给对象加入一个ID号，在对象进行反序列化时会先验证其id号是否与序列化时的是同一个，用于判断类和对象是否是同一个版本。  
 为避免编译器版本的不同而对相同的类产生不同的ID号，最好自定义ID号：private static final long serialVersionUID = 9527l;  
 transient：非静态数据不想被序列化可以用次关键字修饰//静态static变量不会被序列化

 IO包中的其他类：  
 ·RandomAccessFile特点：  
 1.该对象既能读，又能写  
 2.该对象内部维护了一个byte数组，并通过指针操作数组中的元素  
 3.可以通过getFilePointer方法获指针的位置，和通过seek方法设置指针的位置*  
 4.该对象其实就是将字节输入流和输出流进行了封装  
 5.该对象的源或者目的只能是文件

 
```
	/*写入*/
	RandomAccessFile raf = new RandomAccessFile("ranacc.txt","rw");//mode 参数指定用以打开文件的访问模式(rw)
	//如果该文件尚不存在，则尝试创建该文件,存在则不创建
	raf.write("张三".getBytes());//getBytes将String编码为byte序列，并将结果存储到一个新的byte数组中
	raf.writeInt(97);
	raf.close();
	/*读取*/
	RandomAccessFile raf = new RandomAccessFile("ranacc.txt","r");
	//以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException
	System.out.println(raf.getFilePointer());//获取指针位置
	raf.seek(1*8);//设置指针位置从8开始--->实现随机读取或者写入，可以实现不同位置的同时写入或者读取

```
 ·PipedInputStream和PipedOuputStream(管道流)特点：  
 1.输入输出可以直接进行连接，通过结合线程使用  
 2.不建议此两对象单线程使用

 
```
	class Input implements Runnable{
		private PipedInputStream in;
		Input(PipedInputStream in){
			this.in = in;
		}
		public void run(){
			trt{
				bytes[] buf = new bytes[1024];
				int len = in.read(buf);
				String s = new String(buf,0,len);
				System.out.println("s=",s);
				in.close();
			}catch(Exception e){
			}
		}
	}
	class Output implements Runnable{
		private PipedOutputStream out;
		Input(PipedOutputStream out){
		this.out = out;
		}
		public void run(){
			trt{
				out.write("NIUPI".getBytes());
			}catch(Exception e){
			}
		}
	}
	//创建对象
	PipedInputStream input = new PipedInputStream();
	PipedoutputStream output = new PipedOutputStream();
	input.connect(output);//连接管道
	new Thread(new Input(input)).start();
	new Thread(new Output(output)).start();

```
 ·DataInputStream和DataOutputStream  
 1.主要用于操作基本类型的字节流

 
```
	DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
	dos.writeUTF("中文")；//utf-8修改版，转换流无法读取
	DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
	String str = dis.readUTF();

```
 ·ByteArrayOutputStream和ByteArrayInputStream（操作字节数组）  
 1.源和目的地都是内存  
 2.数据都是被写入一个byte数组  
 3.关闭流无效（没有调用底层资源）

 
```
	ByteArrayInputStream bis = new ByteArrayInputStream("abcd".getBytes());
   ByteArrayOutputStream bos = new ByteArrayOuputStream();
   int ch=0;
   while((ch=bis.read())!=null){
   bos.write(ch);
   }

```
 类似的对象：CharArrayInputStream和CharArrayOutputStream（操作字符数组）  
 StringReader和StringWriter（操作字符串）

 
--------
 
## []()多线程

 24.多线程  
 进程：正在进行的程序（系统分配了资源，开辟了内存空间）  
 线程：进程中负责程序执行的控制单元（执行路径），一个进程中至少要有一个线程，且可以多执行路径，称为多线程  
 多线程的优缺点：  
 优点->程序的运行效率可能会提高，使用线程可以把占据时间长的程序中的任务放到后台去处理  
 缺点->如果有大量的线程,会影响性能,因为操作系统（cpu）需要在它们之间随机切换，同时占用更多的内存空间，程中止需要考虑对程序运行的影响

 JVM启动时的多线程：  
 主线程->执行main函数的线程（线程任务代码都定义在main方法中）  
 垃圾回收线程->负责垃圾回收的线程（在垃圾回收器中定义）  
 //垃圾回收器是不定时启动回收的，要启动时则调用System.gc();  
 …

 创建线程的目的：开启一条执行路径，去运行指定的代码与其他代码实现同时运行  
 JVM创建的主线程的任务都定义在主函数中，而自定义线程的任务可以通过Thread类中的run()方法来体现，即run方法就是封装自定义线程运行任务的方法

 多线程（自定义线程）两种创建的方式：  
 1.声明为Thread的子类，并重写Thread类中的run方法

 
```
   class demo extends Thread{//继承Thread类
   	public void run(){//重写run方法
   		//执行代码
   	}
   }
   //通过创建demo对象就可以创建一个线程，然后通过start方法启动(调用run方法执行任务代码)->区别于直接调用run方法
   demo d = new demo();
   d.start();

```
 Thread(String name);//可以通过构造方法定义线程名  
 可以通过Thread类中的getName方法获取线程名称Thread-n（n=0,1,2…）//一创建就定义了名称，即对象的名称  
 获取运行中线程的名称则需要通过currentTread方法（返回的是线程对象，还需getName方法）//主线程的名称为main  
 2.实现Runnable接口,并定义一个称为run的无参方法(较为常用)

 
```
   class demo implements Runnable{//实现接口
   	public void run(){//覆盖run方法
   		//执行任务
   	}
   }
   demo d = new demo();
   Thread t = new Thread(d);//通过Thread类创建线程对象,并将实现接口的对象作为构造方法的参数
   t.start();

```
 适用场景:需使用线程的类已经具备了自己的父类(单继承),则可以通过实现Runnbale接口  
 Runnable接口的好处(区别):  
 将线程的任务从线程的子类中分离出来,进行了单独的封装,即按照面向对象的思想将任务封装成对象  
 避免了java单继承的局限性

 线程的状态：  
 被创建—(start()😉—>运行(具备执行资格和执行权)  
 运行—(run方法结束，即线程任务结束/stop()😉—>消亡  
 运行<—(sleep(time)–time结束)—(wati()–notify();//唤醒)—>冻结(释放执行权同时释放执行资格)—>临时阻塞状态  
 运行<—>临时阻塞状态(具备执行资格,不具备执行权)

 同步（synchronized）：  
 多线程实例：卖票

 
```
   class Ticket implements Runnable{//不能直接继承Thread类，因为实例化n个对象后会产生n*100张票
   	private int num = 100;//100张票
   	public void run(){
   		while(true){
   		if(num>0)
   			System.out.println(Thread.currentThread().getName()+"sale"+(num--));//卖一张少一张
   		}
   	}
   }
   class TicketDemo{
   	public static void main(String[] args){
   		Ticket t = new Ticket();//创建线程任务对象
   		Thread t1 = new Thread(t);//t1,t2,t3,t4执行同一个线程任务
   		Thread t2 = new Thread(t);//多线程同时售卖100张票
   		Thread t3 = new Thread(t);
   		Thread t4 = new Thread(t);
   		t1.start();
   		t2.start();
   		t3.start();
   		t4.start();
   	}
   }

```
 在卖票问题出现的线程的安全问题：

 
```
   public void run(){//run方法不能抛出异常
   	while(true){
   		if(num>0){
   			Thread.sleep(10);//sleep方法有InterruptedException异常，只能用try-catch处理
   			System.out.println(Thread.currentThread().getName()+"sale"+(num--));//卖一张少一张
   		}
   	}
   }结果：卖的票数到了0，-1，-2...出现安全问题

```
 当一个线程在执行操作共享数据的多条代码过程中，此线程变为阻塞状态而其他线程参与了运算，就会导致线程安全问题的产生  
 产生的原因：  
 1.多个线程在操作共享的数据（num）  
 2.操作共享数据的线程代码有多条  
 解决方法：  
 将多条操作共享数据的线程代码封装起来，当由线程在执行这些代码的时候，其他线程是不能参与运算的  
 当该线程把代码执行完毕后，其他线程才可以参与运算  
 所以可以运用_同步代码块_来解决这个问题–>synchronized

 
```
   synchronized(对象){
   	//需要同步的代码
   }
   Object obj = new Object();//就是一个锁
   public void run(){
   	while(true){
   		synchronized(obj){//当某一线程进入synchronized中的代码块后，会持有obj，而其他线程由于没有obj而无法进入代码块
   		if(num>0){
   			Thread.sleep(10);
   			System.out.println(Thread.currentThread().getName()+"sale"+(num--));
   		}
   		}
   	}
   }
   /*同步的好处：解决了线程的安全问题  
	  同步的弊端：因为同步外的线程都需要判断同步锁，所以相对降低了效率
     同步的前提：同步中必须有多个线程并使用同一个锁
    */

```
 第二种同步的方法：同步函数（适用于需要同步的代码都在一个方法中）

 
```
	public synchronized void test()
	{//需要同步的代码}
	/*当同时有同步代码块和同步函数时*/
	if(flag)
		synchronized(this){}//同步函数的锁是任意的对象，所以用固定的this
	else
		public synchronized void test(){}
	/*静态同步函数*/
	public static synchronized void test(){}
	synchronized(this.getClass()){}//锁是字节码文件对象
	synchronized(Ticket.class){}//同上

```
 多线程下的单例模式：  
 懒汉式

 
```
	class Single
	{
		private static Single s = null;//共享数据
		private Single(){}
		public static Single getInstance(){//多条执行语句
			if(s==null)
				synchronized(Single.class){//解决方法，不会每次都需要判断锁
					if(s==null)
						s = new Single();
				}
			return s;
		}
	}
	//由注释可以懒汉模式存在安全问题

```
 同步下死锁的常见情景：  
 1.同步的嵌套

 
```
	class Test implements Runnable{
		private boolean flag;
		Test(boolean flag){
			this.flag  = flag;
		}
		public void run{
			if(flag){
				synchronized(MyLock.locka){
					System.out.println(Thread.currentThread().getName()+"if-locka");
					sychronized(MyLock.lockb){
					System.out.println(Thread.currentThread().getName()+"if-lockb");
					}
				}
			}else{
				synchronized(MyLock.lockb){//当一条线程拿到locka锁，另一条拿了lockb锁，他们都无法继续进入嵌套的锁
					System.out.println(Thread.currentThread().getName()+"else-lockb");
					sychronized(synchronized.locka){
					System.out.println(Thread.currentThread().getName()+"else-locka");
					}
				}
			}
		}
	}
	class MyLock{
		public static final Object locka = new Object();
		public static final Object Lockb = new Object();
	}
	class DeadLock{
		public static void main(String[] args){
			Test a = new Test(true);
			Test b = new Test(false);
			Thread t1 = new Thread(a);
			Thread t2 = new Thread(b);
			t1.start();
			t2.start();
		}
	}
	

```
 多线程间的通信（多个线程在处理同一资源，但是任务不同）  
 等待唤醒机制：  
 wati();//让线程处于冻结状态，线程会被存储到线程池中  
 notify();//唤醒线程池中一个线程（任意）  
 notifyAll();//唤醒线程池中的所有线程  
 这些方法都定义在Object类，在同步中使用，必须明确操作的是哪个锁上的线程

 
```
	//单线程输入和单线程输出
	class Resource{//资源
		private String name = "";
		private String sex = "";
		private boolean flag = false;
		
		public synchronized void set(String name,String sex){
			if(this.flag)
				trt{this.wait();//方法定义在Object方法中，使用时需明确线程的锁对象
				}catch(InterruptedEsception e){}
			this.name = name;
			this.sex = sex;
			flag = true;
			this.notify();在哪个线程池中冻结，就从哪个线程池唤醒
		}
		public synchronized void out(){
			if(!flag)
				trt{this.wait();//方法定义在Object方法中，使用时需明确线程的锁对象
				}catch(InterruptedEsception e){}
			System.out.println(name+"...."+sex);
			flag = false;
			this.notify();
		}
	}
	class Input implements Runnable{输入
		Resource r;
		Input(Resource r){
			this.r = r;
		}
		public void run(){
			int x= 0;
			while(true)
			{
		
				if(x==0){
					r.set("a","nan");
				}else{
					r.set("b","nv")
				}
				x = (x+1)%2;
			}
		}
	}
	class Ouput implements Runnbale{/输出
		Resource r;
		Output(Resource r){
			this.r = r;
		}
		public void run(){
			while(true){
				r.out();
			}
		}
	}

```
 多生产者多消费者问题（多线程生产和多线程消费）：

 
```
	class Resource{
		private String name;
		private int count = 1;
		private boolean flag = false;
		
		public synchronized void set(String name){
			while(flag)//唤醒线程后，需要再判断flag（烤鸭是否已经被消费了）
				trt{this.wait();}catch(InterruptedEsception e){}
			this.name = name + count;
			count++;
			System.out.println(Thread.currentThread().getName()+"...生产者..."+this.name);
			flag = true;
			notifyAll();//防止四条线程都处于冻结状态（生产者线程唤醒了生产者线程，然后都wait，而消费者线程都处于wait）
		}
		
		public synchronized void out(){
			while(!flag)//判断标记，解决了线程获取执行权后，是否要运行
				trt{this.wait();}catch(InterruptedEsception e){}
			Sys2em.out.println(Thread.currentThread().getName()+"...消费者..."+this.name);
			flag = false;
			notifyAll();//解决了本方线程一定会唤醒对方线程的问题，防止死锁
		}
	}
	class Consumer implements Runnable{
		private Resource r;
		Producer(Recource r){
			this.r = r;
		}
		public void run(){
			while(true){
				r.set("烤鸭");
			}
		}
	}
	class Producer implements Runnable{
		private Resource r;
		Producer(Recource r){
			this.r = r;
		}
		public void run(){
			while(true){
				r.out();
			}
		}
	}
	class ProducerConsumerDemo{
		public static void main(String[] args){
			Resource r = new Resource();
			Producer pro = new Oriducer(r);
			Consumer con = new Consumer(r);
			//多生产者多消费者
			Thread t0 = new Thread(pro);
			Thread t1 = new Thread(pro);
			
			Thread t2 = new Thread(con);
			Thread t3 = new Thread(con);
	
			t0.start();
			t1.start();
			t2.start();
			t3.start();
			
		}
	}

```
 上面等待唤醒机制造成的死锁的解决方法（Lock接口和Condition接口）:  
 同步代码块对锁的操作（获取释放）是隐式的，之后同步和锁被封装成对象（实现的接口Lock）//替代synchronized方法和语句的使用

 
```
	Lock lock = new ReentrantLock();//实现Lock接口的子类ReetrantLock
	void test(){
		try{
		lock.lock();//获取锁
		code... throws Exception();
		}finally{
		lock.unlock();//释放锁
		}
	}
	//Condition接口替代了Object监视器方法的使用（wait(),notify(),notifyAll()）
	Condition con = lock.newCondition();//通过已有的锁获取该锁上的监视器对象
	con.await();
	con.singnal();//通过监视器对象获取
	con.singnalAll();
	//监视器封装成对象的好处：生产者和消费者可以定义一个锁和多组监视器，唤醒对象可以通过不同的监视器来控制而不用全部唤醒

```
 wait()和sleep()方法的区别：  
 1.wait可以指定时间也可以不指定，sleep必须指定时间  
 2.在同步中时，对cpu的执行权和锁的处理不同。  
 wait：释放执行权，释放锁  
 sleep：释放执行权，不释放锁

 线程的停止（消亡）：  
 1.stop方法//已过时，不再使用  
 2.run方法结束  
 任务中的循环结构，定义标记来控制循环以结束任务（线程处于冻结状体时，则无法读取标记）  
 3.使用interrupt()方法将线程从冻结状态强制恢复到运行状态中来，让线程具备cpu执行权（会抛出InterruptedException异常）

 线程中常见的其他方法：  
 1.setDaemon()方法->将线程标记为守护线程  
 线程开启前调用->t1.setDaemon(true);//运行时和普通线程一样，而结束不一样（线程无论处于何种状态都自动结束）  
 2.join()方法->等待调用该方法的线程执行结束，在执行其他线程  
 适用场景，临时加入一个线程需要先运算时  
 3.toString()方法->返回该线程的字符串表现形式，包括线程名，优先级和线程组  
 优先级：可能被cpu执行的概率（可调->t.setPriority(Thread.MAX_PRIORITY)//MAX,MIN,NORM设置t线程为最高优先级10，默认为5）  
 线程组：ThreadGround本质就是一个存储线程的集合  
 4.yield()方法->暂停正在执行的线程对象，并执行其他线程（释放执行权）

 
```
	class Threaddemo{
		public static void main(String[] args){
			new Thread(){
				public vodi run(){}
			}.start();
			
			Runnable r = new Runnable(){
				public void run(){}
			}
			new Thread(r).start();
			
			//输出为b，以子类为主（将父类覆盖了）
			new Thread(new Runnable(){
				public void run(){
					System.out.println("a");
				}
			}){
				public void run(){
					System.out.println("b");
				}
			}.start();
		}
	}

```
 
--------
 
## []()网络编程

 25.网络编程  
 网络结构模型：  
 OSI七层模型->物理层（接口设备，比特流传输）-数据链路层（MAC，交换机）-网络层（IP，路由）-传输层（协议端口）-会话层（建立数据传输通路）-表示层（数据解析）-应用层（终端的应用软件）  
 TCP/IP四层模型->应用层-传输层-网际层-网络层//详见计算机网络

 网络通讯要素：  
 IP地址->本机IP：127.0.0.1 本机名：localhost  
 端口号->用于标识进程的逻辑地址//有效端口065535，01024为系统使用或保留端口  
 传输协议->通讯的规则，常见协议TCP,UDP  
 UDP:将数据及源和目的封装成数据包，数据报限制在64k内，无需建立连接，速度快，为不可靠协议  
 TCP:建立连接形成通道，连接中进行大数据量传输，通过三次握手完成连接，效率稍低，是可靠协议

 网络编程涉及的包：java.net包  
 IP对象：InetAddress(ipv4:Inet4Address-ipv6:Inet6Address)

 
```
	InetAdress ip = InetAddress.getLocalHost();//获取本地主机ip地址对象
	ip.getHostAddress();//获取对象ip地址
	ip.getHostName();//获取对象主机名
	ip = InetAddress.getByName("192.168.1.100");//通过ip地址或主机名获取其他主机的ip地址对象--getAllByName返回ip对象数组（服务器）

```
 域名解析：域名-本地host-dns服务器-ip地址-相应的主机/服务器

 Socket：为网络服务提供的一种机制，通信的端口  
 通信的两端都有Socket，网络通信其实就是Socket间的通信，数据在两个Socket间通过IO传输

 UDP协议相关对象：  
 DatagramPacket：数据报包//数据报包  
 DatagramSocket：数据报报的发送和接受点

 
```
	/*UDP传输的发送端*/
	DatagramSocket ds = new DatagramSocket();//建立udp的socket服务,有SocketException异常
	String str = "udp传输内容";
	byte[] buf = str.getBytes();
	DatagramPacket dp = 
		new DatagramPacket(buf,buf.length,InetAddress.getName("192.168.1.1"),10000);//将要发送的数据封装到数据包中，10000为端口号
	ds.send(dp);//通过upd的socket服务将数据包发送出去
	ds.close();//关闭socket服务
	
	/*接收端，无连接*/
	DatagramSocket dg = new DatagramSocket(10000);//接受数据需要明确端口号
	byte[] buf = new byte[1024];
	DatagramPacket dp = new DatagramPacket(buf,buf.length);//创建数据包，用于存储接受到的数据。可以通过数据包的方法解析
	dg.receive(dp);//使用接受方法存储到数据包中，阻塞式
	Stirng ip = dp.getAddress().getHostAddress();//获取ip地址
	int port = dp.getPort();//获取发送端的端口，发送端的Socket对象没有指定端口，所以是随机的
	String text = new String(dp.getData(),0,dp.getLength());//更多方法详见DatagramPacket对象API
	dg.close();
	//实现同时的发送和接收则可以结合多线程技术，255为广播地址即1-244地址的用户都接收的到数据包

```
 TCP协议相关对象：  
 Socket：实现客户端的端点  
 ServerSocket：服务端的端点，可以连接多个客户端  
 socket流：底层建立，只要连接建立成功并建立了数据传输通道就存在，说明存在输入和输出，可以通过Socket对象来获取（getOutputStream(),getInputStream()）//通过流技术可以对数据文件进行更多的操作

 
```
	/*客户端*/
	Socket socket = new Socket("192.168.1.100",10002);//创建tcp客户端socket服务，明确要连接的主机
	OutputStream out = socket.getOutputStream();获取socket流中的输出流
	out.write("tcp演示".getBytes());//在流中写入指定数据
	
	InputStream in = socket.getInputStream();//读取服务端返回的数据
	byte[] buf = new byte[1024];
	int len = in.read();
	String text = new String(buf,0,len);
	System.out.println()
	
	socket.close();//关闭资源
	
	/*服务端*/
	ServerSocket ss = new ServerSocket(10002);//服务端提供连接端口
	Socket s = ss.accept();//获取连接过来的客户端对象
	String ip = s.getInetAddress().getHostAddress();//获取客户端ip地址
	InputStream in = s.getInputStream();//通过客户端对象获取socket流的输入流读取客户端发来的数据（用的是客户端的流）
	bytes[] buf = new bytes[1024];
	int len = in.read(buf);
	String text = new String(buf,0,len);
	System.out.println(ip+":"+text);
	
	OutputStream out = s.getOutputStream();//给客户端返回数据
	out.write("收到"。getBytes());
	
	s.close();
	ss.close();

```
 上传文本文件实例：

 
```
	/*客户端用流读取本地文本文件，并通过socket流传给服务端，服务端通过socket流获取数据后写入本地文本文件时
	 *要定义结束标记，不然客户端循环读取结束后，服务端循环写入并未停止，会造成阻塞
	 *第二种方法：告诉服务端，客户端写完了->shutdownOutput()方法-shutdownInput()//设置结束标记或将流置于末尾
	*/
	//客户端
	Socket s = new Socket("192.168.1.100",10005);
	BuffereReader bufr = new BufereReader(new FileReader("client.txt"));
	PrintWriter out = new PrintWriter(s.getOutputStream(),true);
	String line = null;
	while((line=bufr.redLine())!=null){
		out.println(line);
	}
	//out.println("over");//结束标记
	s.shutdownOutput();//告诉服务端，客户端的此输出流结束了
	BuffereReader bufIn = new BuffereReader(new InputStreamReader(s.getInputStream()));
	String str = bufr.readLine();
	System.out.println(str);//上传成功
	bufr.close();
	s.close();
	//服务端
	ServerSocket ss = new ServerSocket(10005);
	Socket s= ss.accept();
	BuffereReaderReader bufIn = new BuffereReader(new InputStreamReader(s.getInputStream()));
	BuffereWriter bufw = new BuffereWriter(new FileWriter("server.txt"));
	String line = null;
	while((line=bufIn.readLine())!=null){
		//if("over".equals(line))
		//	break;
		bufw.write(line);
		bufw.newLine();
		bufw.flush();
	}
	PrintWriter out = new PrintWriter(s.getOutputStream(),true);
	out.println("上传成功");
	bufw.close();
	s.close();
	ss.close();

```
 上传图片文件实例，服务端运用多线程

 
```
	//客户端
	Socket s = new Socket("192.168.1.100",10006);
	FileInputStream fis = new FileInputStream("c:\\0.bmp");
	OutputStream out = s.getOuputStream();
	byte[] buf = new byte[1024];
	int len = 0;
	while((len=fis.read(buf))!=-1){
		out.write(buf,0,len);
	}
	s.shutdownOutput();
	InputStream in = s.getInputStream();
	byte[] bufIn = new byte[1024];
	int lenIn = in.read(buf);
	String text = new String(buf,0,lenIn);
	System.out.println(text);
	fis.close();
	s.close();
	//服务端
	ServerSocket ss = new ServerSocket(10006);
	
	while(true){
		Socket s= ss.accept();
		new Thread(new UploadTask(s).start());//每有一个客户端连接到服务端则创建一个线程
	}

	class UploadTask{
		private Socket s;
		public UploadTask(Socket s){
			this.s = s;
		}
		public void run(){
			int count = 0;
			String ip = s.getInetAddress().getHostAddress();
			System.out.println(ip+"...connected")
			try{
			InputStream in = s.getInputStream();
			File dir = new File("c:\\pic");
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dir,ip+".bmp");
			while(file.exists()){//如果文件已经存在于服务端
				file = new File(dir,ip+"("+(++count)+").bmp")
			}
			//上传文件可以通过File获取上传文件的文件名知道其文件类型或运用某些工具类
			FileOutputStream fos = new FileOutStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len=in.read(buf))!=-1){
				fos.writer(buf,0,len);
			}
			OutputStream out = s.getOutputStream();
			out.write("上传成功“.getBytes());
			fos.close();
			s.close();
			}catch(){}
		}
	}

```
 常见客户端和服务端：  
 客户端->浏览器  
 服务器->Tomcat  
 //可以自定义服务端，用浏览器通过指定的端口访问http://192.168.1.100:9090, 浏览器会自动解析流中的数据（字符文本/html等超文本标记）  
 浏览器发给服务端的信息:  
 ![1](https://img-blog.csdnimg.cn/20181031232955123.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
```
	//模拟浏览器发送给Tomcat服务端的消息并获取信息
	class MyBrowser{
		public static void main(String[] args){
			Socket s = new Socket("192.168.199.100",8080);
			PrintWriter out = new PrintWriter(s.getOutputStream(),true);
			out.println("GET /myweb/1.html HTTP/1.1");
			out.println("Accept: */*");
			out.println("Host: 192.168.1.100:8080");
			out.println("Connection: close");
			out.println("");
			
			InputStream in = s.getInputStream();
			byte[] buf = new byte[1024];
			int len = in.read(buf);
			String str = new String(buf,0,len);
			System.out.println(str);
			s.close();
		}
	}

```
 上面模拟浏览器的例子效果：  
 ![2](https://img-blog.csdnimg.cn/20181031233021841.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 由图可知，没有解析应答消息头和应答体（html）的内容

 URL和URLConnection类：

 
```
	String str_url = "http://192.168.1.100:8080/myweb/1.html?name=lisi";//需要解析
	URL url = new URL(str_url);
	url.getProtocol();//http
	url.getHost();//192.168.1.100
	url.getPort();//8080
	url.getFile();// /myweb/1.html?name=lisi
	url.getPath();// /myweb/1.html
	url.getQuery();//name=lisi
	InputStream in = url.openStream();//输出流中的数据时，只显示应答体，应答消息头被解析了
	//↑打开此URL的连接并返回一个用于从该连接连续读入的InputStream流
	URLConnection conn = url.openConnection();//获取url对象的URL连接器对象，将连接封装成了对象
	//↑java中内置的可以解析具体协议的对象，就是解析了应答消息头
	conn.getHeaderField("Content-Type");// text/html->可以通过此对象获取应答消息头的信息
	conn.getInputStream();//也可以获取Socket的输入输出流，openStream方法就是对此功能进行了封装

```
 开发常见网络结构：  
 1.C/S-> Client/Server  
 特点：  
 该结构的软件，客户端和服务端都需要编写  
 开发成本较高，维护较为麻烦  
 好处：  
 客户端在本地可以分担一部分运算  
 2.B/S-> Browser/Server  
 特点：  
 该结构的软件，只开发服务器端，不开发客户端，因为客户端直接由浏览器取代  
 开发成本相对低，维护更为简单  
 缺点：  
 所有的运算都要在服务端完成

 
--------
 
## []()反射机制

 26.反射机制  
 Java反射机制时在运行状态中，对于任意一个类（class文件），都能知道这个类的所有属性和方法  
 对于任意一个对象，都能够调用它的任意一个方法  
 这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制（反射包：relect）  
 应用场景：一个独立运行的应用程序对外提供了接口，外部有实现了接口的类，应用程序会通过配置文件中的指定名称加载其字节码文件，获取其中的内容并调用  
 Tomcat：提供了处理请求和应答的方式，因为具体的处理动作不同，所以对外提供了接口（Servlet），由开发者来实现具体请求和应答处理，用配置文件调用

 获取Class对象的三种方法：

 
```
	//已知有Person类
	public static void getClassObject_1(){//1.Object类中的getClass方法，必须要明确具体的类并创建对象
		Person p = new Person();
		Class clazz = p.getClass
	}
	public static void getClassObject_2(){//2.任何数据类型都具有一个静态的属性.class来获取其对应的Class对象
		Class clazz = Person.class;//还是要明确用到类中的静态成员，不够扩展
	}
	//主要获取方法，用Class类中的forName方法，只需要名称，扩展性强
	public static void getClassObject_3(){
		String className = "cn.itcast.bean.Person";//明确包名，可以通过配置文件获取
		Class clazz = Class.forName(className);
	}
	/*区别
	 *cn.itcast.bean.Person p = new cn.itcast.bean.Person();//根据类的名称找该类的字节码文件，并加载进内存
	 *String name = "cn.itcast.bean.Person";
	 *Class clazz = Class.forName(name);//寻找该类文件，并加载进内存，再产生Class对象
	*/

```
 获取Class对象中的构造方法，字段，方法：

 
```
	//构造方法
	public static void createNewObject() {
		String name = "cn.itcast.bean.Person";
	 	Class clazz = Class.forName(name);
		Object obj = clazz.newInstance();//创建此Class对象所表示的类的一个新实例，空参构造方法初始化
		//不使用空参数构造
		Constructor construtor = clazz.getConstructor(String.class,int.class);//获取参数为String和int的构造方法对象
		Object obj = construtor.newInstance("a",20);//同无参通过newInstance方法进行对象的初始化
	}
	//字段
	public static void getFieldDemo(){
		Class clazz = Class.forName("cn.itcast.bean.Person");
		Field field = clazz.getField("age");//只获取公共字段
		Filed = clazz.getDeclaredField("age");//只获取本类，但包含私有
		field.setAccessible(true);//对私有字段的访问，取消权限检查（不建议使用）
		Object obj = clazz.newInstance();
		field.set(obj,89);//设置age的值
		Object o = field.get(obj);
		System.out.println(o);
	}
	//方法
	public static void getMethodDemo(){
		Class clazz = Class.forName("cn.itcast.bean.Person");
		Method[] methods = clazz.getMethods();//获取所有公有的方法
		Method method = clazz.getMethod("show",String.class,int.class);//获取有参的show方法，无参用null表示
		Object obj = clazz.newInstance();
		method.invoke(obj,null);//获取对象，运行show方法
		//操作静态方法时，invoke(null,"...")
	}

```
   
  