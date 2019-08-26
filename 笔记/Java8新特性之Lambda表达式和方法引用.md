---
title: Java8新特性之Lambda表达式和方法引用
date: 2019-07-25 15:33:45
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/97176111](https://blog.csdn.net/MOKEXFDGH/article/details/97176111)   
    
  ### []()Lambda表达式

 Lambda表达式是 Java 8 新特性中的一种新的语法形式，首先我们先来看下它的**例子**：

 
> (String s) -> System.out.println(s)
> 
>  
 **格式**：

 
> 参数列表 -> 函数主体（可有返回类型 / 抛出异常）
> 
>  
 我们可以将 Lambda 表达式理解为是一个**函数**，这个函数的特点是**匿名**（没有名称）且**可传递**（作为方法参数）的。

 而可传递是如何表现的呢？这就涉及到 Java8 新加入的**函数式接口**，其形式如下：

 
```
		public static test(String a,Predicate<Integer> predicate){...}
		//调用test方法，传入有返回值的 Lambda 表达式
		test("mo",(Integer i) -> i>0);//参数类型：String，boolean
		//对应的函数式接口如下
		@FunctionalInterface
		public interface Predicate<T>{
			boolean test(T t);
		}

```
 特点：**只定义一个抽象方法的接口**，@FunctionalInterface 注解可以用于检查接口符不符合函数式接口的规则，不符合会给出异常。

 函数式接口和 Lambda 表达式之间的关系，就如同接口的实例和接口之间的关系，即 Lambda 表达式本质是函数式接口的实现。

 **函数描述符**即函数式接口的抽象方法的签名（一种表现形式），Lambda 表达式的签名会对应函数式接口的抽象方法的签名，如：

  
  * 函数式接口：Predicate  
     函数描述符：T -> boolean 
  * 函数式接口：Consumer  
     函数描述符：T -> void 
  * 函数式接口：Function<T,R>  
     函数描述符：T -> R 
  * 函数式接口：Runnable  
     函数描述符：() -> void  
```
		Runnable r = () -> System.out.println("test");
		//上面相当于实现了 Runnbale 的函数式接口，相当于如下：
		Runnable r = new Runnable(){
			public void run(){
				System.out.println("test");
			}
		};

```
 在前面的例子中我们指定了 Lambda 表达式的类型，其实 Lambda 表达式的类型是可以自动通过上下文推断出来的，如：

 
```
		Predicate<Integer> predicate = (Integer i) -> i>0;
		//根据上下文，可以简写为（作为参数也一样）：
		Predicate<Integer> predicate = i -> i>0;

```
 
--------
 
### []()方法引用

 _[参考](https://blog.csdn.net/TimHeath/article/details/71194938)_  
 方法引用是**运用在 Lambda 表达式之中**的，也是一种新的语法形式。

 在我们写表达式的时候，我们的方法主体可能在某个其它类中已经实现了，那么我们可以直接引用该类中的方法，而不需要我们再写一遍，如：

 
```
		Consumer<String> consumer = s -> System.out.println(s);
		//因为println其实是System.out下的一个静态实例方法，我们也可以直接引用，如下：
		Consumer<String> consumer = System.out::println;
		//这两种方法都可以打印出我们传入的字符串
		consumer.accept("moke");

```
 由上面的例子可知，方法引用使用的**符号为 “::”**，**左边是类名或者某个对象的引用，而右边是相应的方法名或者new**，根据引用的不同可以分成三类引用：

  
  * **引用方法** 
  * **引用构造器** 
  * **引用数组**  下面为这三类引用的示例：  
 **1.引用方法**  
 对象引用::实例方法名

 
```
		Consumer<String> consumer = s -> System.out.println(s);
		Consumer<String> consumer = System.out::println;
		consumer.accept("moke");

```
 类名::静态方法名

 
```
		//Function<Long, Long> f = x -> Math.abs(x);
		Function<Long, Long> f = Math::abs;
		Long result = f.apply(-3L);

```
 类名::实例方法名  
 _若Lambda表达式的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时才可以使用_

 
```
		//BiPredicate<String, String> b = (x,y) -> x.equals(y);
		BiPredicate<String, String> b = String::equals;
		b.test("abc", "abcd");

```
 **2.引用构造器**

 
```
		//Function<Integer, StringBuffer> c = n -> new StringBuffer(n); 
		Function<Integer, StringBuffer> c = StringBuffer::new;//构造方法：StringBuffer(int capacity)
		StringBuffer buffer = c.apply(10);

```
 **3.引用数组**

 
```
		// Function<Integer, int[]> array = n -> new int[n];
		Function<Integer, int[]> array = int[]::new;
		int[] arr = array.apply(10);

```
   
  