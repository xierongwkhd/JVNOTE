---
title: Java8新特性之Stream API
date: 2019-07-25 18:32:29
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/97272894](https://blog.csdn.net/MOKEXFDGH/article/details/97272894)   
    
  ### []()概述

 Stream API 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用fork/join并行方式来拆分任务和加速处理过程。总得来说，Stream API 就是提供了一种高效且易于使用的处理数据的方式。

 _注：需要会使用 Lambda 表达式 [地址](https://blog.csdn.net/MOKEXFDGH/article/details/97176111)_

 
--------
 
### []()Stream 的流

 Stream 不是集合元素，它不是数据结构并不保存数据，而属于流。  
 流是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。  
 区别：集合注重的是数据的保存，而流注重的是数据的计算

 Stream 流的特点：

  
  * 不会存储元素 
  * 不会改变源对象 
  * 操作是延迟执行的  流的使用步骤：

  
  * 获取一个数据源 
  * 数据转换，即中间操作 
  * 执行操作获取想要的结果并终止操作  **通过数据源创建 Stream 流**  
 **1.Collection**

 
```
		List<String> list = new ArrayList<>();
		Stream<String> stream1 = list.stream();
		Stream<String> stream2 = list.parallelStream();

```
 注：parallelStream获取的是并行执行的流，底层使用的是 [ForkJoin框架](https://blog.csdn.net/MOKEXFDGH/article/details/89423910#_331)  
 **2.数组**

 
```
		Stream<T> stream3 = Arrays.stream(T array);//int,long,double

```
 **3.值**

 
```
		Stream<String> stream4 = Stream.of("a","b","c");

```
 **4.函数（无限流）**

 
```
		Stream<Integer> stream5 = Stream.iterate(0, (x) -> x + 2);
		Stream<String> stream6 = Stream.generate(() -> Math.random()).limit(5).forEach(System.out::println);

```
 **流的中间操作**  
 两类操作：

  
  * Intermediate（中间操作）：可以有多个，即对流的数据进行映射或者过滤，每一个操作会返回一个新的流给下一个操作。这类操作是惰性的，即此时还没有对流进行遍历，操作也还没有真正执行。 
  * Terminal（终止操作）：只能有一个，执行前会将Intermediate的多个操作融合起来，该操作执行时才会真正的遍历流并消耗流，最终会返回一个结果。  常见的操作：

  
  * Intermediate：  
     map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 findFirst、 peek、 limit、 skip、 parallel、 sequential、 unordered 
  * Terminal：  
     forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator  
--------
 
### []()举例

 **1.filter（过滤）、forEach（遍历）**

 
```
		int[] nums = {2,1,4,5,8};
		IntStream stream = Arrays.stream(nums);
		stream.filter((x) -> x%2==0).forEach(System.out::println);

```
 输出结果如下：  
 ![1](https://img-blog.csdnimg.cn/20190725172258819.png)  
 **2.limit（返回前n个）、skip（丢弃前n个）、sorted（排序）**

 
```
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		Stream<Integer> stream = list.stream();
		list = stream.limit(5).skip(2).sorted((a,b) -> b.compareTo(a)).collect(Collectors.toList());
		System.out.println(list);

```
 输出结果如下：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190725174034217.png)  
 **3.allMatch(所有)、anyMatch（存在）、noneMatch（没有）**

 
```
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		boolean flag = list.stream().allMatch(x -> x > 2);
		System.out.println("allMatch:"+flag);
		flag = list.stream().anyMatch(x -> x > 2);
		System.out.println("anyMatch:"+flag);
		flag = list.stream().noneMatch(x -> x > 2);
		System.out.println("noneMatch:"+flag);

```
 输出结果如下：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190725175038502.png)  
 **4.min、max、distinct（不重复）**

 
```
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(2);
		int a = list.stream().max(Integer::compare).get();
		System.out.println("max:"+a);
		a = list.stream().min(Integer::compare).get();
		System.out.println("min:"+a);
		list = list.stream().distinct().collect(Collectors.toList());
		System.out.println(list);

```
 输出结果如下：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/2019072518015574.png)  
 **5.map、flatMap（把 input Stream 的每一个元素，映射成 output Stream 的另外一个元素，flatMap对应一对多情况）**

 
```
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list = list.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(list);

```
 输出结果如下：  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190725181023318.png)

 **注：**  
 1.Collector是专门用来作为Stream的collect方法的参数的，而 Collectors 是一个工具类，是JDK预实现Collector的工具类，它内部提供了多种Collector，如：

  
  * toCollection 
  * toList 
  * toSet 
  * counting 
  * …  2.无限流一般需要有限制的操作，如：anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit 等

   
  