---
title: ArrayList源码分析
date: 2019-03-15 15:13:42
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/88575150](https://blog.csdn.net/MOKEXFDGH/article/details/88575150)   
    
  ### 文章目录


    * [ArrayList 简介](#ArrayList__3)
    * [源码解析](#_13)
      * [继承实现](#_14)
      * [属性](#_23)
      * [构造方法](#_41)
      * [扩容机制](#_82)
      * [常用方法](#_139)
      * [其它方法](#_276)
      * [迭代器](#_341)
    * [arraycopy和copyOf区别](#arraycopycopyOf_369)  


 
--------
 
## []()ArrayList 简介

  
  * ArrayList 的底层是数组队列（动态数组），其容量能动态增长。 
  * 继承了 AbstractList，实现了List，具备了相应的增删改查等操作。 
  * 实现了 RandomAcces 接口，使得该集合支持随机访问。 
  * 实现 Cloneable 接口，覆盖了 clone()，能被克隆。 
  * 实现了 Serializable 接口，支持序列化。 
  * ArrayList 不是线程安全的，多线程可以使用 Vector。  
--------
 
## []()源码解析

 
### []()继承实现

 
```
		public class ArrayList<E> extends AbstractList<E>
        		implements List<E>, RandomAccess, Cloneable, java.io.Serializable

```
 由简介可知，继承 AbstractList，实现 RandomAccess, Cloneable, java.io.Serializable。

 
--------
 
### []()属性

 
```
		//指定serialVersionUID用于指定序列化对象
		private static final long serialVersionUID = 8683452581122892189L;
		//默认初始容量大小
		private static final int DEFAULT_CAPACITY = 10;
		//空数组，用于空实例（给定初始容量0）
		private static final Object[] EMPTY_ELEMENTDATA = {};
		//
		private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
		//保存数据的数组
		transient Object[] elementData;
		//包含元素的个数
		private int size;
		//最大的数组大小，用于扩容判断
		private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

```
 
--------
 
### []()构造方法

 默认构造方法：

 
```
		//默认构造方法
		//DEFAULTCAPACITY_EMPTY_ELEMENTDATA为空，只有添加第一个元素时才初始化为10
		public ArrayList() {
	        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	    }

```
 指定容量的构造方法：

 
```
		public ArrayList(int initialCapacity) {
	        if (initialCapacity > 0) {
	            //已经给的容量创建数组
	            this.elementData = new Object[initialCapacity];
	        } else if (initialCapacity == 0) {
	            //创建空数组
	            this.elementData = EMPTY_ELEMENTDATA;
	        } else {
	            throw new IllegalArgumentException("Illegal Capacity: "+
	                                               initialCapacity);
	        }
	    }

```
 包含指定集合的构造方法：

 
```
		public ArrayList(Collection<? extends E> c) {
	        //将集合存到数组中
	        elementData = c.toArray();
	        //如果其元素个数不为0
	        if ((size = elementData.length) != 0) {
	            //c.toArray()返回的不是Object类型，则将变为Object[]类型并重新存到数组中
	            if (elementData.getClass() != Object[].class)
	                elementData = Arrays.copyOf(elementData, size, Object[].class);
	        } else {
	            // 元素个数为0则用空数组代替
	            this.elementData = EMPTY_ELEMENTDATA;
	        }
	    }

```
 
--------
 
### []()扩容机制

 当插入的数据量已知时，我们可以自行调用ensureCapacity：

 
```
		public void ensureCapacity(int minCapacity) {//minCapacity是所需的最小容量
	        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
	            ? 0: DEFAULT_CAPACITY;
	        if (minCapacity > minExpand) {
	            ensureExplicitCapacity(minCapacity);
	        }
	    }

```
 而在我们添加元素获取所需最小容量时，则是使用：

 
```
		//首先得到最小扩容量（minCapactiy在方法调用处如add，会自行计算出来）
	    private void ensureCapacityInternal(int minCapacity) {
	        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {//判断是否为空
	              // 获取默认的容量10和传入参数的较大值
	            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
	        }
	        ensureExplicitCapacity(minCapacity);
	    }

```
 上面两个方法都会调用ensureExplicitCapacity来判断是否进行扩容：

 
```
		private void ensureExplicitCapacity(int minCapacity) {
	        modCount++;//记录修改次数，多用于非线程安全的集合，便于检验是否修改出错
	        if (minCapacity - elementData.length > 0)//如果大于0则说明需要进行扩容
	            //调用grow方法进行扩容，调用此方法代表已经开始扩容了
	            grow(minCapacity);
	    }

```
 扩容的核心方法 grow(minCapacity)：

 
```
		private void grow(int minCapacity) {
	        // oldCapacity为旧容量，newCapacity为新容量
	        int oldCapacity = elementData.length;
	        //将oldCapacity 右移一位，其效果相当于oldCapacity /2，整句运算式的结果就是将新容量更新为旧容量的1.5倍，
	        int newCapacity = oldCapacity + (oldCapacity >> 1);
	        //然后检查新容量是否大于最小需要容量，若还是小于最小需要容量，那么就把最小需要容量当作数组的新容量，
	        if (newCapacity - minCapacity < 0)
	            newCapacity = minCapacity;
	        //再检查新容量是否超出了ArrayList所定义的最大容量，
	        //若超出了，则调用hugeCapacity()来比较minCapacity和 MAX_ARRAY_SIZE，
	        if (newCapacity - MAX_ARRAY_SIZE > 0)
	            newCapacity = hugeCapacity(minCapacity);
	        //创建新数组长度为newCapacity，并将原来的数据拷贝回去
	        elementData = Arrays.copyOf(elementData, newCapacity);
	    }
	     //比较minCapacity和 MAX_ARRAY_SIZE
	    private static int hugeCapacity(int minCapacity) {
	        if (minCapacity < 0) 
	            throw new OutOfMemoryError();
	        //如果minCapacity大于MAX_ARRAY_SIZE，则新容量则为Interger.MAX_VALUE，否则，新容量大小则为MAX_ARRAY_SIZE。
	        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	    }

```
 
--------
 
### []()常用方法

 
```
		//获取元素个数
		public int size() {
	        return size;
	    }
		
		//判断有无元素
		public boolean isEmpty() {
	        return size == 0;
	    }
		
		//判断是否包含
		public boolean contains(Object o) { 
	        return indexOf(o) >= 0;
	    }
	    
	    //返回此列表中指定元素的首次出现的索引，如果此列表不包含此元素，则为-1
	    public int indexOf(Object o) {
	        if (o == null) {
	            for (int i = 0; i < size; i++)
	                if (elementData[i]==null)
	                    return i;
	        } else {
	            for (int i = 0; i < size; i++)
	                //equals()方法比较
	                if (o.equals(elementData[i]))
	                    return i;
	        }
	        return -1;
	    }
	    
	    //返回此列表中指定元素的最后一次出现的索引，如果此列表不包含元素，则返回-1。
	    public int lastIndexOf(Object o) {
	        if (o == null) {
	            for (int i = size-1; i >= 0; i--)
	                if (elementData[i]==null)
	                    return i;
	        } else {
	            for (int i = size-1; i >= 0; i--)
	                if (o.equals(elementData[i]))
	                    return i;
	        }
	        return -1;
	    }
		//获取指定位置元素
		public E get(int index) {
	        rangeCheck(index);//对index进行界限检查
	        return elementData(index);
	    }
		
		//指定位置替换为指定元素，并返回旧元素
		public E set(int index, E element) {
	        rangeCheck(index);
	        E oldValue = elementData(index);
	        elementData[index] = element;
	        //返回原来在这个位置的元素
	        return oldValue;
	    }

		//添加指定元素到末尾
		public boolean add(E e) {
			//判断是否需要扩容
	        ensureCapacityInternal(size + 1);  
	        elementData[size++] = e;
	        return true;
	    }

		//指定位置添加
		public void add(int index, E element) {
	        rangeCheckForAdd(index);
	        ensureCapacityInternal(size + 1);
	        System.arraycopy(elementData, index,elementData, index + 1,size - index);
	        //区别copyOf()
	        elementData[index] = element;
	        size++;
	    }
		//指定位置删除
		public E remove(int index) {
	        rangeCheck(index);
	
	        modCount++;
	        E oldValue = elementData(index);
	
	        int numMoved = size - index - 1;
	        if (numMoved > 0)
	            System.arraycopy(elementData, index+1, elementData, index, numMoved);
	        elementData[--size] = null; //从列表中删除的元素 
	        return oldValue;
	    }

		//删除指定元素的第一个出现
		public boolean remove(Object o) {
	        if (o == null) {
	            for (int index = 0; index < size; index++)
	                if (elementData[index] == null) {
	                    fastRemove(index);
	                    return true;
	                }
	        } else {
	            for (int index = 0; index < size; index++)
	                if (o.equals(elementData[index])) {
	                    fastRemove(index);
	                    return true;
	                }
	        }
	        return false;
	    }

		//删除所有元素
		public void clear() {
	        modCount++;
	        // 把数组中所有的元素的值设为null
	        for (int i = 0; i < size; i++)
	            elementData[i] = null;
	        size = 0;
	    }

		//从此列表中删除指定集合中包含的所有元素。 
	    public boolean removeAll(Collection<?> c) {
	        Objects.requireNonNull(c);
	        //如果此列表被修改则返回true
	        return batchRemove(c, false);
	    }
	
	    //仅保留此列表中包含在指定集合中的元素。
	    public boolean retainAll(Collection<?> c) {
	        Objects.requireNonNull(c);
	        return batchRemove(c, true);
	    }
		
		//从前往后将集合转为数组
		 public Object[] toArray() {
	        return Arrays.copyOf(elementData, size);
	    }

```
 
--------
 
### []()其它方法

 
```
		//修改容量为元素个数一样
		public void trimToSize() {
	        modCount++;
	        if (size < elementData.length) {
	            elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
	        }
	    }

		//将指定集合中所有元素添加到末尾
		public boolean addAll(Collection<? extends E> c) {
	        Object[] a = c.toArray();
	        int numNew = a.length;
	        ensureCapacityInternal(size + numNew);  // Increments modCount
	        System.arraycopy(a, 0, elementData, size, numNew);
	        size += numNew;
	        return numNew != 0;
	    }

		//将指定集合中所有元素添加到指定位置
		public boolean addAll(int index, Collection<? extends E> c) {
	        rangeCheckForAdd(index);
	
	        Object[] a = c.toArray();
	        int numNew = a.length;
	        ensureCapacityInternal(size + numNew);  // Increments modCount
	
	        int numMoved = size - index;
	        if (numMoved > 0)
	            System.arraycopy(elementData, index, elementData, index + numNew,
	                             numMoved);
	
	        System.arraycopy(a, 0, elementData, index, numNew);
	        size += numNew;
	        return numNew != 0;
	    }

		//删除某段的所有元素。fromIndex-toIndex
		 protected void removeRange(int fromIndex, int toIndex) {
	        modCount++;
	        int numMoved = size - toIndex;
	        System.arraycopy(elementData, toIndex, elementData, fromIndex,
	                         numMoved);
	
	        // clear to let GC do its work
	        int newSize = size - (toIndex-fromIndex);
	        for (int i = newSize; i < size; i++) {
	            elementData[i] = null;
	        }
	        size = newSize;
	    }

		//检查给定索引是否在范围内（get、set）
		private void rangeCheck(int index) {
	        if (index >= size)
	            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	    }
		//检查给定索引是否在范围内（add、addAll）
		private void rangeCheckForAdd(int index) {
	        if (index > size || index < 0)
	            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	    }	    

```
 
--------
 
### []()迭代器

 内部类：

 
```
		private class Itr implements Iterator<E>  
	    private class ListItr extends Itr implements ListIterator<E>  
	    private class SubList extends AbstractList<E> implements RandomAccess  
	    static final class ArrayListSpliterator<E> implements Spliterator<E>

```
 相关方法：

 
```
		//以正确的顺序返回该列表元素的迭代器
		public Iterator<E> iterator() {
	        return new Itr();
	    }

		//返回按照适当的顺序的迭代器
		public ListIterator<E> listIterator() {
	        return new ListItr(0);
	    }
		
		//返回从指定位置开始的迭代器
		public ListIterator<E> listIterator(int index) {
	        if (index < 0 || index > size)
	            throw new IndexOutOfBoundsException("Index: "+index);
	        return new ListItr(index);
	    }

```
 
--------
 
## []()arraycopy和copyOf区别

 在指定位置添加元素时：

 
```
		public void add(int index, E element) {
	        rangeCheckForAdd(index);
	        ensureCapacityInternal(size + 1);
	        //arraycopy()方法实现数组自己复制自己
	        //elementData:源数组;index:源数组中的起始位置;
	        //elementData：目标数组；index + 1：目标数组中的起始位置； size - index：要复制的数组元素的数量；
	        System.arraycopy(elementData, index, elementData, index + 1, size - index);
	        elementData[index] = element;
	        size++;
	    }

```
 使用arraycopy实现了数组自己复制自己，并index后面的元素都后移一位。

 **联系和区别：**  
 联系：copyyOf()方法内部其实也是调用的 arraycopy() 方法  
 区别：

  
  * arraycopy()方法需要源数组和目标数组，且能根据位置进行复制拷贝。 
  * copyOf()方法则只需要源数组，将其中元素复制到新的数组中并返回。    
  