---
title: LinkedList源码分析
date: 2019-03-17 11:12:56
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88615783](https://blog.csdn.net/MOKEXFDGH/article/details/88615783)   
    
  ### 文章目录


  * [LinkedList](#LinkedList_3)
    * [简介](#_4)
    * [源码解析](#_13)
      * [内部私有节点类](#_14)
      * [构造方法](#_28)
      * [常用方法](#_41)  


 
--------
 
# []()LinkedList

 
## []()简介

  
  * 是实现了 List 接口和 Deque 接口的双端链表 
  * 底层使用链表结构使其能高效的进行插入和删除操作 
  * 不是线程安全的，如果想使其变为线程安全的，可以使用 Collections 中的 synchronizedList 方法：  
```
		List list = Collections.synchronizedList(new LinkedList());

```
 
--------
 
## []()源码解析

 
### []()内部私有节点类

 
```
		private static class Node<E> {
	        E item;//节点值
	        Node<E> next;//后继节点
	        Node<E> prev;//前驱节点
	
	        Node(Node<E> prev, E element, Node<E> next) {
	            this.item = element;
	            this.next = next;
	            this.prev = prev;
	        }
	    }

```
 
### []()构造方法

 默认构造方法：

 
```
		public LinkedList(){}

```
 包含指定集合构造方法：

 
```
		public LinkedList(Collection<? extends E> c) {
	        this();
	        addAll(c);
	    }

```
 
### []()常用方法

 1.add、linkLast

 
```
		//添加元素到末尾
		public boolean add(E e) {
	        linkLast(e);//这里就只调用了这一个方法
	        return true;
	    }
		
		//指定位置添加元素
		public void add(int index, E element) {
	        checkPositionIndex(index); //检查索引是否处于[0-size]之间
	
	        if (index == size)//添加在链表尾部
	            linkLast(element);
	        else//添加在链表中间
	            linkBefore(element, node(index));//参数：节点值，index位置的节点
	    }

		//将集合插入到末尾
		public boolean addAll(Collection<? extends E> c) {
	        return addAll(size, c);//调用指定位置插入集合的方法
	    }
		
		//将元素添加到链表头
		 public void addFirst(E e) {
	        linkFirst(e);
	    }
		
		//将元素添加到链表末尾，同add
		public void addLast(E e) {
	        linkLast(e);
	    }	
		//----------------------------------
		private void linkFirst(E e) {
	        final Node<E> f = first;
	        final Node<E> newNode = new Node<>(null, e, f);//新建节点，以头节点为后继节点
	        first = newNode;
	        //如果链表为空，last节点也指向该节点
	        if (f == null)
	            last = newNode;
	        //否则，将头节点的前驱指针指向新节点，也就是指向前一个元素
	        else
	            f.prev = newNode;
	        size++;
	        modCount++;
	    }		

	    void linkLast(E e) {
	        final Node<E> l = last;
	        final Node<E> newNode = new Node<>(l, e, null);//新建节点
	        last = newNode;//更新last
	        if (l == null)
	            first = newNode;//为空则直接作为表头
	        else
	            l.next = newNode;//指向后继元素也就是指向下一个元素
	        size++;
	        modCount++;
	    }

```
 2.addAll(int index,Conllection c)

 
```
		public boolean addAll(int index, Collection<? extends E> c) {
	        checkPositionIndex(index);
	        
	        Object[] a = c.toArray();
	        int numNew = a.length;
	        if (numNew == 0)
	            return false;
	
	        //创建两个节点用以保存插入位置的前驱节点和后继节点
	        Node<E> pred, succ;
	        //如果插入位置为尾部，前驱节点为last，后继节点为null
	        if (index == size) {
	            succ = null;
	            pred = last;
	        }
	        //否则，调用node()方法得到后继节点，再得到前驱节点
	        else {
	            succ = node(index);
	            pred = succ.prev;
	        }
	
	        //遍历数据将数据插入
	        for (Object o : a) {
	            @SuppressWarnings("unchecked") E e = (E) o;
	            Node<E> newNode = new Node<>(pred, e, null);
	            if (pred == null)
	                first = newNode;
	            else
	                pred.next = newNode;
	            pred = newNode;
	        }
			//插入完数据，把后面的连接起来
	        //如果插入位置在尾部，重置last节点
	        if (succ == null) {
	            last = pred;
	        }
	        //否则，将插入的链表与先前链表连接起来
	        else {
	            pred.next = succ;
	            succ.prev = pred;
	        }
	
	        size += numNew;
	        modCount++;
	        return true;
	    }    

```
 addAll步骤：

  
  2. 检查index 
  4. toArray()将集合转为数组 
  6. 得到插入位置的前驱节点和后继节点 
  8. 遍历数组，将数据插入指定位置 
  10. 将后继节点后的数据接回来  3.获取数据方法

 
```
		//获取指定位置数据
		public E get(int index) {
	        checkElementIndex(index);
	        //调用Node(index)去找到index对应的node然后返回它的值
	        return node(index).item;
	    }

```
 获取头节点的方法、获取尾节点

 
```
		//获取头节点
		public E getFirst() {
	        final Node<E> f = first;
	        if (f == null)
	            throw new NoSuchElementException();
	        return f.item;
	    }
		public E element() {
	        return getFirst();
	    }
		public E peek() {
	        final Node<E> f = first;
	        return (f == null) ? null : f.item;
	    }
		public E peekFirst() {
	        final Node<E> f = first;
	        return (f == null) ? null : f.item;
	     }

		//获取尾节点
		public E getLast() {
	        final Node<E> l = last;
	        if (l == null)
	            throw new NoSuchElementException();
	        return l.item;
	    }
	 	public E peekLast() {
	        final Node<E> l = last;
	        return (l == null) ? null : l.item;
	    }

```
 **区别：**

  
  2. getFirst() 和element() 方法将会在链表为空时，抛出异常，而其它获取头节点方法返回 null。 
  4. getLast() 方法在链表为空时抛出异常，而peekLast() 方法返回 null。  4.根据对象得到索引  
 从头遍历找：

 
```
		public int indexOf(Object o) {
	        int index = 0;//****
	        if (o == null) {//为空则直接判断是否为null
	            for (Node<E> x = first; x != null; x = x.next) {
	                if (x.item == null)
	                    return index;
	                index++;
	            }
	        } else {
	            for (Node<E> x = first; x != null; x = x.next) {
	                if (o.equals(x.item))
	                    return index;
	                index++;
	            }
	        }
	        return -1;
	    }

```
 从尾遍历找：

 
```
		public int lastIndexOf(Object o) {
	        int index = size;//*****
	        if (o == null) {
	            for (Node<E> x = last; x != null; x = x.prev) {
	                index--;
	                if (x.item == null)
	                    return index;
	            }
	        } else {
	            for (Node<E> x = last; x != null; x = x.prev) {
	                index--;
	                if (o.equals(x.item))
	                    return index;
	            }
	        }
	        return -1;
	    }

```
 5.删除方法  
 删除头节点：

 
```
		public E pop() {
	        return removeFirst();
	    }
		public E remove() {
	        return removeFirst();
	    }
		public E removeFirst() {
	        final Node<E> f = first;
	        if (f == null)
	            throw new NoSuchElementException();
	        return unlinkFirst(f);
	    }

```
 删除尾节点：

 
```
		public E removeLast() {
	        final Node<E> l = last;
	        if (l == null)
	            throw new NoSuchElementException();
	        return unlinkLast(l);
	    }
		public E pollLast() {
	        final Node<E> l = last;
	        return (l == null) ? null : unlinkLast(l);
	    }

```
 **区别：** removeLast()在链表为空时将抛出NoSuchElementException，而pollLast()方法返回null。  
 删除指定元素：

 
```
		public boolean remove(Object o) {
	        if (o == null) {
	            for (Node<E> x = first; x != null; x = x.next) {
	            	//找到对应节点x
	                if (x.item == null) {
	                   //调用unlink删除
	                    unlink(x);
	                    return true;
	                }
	            }
	        } else {
	            for (Node<E> x = first; x != null; x = x.next) {
	                if (o.equals(x.item)) {
	                    unlink(x);
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	    
		//unlink
		E unlink(Node<E> x) {
	        // assert x != null;
	        final E element = x.item;
	        final Node<E> next = x.next;//得到后继节点
	        final Node<E> prev = x.prev;//得到前驱节点
	
	        //改变x前驱节点的后继指针为x后继节点，x前驱指针变为null
	        if (prev == null) {
	            first = next;//如果删除的节点是头节点,令头节点指向该节点的后继节点
	        } else {
	            prev.next = next;
	            x.prev = null;
	        }
	
	        //改变x后继节点的前驱指针为x前驱节点，x后继指针变为null
	        if (next == null) {
	            last = prev;//如果删除的节点是尾节点,令尾节点指向该节点的前驱节点
	        } else {
	            next.prev = prev;
	            x.next = null;
	        }
	
	        x.item = null;
	        size--;
	        modCount++;
	        return element;
	    }

```
 删除指定位置元素：

 
```
		public E remove(int index) {
	        //检查index范围
	        checkElementIndex(index);
	        //将节点删除
	        return unlink(node(index));
	    }

```
 6.检查是否包含

 
```
		public boolean contains(Object o) {
	        return indexOf(o) != -1;//索引是否存在
	    }

```
   
  