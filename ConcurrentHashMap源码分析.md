---
title: ConcurrentHashMap源码分析
date: 2019-03-19 21:05:41
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88660349](https://blog.csdn.net/MOKEXFDGH/article/details/88660349)   
    
  ### 文章目录


  * [ConcurrentHashMap](#ConcurrentHashMap_3)
    * [简介](#_4)
    * [源码分析](#_12)
      * [属性](#_13)
      * [构造方法](#_51)
      * [put 方法](#put__87)
      * [get 方法](#get__112)
      * [put 方法（Segment中）](#put_Segment_136)  


 
--------
 
# []()ConcurrentHashMap

 
## []()简介

 **HashMap：** 线程不安全，在并发环境下，扩容时可能会形成环形链表（1.8之前），导致在 get 操作时，cpu空转。

 **HashTable：** 相比 HashMap 不允许 key 和 value 为空，且是线程安全的。但所有的 get、put 方法都加上了 synchronized 锁，相当于给整个哈希表加了一把大锁，当一个线程访问时，其它线程只能等待，耗时太大。

 **ConcurrentHashMap：** 采用分段锁策略。多个线程访问不同的分段时，不存在锁竞争；只有同一分段操作才需要考虑线程同步问题。

 
--------
 
## []()源码分析

 
### []()属性

 
```
		transient volatile Node<K,V>[] table;
		final Segment<K,V>[] segments;

```
 主要属性相似于 HashMap ，这里主要分析两个不同的：segments、HashEntry（Segments内部）。

 **Segments：** 继承 ReentrantLock，一个 segements 对应一个子哈希表，Segment 内部维护了一个 HashEntry 数组（类似于HashMap）。

 
```
		static final class Segment<K,V> extends ReentrantLock implements Serializable {

		    private static final long serialVersionUID = 2249069246763182397L;
		
		    static final int MAX_SCAN_RETRIES = Runtime.getRuntime().availableProcessors() > 1 ? 64 : 1;
		
		    transient volatile HashEntry<K,V>[] table;
		
		    transient int count;
		
		    transient int modCount;
		
		    transient int threshold;
		
		    final float loadFactor;
		}

```
 **HashEntry：** 目前最小的逻辑处理单元。

 
```
		static final class HashEntry<K,V> {
	        final int hash;
	        final K key;
	        volatile V value;
	        volatile HashEntry<K,V> next;
	        //其他省略
	}    

```
 
--------
 
### []()构造方法

 
```
		//初始化默认参数：initialCapacity为16，loadFactor为0.75，concurrentLevel为16
		public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        	if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)throw new IllegalArgumentException();
           //MAX_SEGMENTS 为1<<16=65536，也就是最大并发数为65536
           	if (concurrencyLevel > MAX_SEGMENTS)
               concurrencyLevel = MAX_SEGMENTS;
           //2的sshif次方等于ssize，例:ssize=16,sshift=4;ssize=32,sshif=5
	        int sshift = 0;
	        //ssize 为segments数组长度，根据concurrentLevel计算得出
	        int ssize = 1;
	        while (ssize < concurrencyLevel) {
	        	++sshift;
	            ssize <<= 1;
	        }
	        //segmentShift和segmentMask这两个变量用于定位 segment
	        this.segmentShift = 32 - sshift;
	        this.segmentMask = ssize - 1;
	        if (initialCapacity > MAXIMUM_CAPACITY)
	            initialCapacity = MAXIMUM_CAPACITY;
	        //计算cap的大小，即Segment中HashEntry的数组长度，cap也一定为2的n次方.
	        int c = initialCapacity / ssize;
	        if (c * ssize < initialCapacity)
	            ++c;
	        int cap = MIN_SEGMENT_TABLE_CAPACITY;
	        while (cap < c)
	            cap <<= 1;
	        //创建segments数组并初始化第一个Segment，其余的Segment延迟初始化
	        Segment<K,V> s0 = new Segment<K,V>(loadFactor, (int)(cap * loadFactor), (HashEntry<K,V>[])new HashEntry[cap]);
	        Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
	        UNSAFE.putOrderedObject(ss, SBASE, s0); 
	        this.segments = ss;
	    }

```
 
--------
 
### []()put 方法

 **put 方法步骤：**

  
  2. 定位 segment 并确保 segment 已经初始化。 
  4. 调用 Segment 的 put 方法。  
```
		public V put(K key, V value) {
	        Segment<K,V> s;
	        //concurrentHashMap不允许key/value为空
	        if (value == null)
	            throw new NullPointerException();
	        //计算 hash 值
	        int hash = hash(key);
	        //返回的hash值无符号右移segmentShift位与segmentMask进行位运算，定位segment
	        int j = (hash >>> segmentShift) & segmentMask;
	        if ((s = (Segment<K,V>)UNSAFE.getObject          // nonvolatile; recheck
	             (segments, (j << SSHIFT) + SBASE)) == null) //  in ensureSegment
	            s = ensureSegment(j);
	        return s.put(key, hash, value, false);
	    }

```
 **segmentMask：** 段位码，通过 Segments 长度-1得到，因为长度是2的幂次方，所以减一的数的所有bit位都是1，可以更好地保证散列的均匀性。

 **segmentShift：** 段偏移量，segmentShift=32-sshift，计算得出的hash值最大为32位，无符号右移segmentShift，则意味着只保留高几位（其余位是没用的），然后与段掩码segmentMask位运算来定位Segment。

 
--------
 
### []()get 方法

 get没有使用锁同步，而是使用轻量级同步volatile原语sun.misc.Unsafe.getObjectVolatile(Object, long)，保证读到的是最新的对象。

 
```
		public V get(Object key) {
		    Segment<K,V> s; 
		    HashEntry<K,V>[] tab;
		    int h = hash(key);
		    long u = (((h >>> segmentShift) & segmentMask) << SSHIFT) + SBASE;
		    /*先定位到Segment*/
		    if ((s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)) != null &&
		        (tab = s.table) != null) {
		        /*再遍历segment中的HashEntry数组*/
		        for (HashEntry<K,V> e = (HashEntry<K,V>) UNSAFE.getObjectVolatile
		                 (tab, ((long)(((tab.length - 1) & h)) << TSHIFT) + TBASE);
		             e != null; e = e.next) {
		            K k;
		            if ((k = e.key) == key || (e.hash == h && key.equals(k)))
		                return e.value;
		        }
		    }
		    return null;
		}

```
 
--------
 
### []()put 方法（Segment中）

 由于Segment的put方法需要对共享变量进行写操作，所以put方法是需要加锁操作的，put的步骤：

  
  2. 判断是否扩容（扩容时不针对整个ConcurrentHashMap，而是只对需要扩容的Segment扩容） 
  4. 定位HashEntry 
  6. 放置元素  
```
		final V put(K key, int hash, V value, boolean onlyIfAbsent) {
		     /*put方法要加锁*/
		    HashEntry<K,V> node = tryLock() ? null :
		        scanAndLockForPut(key, hash, value);
		    V oldValue;
		    try {
		        HashEntry<K,V>[] tab = table;
		        int index = (tab.length - 1) & hash;
		          /*定位HashEntry，可以看到之前在ConcurrentHashMap的put方法中产生的hash值在定位HashEntry时也用到了*/
		        HashEntry<K,V> first = entryAt(tab, index);
		        for (HashEntry<K,V> e = first;;) {
		            if (e != null) {
		                K k;
		                if ((k = e.key) == key ||
		                    (e.hash == hash && key.equals(k))) {
		                    oldValue = e.value;
		                    if (!onlyIfAbsent) {
		                        e.value = value;
		                        ++modCount;
		                    }
		                    break;
		                }
		                e = e.next;
		            }
		            else {
		                if (node != null)
		                    node.setNext(first);
		                else
		                    node = new HashEntry<K,V>(hash, key, value, first);
		                int c = count + 1;
		                  /*扩容*/
		                if (c > threshold && tab.length < MAXIMUM_CAPACITY)
		                    rehash(node);
		                else
		                    setEntryAt(tab, index, node);
		                ++modCount;
		                count = c;
		                oldValue = null;
		                break;
		            }
		        }
		    } finally {
		        unlock();
		    }
		    return oldValue;
		}

```
   
  