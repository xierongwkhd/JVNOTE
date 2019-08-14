---
title: HashMap源码分析
date: 2019-03-18 21:41:25
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88628558](https://blog.csdn.net/MOKEXFDGH/article/details/88628558)   
    
  ### 文章目录


  * [HashMap](#HashMap_3)
    * [简介](#_4)
    * [底层数据结构](#_12)
      * [JDK 1.8 之前](#JDK_18__13)
      * [JDK 1.8 之后](#JDK_18__36)
    * [源码分析](#_43)
      * [属性](#_44)
      * [节点类](#_85)
      * [构造方法](#_153)
      * [put 方法（*）](#put__211)
      * [get 方法](#get__294)
      * [resize 方法](#resize__326)
      * [entrySet 方法](#entrySet__416)  


 
--------
 
# []()HashMap

 
## []()简介

  
  * 基于哈希表的 Map 接口实现，用于存放键值对 
  * JDK1.8之前由数组（主体）、链表（解决哈希冲突）组成，而1.8之后当链表长度大于8时，会将链表转为红黑树，以减少搜索时间。  红黑树详解：[地址](https://blog.csdn.net/qq_36523667/article/details/80961912)

 
--------
 
## []()底层数据结构

 
### []()JDK 1.8 之前

 **底层数据结构**：数据+链表（链表散列）

 **键值对存放过程：**

  
  2. key 的 hashCode 经过**扰动函数**处理过后得到 hash 值 
  4. 然后 (n-1) & hash 判断当前元素存放的位置（n为数组长度） 
  6. 判断当前位置是否存在元素，即判断 hash 值与 key 是否相同 
  8. 相同则直接覆盖，不同则通过**拉链法**解决哈希冲突  **扰动函数**：其实就是 HashMap 内部的 hash 方法，用于减少哈希冲突的产生  
 1.8 版 hash 方法源码：

 
```
		static final int hash(Object key) {
	      int h;
	      // key.hashCode()：返回散列值也就是hashcode
	      // ^ ：按位异或
	      // >>>:无符号右移，忽略符号位，空位都以0补齐
	      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	  }

```
 **拉链法**：  
 ![1](https://img-blog.csdnimg.cn/20190317204526707.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
### []()JDK 1.8 之后

 主要区别在于解决哈希冲突时的方法，当链表长度大于8时，会将链表转为红黑树，以减少搜索时间。

 **改进**：  
 ![2](https://img-blog.csdnimg.cn/20190317204717220.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
--------
 
## []()源码分析

 
### []()属性

 
```
		public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
		    // 序列号
		    private static final long serialVersionUID = 362498820763181265L;    
		    // 默认的初始容量是16
		    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;   
		    // 最大容量
		    static final int MAXIMUM_CAPACITY = 1 << 30; 
		    // 默认的加载因子
		    static final float DEFAULT_LOAD_FACTOR = 0.75f;
		    // 链表结点数大于这个值时会转成红黑树
		    static final int TREEIFY_THRESHOLD = 8; 
		    // 链表结点数小于这个值时树转链表
		    static final int UNTREEIFY_THRESHOLD = 6;
		    // 红黑树对应的table的最小大小
		    static final int MIN_TREEIFY_CAPACITY = 64;
		    // 存储元素的数组，总是2的幂次倍
		    transient Node<k,v>[] table; 
		    // 存放具体元素的集,?
		    transient Set<map.entry<k,v>> entrySet;
		    // 存放元素的个数，注意这个不等于数组的长度。
		    transient int size;
		    // 每次扩容和更改map结构的计数器
		    transient int modCount;   
		    // 临界值 当实际大小(容量*填充因子)超过临界值时，会进行扩容
		    int threshold;
		    // 加载因子
		    final float loadFactor;
		}

```
 **loadFactor**：加载因子  
 越趋近于1，则说明数组中存放的数据越多，数据越密。  
 越趋近于0，则说明数组中存放的数据越少，数据越疏。  
 默认值为 0.75f

 **threshold**：临界值

 
> threshold = capacity * loadFactor （容量 * 加载因子）
> 
>  
 当 size >= threshold 时，说明需要扩容。

 
### []()节点类

 **Node节点：**

 
```
		// 继承自 Map.Entry<K,V>
		static class Node<K,V> implements Map.Entry<K,V> {
		       final int hash;// 哈希值，存放元素到hashmap中时用来与其他元素hash值比较
		       final K key;//键
		       V value;//值
		       // 指向下一个节点
		       Node<K,V> next;
		       //构造方法
		       Node(int hash, K key, V value, Node<K,V> next) {
		            this.hash = hash;
		            this.key = key;
		            this.value = value;
		            this.next = next;
		        }
		        //获取key，value方法
		        public final K getKey()        { return key; }
		        public final V getValue()      { return value; }
		        //重写toString
		        public final String toString() { return key + "=" + value; }
		        // 重写hashCode()方法
		        public final int hashCode() {
		            return Objects.hashCode(key) ^ Objects.hashCode(value);
		        }
		
		        public final V setValue(V newValue) {
		            V oldValue = value;
		            value = newValue;
		            return oldValue;
		        }
		        // 重写 equals() 方法
		        public final boolean equals(Object o) {
		            if (o == this)
		                return true;
		            if (o instanceof Map.Entry) {
		                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
		                if (Objects.equals(key, e.getKey()) &&
		                    Objects.equals(value, e.getValue()))
		                    return true;
		            }
		            return false;
		        }
		}

```
 **树节点类**

 
```
		static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
	        TreeNode<K,V> parent;// 父
	        TreeNode<K,V> left;// 左
	        TreeNode<K,V> right;// 右
	        TreeNode<K,V> prev; 
	        boolean red;// 判断颜色，红黑树
	        TreeNode(int hash, K key, V val, Node<K,V> next) {
	            super(hash, key, val, next);
	        }
	        // 返回根节点
	        final TreeNode<K,V> root() {
	            for (TreeNode<K,V> r = this, p;;) {
	                if ((p = r.parent) == null)
	                    return r;
	                r = p;
	       }
	   }

```
 
### []()构造方法

 
```
		// 默认构造方法
	    public HashMap() {
	        this.loadFactor = DEFAULT_LOAD_FACTOR; // all   other fields defaulted
	     }
	     
	     // 包含另一个“Map”的构造方法
	     public HashMap(Map<? extends K, ? extends V> m) {
	         this.loadFactor = DEFAULT_LOAD_FACTOR;
	         putMapEntries(m, false);
	     }
	     
	     // 指定“容量大小”的构造方法
	     public HashMap(int initialCapacity) {
	         this(initialCapacity, DEFAULT_LOAD_FACTOR);
	     }
	     
	     // 指定“容量大小”和“加载因子”的构造方法
	     public HashMap(int initialCapacity, float loadFactor) {
	         if (initialCapacity < 0)
	             throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
	         if (initialCapacity > MAXIMUM_CAPACITY)
	             initialCapacity = MAXIMUM_CAPACITY;
	         if (loadFactor <= 0 || Float.isNaN(loadFactor))
	             throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
	         this.loadFactor = loadFactor;
	         this.threshold = tableSizeFor(initialCapacity);
	     }

```
 **构造方法中的 putMapEntries 方法**：

 
```
		final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
		    int s = m.size();//m元素个数
		    if (s > 0) {
		        // 判断table是否已经初始化
		        if (table == null) { 
		            // 未初始化，则通过s计算 m 的 size（t）
		            float ft = ((float)s / loadFactor) + 1.0F;
		            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
		                    (int)ft : MAXIMUM_CAPACITY);
		            // 计算得到的t大于默认阈值，则初始化阈值
		            if (t > threshold)
		                threshold = tableSizeFor(t);
		        }
		        // 已初始化，并且m元素个数大于阈值，进行扩容
		        else if (s > threshold)
		            resize();
		        // 使用 putVal 将m中的所有元素添加至HashMap中
		        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
		            K key = e.getKey();
		            V value = e.getValue();
		            putVal(hash(key), key, value, false, evict);
		        }
		    }
		}

```
 
### []()put 方法（*）

 HashMap 只提供 put 方法用于添加元素，putVal 方法只在内部调用。

 **put（1.8）**：

 
```
		public V put(K key, V value) {
		    return putVal(hash(key), key, value, false, true);
		}

```
 **putVal**：  
 添加元素过程：

  
  2. 判断是否要扩容 
  4. 定位到的位置没有元素，直接插入 
  6. 有元素，则比较 key 值，相同则直接覆盖 
  8. 不相同，判断此位置是否是一个树节点，如果不是就遍历链表插入（长度大于8转为红黑树） 
  10. 如果是，则调用 putTreeVal 方法插入树中  
```
		final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
			Node<K,V>[] tab;//table
			Node<K,V> p;//插入位置节点
			int n, i;//n：table长度，i：位置
			//判断table是否初始化，或者长度为0
			if ((tab = table) == null || (n = tab.length) == 0)
        		n = (tab = resize()).length;
        	//(n-1)&hash判断插入位置，没有元素则直接插入
        	if ((p = tab[i = (n - 1) & hash]) == null)
        		tab[i] = newNode(hash, key, value, null);
        	else{//已经有元素
				Node<K,V> e;
				K k;//插入位置节点key
				//要插入元素和插入位置节点的hash和key相同
				if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                	// 将第一个元素赋值给e，用e来记录
                	e = p;
               	else if (p instanceof TreeNode)//不相同，且p为红黑树节点
	            	// 放入树中
	            	e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
	            else {//不相同，且p为链表节点
		            // 在链表最末插入结点
		            for (int binCount = 0; ; ++binCount) {
		                if ((e = p.next) == null) {
		                    p.next = newNode(hash, key, value, null);
		                    // 结点数量达到阈值，转化为红黑树
		                    if (binCount >= TREEIFY_THRESHOLD - 1)
		                        treeifyBin(tab, hash);
		                    break;
		                }
		                // 判断链表中结点的key值与插入的元素的key值是否相等
		                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))){
		                    break;//相等覆盖，操作
			            p = e;// p = p.next;
		        	}
				}
				//e不为空，则说明存在相同的节点，需要覆盖旧值
		        if (e != null) { 
		            // 记录e的value
		            V oldValue = e.value;
		            // onlyIfAbsent为false或者旧值为null
		            if (!onlyIfAbsent || oldValue == null)
		                //用新值替换旧值
		                e.value = value;
		            // 访问后回调
		            afterNodeAccess(e);
		            // 返回旧值
		            return oldValue;
		        }
        	}
		    ++modCount;
		    // 添加后实际大小大于阈值则扩容
		    if (++size > threshold)
		        resize();
		    // 插入后回调
		    afterNodeInsertion(evict);
		    return null;
		} 

```
 **put（1.7）**：

  
  2. 定位到的位置没有元素，直接插入 
  4. 有元素，遍历此位置链表，依次和插入的 key 比较，相同则直接覆盖 
  6. 不相同，则采用头插法插入元素（1.8之后是尾插入）  
### []()get 方法

 与 put 方法类似，内部调用 getNode 方法

 
```
		public V get(Object key) {
		    Node<K,V> e;
		    return (e = getNode(hash(key), key)) == null ? null : e.value;
		}
		
		final Node<K,V> getNode(int hash, Object key) {
		    Node<K,V>[] tab; 
		    Node<K,V> first, e; 
		    int n; K k;
		    if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
		        // 元素节点相同
		        if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
		            return first;
		        // 不同，且该位置不止一个节点
		        if ((e = first.next) != null) {
		            // 在树中get
		            if (first instanceof TreeNode)
		                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
		            // 在链表中get
		            do {
		                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
		                    return e;
		            } while ((e = e.next) != null);
		        }
		    }
		    return null;
		}

```
 
### []()resize 方法

 resize 方法是用于扩容的方法，遍历所有的元素并重新分配 hash 值，非常的耗时，因此应当尽量避免使用。  
 **扩容步骤：**

  
  2. 判断原本 table 的大小，如果为 0，则直接使用默认的容量和默认的加载因子。 
  4. 大于 0，则判断是否超过最大容量，超过则不进行扩容；没超过，则将容量和临界值扩充为原来的两倍。 
  6. 将新的临界值，赋给 threshold ，依据新的容量创建新的table，赋给 table。 
  8. 将原来 table 中的元素移动到新的 table 中。  
```
		final Node<K,V>[] resize() {
		    Node<K,V>[] oldTab = table;
		    int oldCap = (oldTab == null) ? 0 : oldTab.length;
		    int oldThr = threshold;
		    int newCap, newThr = 0;
		    if (oldCap > 0) {
		        //大于 0，则判断是否超过最大容量，超过则不进行扩容
		        if (oldCap >= MAXIMUM_CAPACITY) {
		            threshold = Integer.MAX_VALUE;
		            return oldTab;
		        }
		        //没超过，则将容量和临界值扩充为原来的两倍。
		        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
		            newThr = oldThr << 1;
		    }
		    else if (oldThr > 0)//如果oldCap为零，而oldThr>0，则新容量=旧临界值
		        newCap = oldThr;
		    else { 
		        //如果为 0，则直接使用默认的容量和默认的加载因子
		        newCap = DEFAULT_INITIAL_CAPACITY;
		        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
		    }
		    // 上面的第二种情况未计算临界值
		    if (newThr == 0) {
		        float ft = (float)newCap * loadFactor;
		        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE);
		    }
		    threshold = newThr;
		    @SuppressWarnings({"rawtypes","unchecked"})
		        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
		    table = newTab;
		    if (oldTab != null) {
		        //将原来 table 中的元素移动到新的 table 中
		        for (int j = 0; j < oldCap; ++j) {
		            Node<K,V> e;
		            if ((e = oldTab[j]) != null) {
		                oldTab[j] = null;
		                if (e.next == null)
		                    newTab[e.hash & (newCap - 1)] = e;
		                else if (e instanceof TreeNode)
		                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
		                else { 
		                    Node<K,V> loHead = null, loTail = null;
		                    Node<K,V> hiHead = null, hiTail = null;
		                    Node<K,V> next;
		                    do {
		                        next = e.next;
		                        // 原索引
		                        if ((e.hash & oldCap) == 0) {
		                            if (loTail == null)
		                                loHead = e;
		                            else
		                                loTail.next = e;
		                            loTail = e;
		                        }
		                        // 原索引+oldCap
		                        else {
		                            if (hiTail == null)
		                                hiHead = e;
		                            else
		                                hiTail.next = e;
		                            hiTail = e;
		                        }
		                    } while ((e = next) != null);
		                    // 原索引放到bucket里
		                    if (loTail != null) {
		                        loTail.next = null;
		                        newTab[j] = loHead;
		                    }
		                    // 原索引+oldCap放到bucket里
		                    if (hiTail != null) {
		                        hiTail.next = null;
		                        newTab[j + oldCap] = hiHead;
		                    }
		                }
		            }
		        }
		    }
		    return newTab;
		}

```
 
### []()entrySet 方法

 在指定map的构造方法中是使用 entrySet 方法遍历 map 的，而在 put 的方法中并未对 entrySet 进行赋值，这是因为有一个方法：

 
```
		public Set<Map.Entry<K, V>> entrySet(){
           Set<Map.Entry<K, V>> es;
           return (es=entrySet)==null?(es=new EntrySet()):es;
       }

```
 当 entrySet 为空时 new 一个 EntrySet 类，而其源码如下：

 
```
		final class EntrySet extends AbstractSet<Map.Entry<K, V>>{
           public final int size(){return size;}
           public final void clear(){HashMapMmc.this.clear();}

           public final Iterator<Map.Entry<K, V>> iterator(){
               return new EntryIterator();
           }
           
           public final boolean contains(Object o){
               if(!(o instanceof Map.Entry))
                   return false;
               Map.Entry<?, ?> e=(Map.Entry<?, ?>) o;
               Object key=e.getKey();
               Node<K,V> candidate=getNode(hash(key),key);
               return candidate!=null&&candidate.equals(o);
           }
           
           public final boolean remove(Object o){
               if(o instanceof Map.Entry){
                   Map.Entry<?, ?> e=(java.util.Map.Entry<?, ?>) o;
                   Object key= e.getKey();
                   Object value=e.getValue();
                   return removeNode(hash(key), key, value, true,true)!=null;
               }
                return false;   
           }
           
           //这里重写了iterator方法，返回一个EntryIterator实例
           public final Spliterator<Map.Entry<K, V>> spliterator(){
               return new EntrySpliterator<>(HashMapMmc.this,0,-1,0,0);
           }
           
           public final void forEach(Consumer<? super Map.Entry<K, V>> action){
               Node<K,V> [] tab;
               if(action==null)
                   throw new NullPointerException();
               if(size>0&&(tab=table)!=null){
                   int mc=modCount;
                   for(int i=0;i<tab.length;++i){
                       for(Node<K,V> e=tab[i];e!=null;e=e.next)
                           action.accept(e);
                   }
                   if(modCount!=mc)
                       throw new ConcurrentModificationException();
               }
           }
       }

```
 可以看到类中重写了 iterator 相关方法，返回一个EntryIterator实例，而 EntryIterator 继承 HashIterator，HashIterator 通过遍历table数组，实现对 HashMap 的遍历。

   
  