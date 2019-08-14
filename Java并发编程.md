---
title: Java并发编程
date: 2019-05-02 13:06:40
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/89423910](https://blog.csdn.net/MOKEXFDGH/article/details/89423910)   
    
  ### 文章目录


    * [线程的状态](#_4)
    * [实现多线程的三种方式](#_22)
    * [sleep()、yield() 和线程阻塞](#sleepyield__76)
    * [Executor 线程池框架](#Executor__87)
      * [Executor](#Executor_88)
      * [守护线程](#_122)
      * [线程的中断](#_139)
    * [互斥同步](#_158)
      * [synchronized](#synchronized_163)
      * [ReentrantLock](#ReentrantLock_181)
      * [synchronized 与 lock 的区别](#synchronized__lock__204)
      * [CAS、乐观锁、悲观锁](#CAS_217)
    * [线程间的协作](#_233)
    * [J.U.C](#JUC_256)
      * [AQS](#AQS_259)
      * [三个辅助类](#_267)
      * [其它组件](#_331)
    * [线程安全](#_384)
      * [线程安全的分类](#_387)
      * [实现线程安全](#_407)
      * [ThreadLocal](#ThreadLocal_426)
    * [锁优化](#_550)  
  
 参考：[地址](https://github.com/frank-lam/fullstack-tutorial/blob/master/notes/JavaArchitecture/03-Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B.md)

 
--------
 
## []()线程的状态

 ![1](https://img-blog.csdnimg.cn/20190420200904651.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 **五种基本状态**

  
  * **新建：** 线程创建后还未启动。 
  * **可运行：** 正在运行或正在等待 CPU 时间片（CPU分配给各个程序的时间）。 
  * **阻塞：** 多线程有同步操作时才会出现的状态，一个线程等待另一个线程（锁释放）。 
  * **等待：**  
     1.**限期等待**，一定时间后会被系统自动唤醒，可以使用 sleep、wait（带参）、join（带参）等方法进入。  
     2.**无限期等待**，只能被其它线程唤醒，可以使用 wait（无参）、join（无参）等方法进入。  
     **与阻塞区别：** 等待是主动进入，而阻塞是被动进入；等待在同步代码内，而阻塞在同步代码外。 
  * **死亡：** run 方法运行结束或出现未捕获的异常。  **线程的睡眠和挂起：**  
 **sleep()** 方法使线程进入限期等待，被描述为睡眠。  
 **wait()** 方法使线程进入限期或无限期等待，被描述为挂起。  
 **注：** 睡眠和挂起是线程的行为，阻塞和等待是线程的状态。

 
--------
 
## []()实现多线程的三种方式

 **继承 Thread 类**  
 重写父类 run 方法。

 
```
		class MyThread extends Thread{
			public void run(){...}
		}
		public class test{
			public static void main(String[] args){
				MyThread mt = new MyThread();
				mt.start();
			}
		}

```
 **实现 Runnable 接口**  
 实现接口 run 方法，需通过 Thread 调用 start。

 
```
		class MyRunnable implements Runnable {
		    public void run() {...}
		}
		public class test{
			public static void main(String[] args){
				MyRunnable mr = new MyRunnable();
				Thread t = new Thread(mr);
				t.start();
			}
		}

```
 **实现 Callable 接口**  
 可以有返回值，返回值由 FutureTask 封装。

 
```
		class MyCallable implements Callable<Integer> {
		    public Integer call() {
		        return 123;
		    }
		}
		public class test{
			public static void main(String[] args) throws ExecutionException, InterruptedException{
				MyCallable mc = new MyCallable();
				FutureTask<Integer> ft = new FutureTask<>(mc);
				Thread t = new Thread(ft);
				thread.start();
				System.out.println(ft.get());
			}
		}

```
 **三种方式的区别**

  
  * 实现 Runnable 接口可以避免单继承带来的局限性，可以增强程序的健壮性。 
  * 继承 Thread 类则无法继承其它类，与实现 Runnble 接口一样都要实现 run 方法。 
  * 实现 Callabe 接口则要实现 call() 方法，且会有返回值。  **注：** 由区别可知当我们不需要返回值时，一般会通过实现 Runnable 接口实现多线程。

 
--------
 
## []()sleep()、yield() 和线程阻塞

 **sleep()：** 休眠当前正在执行的线程，单位为毫秒。  
 **yield()：** 表面当前线程已经完成了生命周期中的最重要的部分，可以执行其它相同优先级的线程。

 **线程阻塞（暂停，区别于阻塞状态）的四种状态**

  
  2. 执行 sleep 方法，阻塞到指定毫秒时间，或被中断。 
  4. 执行 wait 方法，阻塞道接到 notify 通知，或经过指定毫秒时间，或被中断。 
  6. I/O 中的许多方法会阻塞，如 InputStream 的 read() 方法，阻塞到从流中读取到一个字节数据为止。 
  8. 等待获得 synchronized 的锁。  
--------
 
## []()Executor 线程池框架

 
### []()Executor

 **概述**  
 Executor 用于管理多个异步任务（互不干扰）的执行，无需我们显式地管理线程的生命周期。

 主要由三种 Executor：

  
  * CachedThreadPool：一个任务创建一个线程。 
  * FixedThreadPool：所有任务使用固定数量的线程。 
  * SingleThreadExecutor：相当于于大小为 1 的 FixedThreadPool。  创建方式：

 
```
		ExecutorService executorService = Executors.newCachedThreadPool();
	    for (int i = 0; i < 5; i++) {
	        executorService.execute(new MyRunnable());
	    }
	    executorService.shutdown();

```
 **与直接new Thread() 的区别：**  
 直接 new Thread()：

  
  2. 每次 new 都耗费性能。 
  4. 可以无限创建，导致线程之间相互竞争耗费系统资源。 
  6. 不利于扩展，如定时执行、线程中断等。  使用线程池：  
 4. 重用已经存在的线程，减少对象的创建，性能好。  
 5. 能有效控制最大并发线程数，提高系统资源利用率。  
 6. 提供许多扩展功能，如定时执行，并发数控制等。

 **execute与submit的区别**

  
  * 接受的参数不同 
  * submit有返回值，而execute没有 
  * submit方便Exception处理  
### []()守护线程

 线程分为两类：

  
  * 用户线程 
  * 守护线程  **概述**  
 用户线程运行再前台，而守护线程运行再后台。主要用于为其它前台线程的运行提供便利服务。  
 当所有非守护线程结束时，同时会杀死所有守护线程以终止程序，所以不要在守护线程中执行业务逻辑操作。

 **使用守护线程**  
 垃圾回收线程就是一个守护线程，我们也可以将一个新建的线程设置为守护线程：

 
```
		Thread thread = new Tread(new MyRunnable());
		thread.setDaemon(true);//必须再 start 方法之前设置
		thread.start();

```
 
### []()线程的中断

 **interrupt() ：**  
 线程处于**阻塞或等待状态**（I/O阻塞和synchronized 阻塞则不会）时调用其 interrupt() 方法就会抛出 InterruptedException ，从而中断该线程。

 **interrupted()：**  
 当线程不处于阻塞或等待状态时，调用 interrupt() 方法不会中断线程，但会设置线程的**中断标记**，此时调用 interrupted() 方法会返回 true。所以该方法可用于 run 方法中当中断的判断条件。

 **Executor 的中断**  
 两个方法：

  
  2. shutdown() 方法：等待所有线程执行完毕后再关闭。 
  4. shutdownNow() 方法：相当于调用每个线程的 interrupt() 方法。  中断 Executor 中的一个线程：

 
```
		Future<?> f = executorService.submit(()->{...});
		f.cancle(true);

```
 
--------
 
## []()互斥同步

 Java 提供了两种锁的机制来实现多个线程对共享资源的互斥访问：

  
  2. synchronized 
  4. ReentrantLock  
### []()synchronized

  
  2. 同步一个代码块：作用于同一个对象，若调用多个对象的同步代码块则不会同步。  
```
		synchronized(this){...}

```
  
  2. 同步一个方法：作用于同一个对象。  
```
		public synchronized void function(){...}

```
  
  2. 同步一个类：作用于整个类，即同一个类的不同对象都会进行同步。  
```
		synchronized(demo.class){...}

```
  
  2. 同步一个静态方法：作用于整个类。  
```
		public synchronized static void function(){...}

```
 
### []()ReentrantLock

 **概述**

  
  * ReentrantLock 是 J.U.C 包中的锁，相比 synchronized 多了一些高级功能。 
  * 重入锁是一种递归无阻塞的同步机制，ReentrantLock 和 synchronized 都是重入锁。  
```
		Lock lock = new ReentrantLock();
		lock.lock();
		...//同步代码
		lock.unlock();//一般放在finally中，防止死锁

```
 **高级功能**

  
  2. 等待中断：synchronized 不可中断。 
  4. 可实现公平锁：通过带布尔值的构造函数实现。 
  6. 锁可以绑定多个条件：Condition 对象。  **synchronized 和 ReentrantLock 比较**

  
  2. 锁实现：synchronized 是 JVM 实现，而 ReentrantLock 是 JDK 实现。 
  4. 性能：优化后的 synchronized 与 ReentrantLock 基本相同。 
  6. 功能：ReentrantLock 多了一些高级功能。  选择：除非需要使用到 ReentrantLock 的高级功能，否则优先使用 synchronized。

 
### []()synchronized 与 lock 的区别

 **用法**

  
  * synchronized 是隐式锁，在需要同步的对象中加入此控制，括号中国表示需要锁的对象。 
  * lock 是显示锁，需要显示指定起始终止位置，一般使用 ReentrantLock。  **性能**

  
  * Java1.5 之前 synchronized 是性能低效的，相比之下 Lock 对象性能更高一些。 
  * Java1.6 之后，synchronized 进行了锁优化，使得 synchronized 性能基本与 Lock 持平。  **机制**

  
  * synchronized 原始使用的是**悲观锁**机制，即线程获得的是独占锁 
  * Lock 采用的是**乐观锁**的方式。  
### []()CAS、乐观锁、悲观锁

 **CAS**  
 CAS：Compare And Swap 是现代 CPU 广泛支持的一种对内存中的共享数据进行原子读写操作的一种特殊指令，操作过程是乐观锁的思路。

 操作过程：

  
  2. 将内存中要被修改的数据与期望的值进行比较。 
  4. 当这两个值相等时，CPU 才会将内存中的数值替换为新的值，否则便不做操作。 
  6. 最后返回旧的数值。  主要应用：Java 并发包中的 atomic 包，如AtomicInteger。

 **乐观锁和悲观锁**

  
  * **悲观锁：** 默认会发生并发冲突，采用独占锁，避免一切会违反数据完整性的操作。 
  * **乐观锁：** 默认不会发生并发冲突，只在提交时检查是否违反数据完整性。  
--------
 
## []()线程间的协作

 **join()：**  
 在线程中调用另一个线程的 join() 放啊，会将当前线程挂起，直到目标线程结束才继续执行。

 **wait()、notify()、notifyAll()：**  
 调用 **wait()** 方法会使线程等待某个条件满足，等待时被挂起。  
 其它线程调用 **notify()** 方法会**随机**唤醒一个被挂起的线程，而调用 **notifyAll()** 方法会唤醒**所有**被挂起的线程。  
 **注：** 三个方法只能用在同步代码块或同步方法中，否则会抛出 IllegalMonitorStateExeception 异常。

 **await()、signal()、signalAll()**  
 三个方法是 通过 Lock 类获取的 Condition 对象中的方法。相比与 wait() 的三个方法可以指定等待的条件。

 
```
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		lock.lock();
		try{
			...//同步代码，await()、signal()、signalAll()
		}finally{
			lock.unlock();
		}

```
 
--------
 
## []()J.U.C

 J.U.C：主要指 java.util.concurrent 包，提高了 Java 并发性能。

 
### []()AQS

 AQS：AbstractQueuedSynchronizer,是 J.U.C 的核心，提供了一个基于 FIFO（先入先出）队列，是一个可以用来构建锁或其它相关的同步装置的基础**框架**，如 ReentrantLock 就是基于 AQS 构建的。

 **底层结构：**  
 ![1](https://img-blog.csdnimg.cn/2019042810405653.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

  
  * Sync queue：同步链表，属于双向链表是队列的一种实现，还保存了 head 和 tail 节点。 
  * Condition queue：单向链表，只当程序中用到 Condition 的时候才会构建出来。可以有多个。  
### []()三个辅助类

 **CountdownLatch**

  
  * 可以实现计数器的功能，实现一个线程等待其它多个线程执行完毕后才执行的功能。 
  * 内部维护了一个计数器 cnt，每次调用 countDown() 方法都会使计数器减 1，减到 0 则会将调用 await() 的线程唤醒。  **三个常用方法：**

 
```
		public void await() throws InterruptedException{}
		public boolean await(long timeout,TimeUnit unit) throws InterruptedException{}
		public void countDown(){}

```
 
--------
 **CyclicBarrier**

  
  * 可以控制多个线程相互等待，当多个线程都到达时，这些线程才会继续执行。 
  * 内部也是维护了一个计数器，但是 计数器是递增且可循环使用的，每次调用 await() 方法后，计数器会加 1，直到和我们设置的值相等。  
--------
 **Semaphore**

  
  * 是操作系统中的信号量，可以控制对互斥资源的访问的线程数。 
  * 通过 acquire() 获取一个许可，如果没有则等待，而通过 release() 释放一个许可。  **两个构造器**

 
```
		public Semaphore(int permits){
			sync = new NofairSync(permits);
			//permits表示许可数目，即同时可运行多少线程进行访问
		}
		public Semaphore(int permits,boolean fair){
			sync = (fair) ? new FairSync(permits) : new NonfairSync(permits);
			//是否公平，即等待时间越久越优先获取许可
		}

```
 **常用方法：**

  
  * 会阻塞：  
```
		//获取一个许可
		public void acquire() throws InterruptedException {  }
		//获取permits个许可
		public void acquire(int permits) throws InterruptedException { }
		//释放一个许可
		public void release() { }
		//释放permits个许可
		public void release(int permits) { }

```
  
  * 不会阻塞：  
```
		//尝试获取一个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
		public boolean tryAcquire() { };    
		//尝试获取一个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
		public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException { }; 
		//尝试获取permits个许可，若获取成功，则立即返回true，若获取失败，则立即返回false
		public boolean tryAcquire(int permits) { }; 
		//尝试获取permits个许可，若在指定的时间内获取成功，则立即返回true，否则则立即返回false
		public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException { }; 

```
 
--------
 **三个辅助类总结：**

  
  * CountDownLatch 和 CyclicBarrier 都能实现线程之间的等待而侧重的点不同。  
     CountDownLatch 侧重于其它线程执行完后才执行，而CyclicBarrier 侧重于一组线程互相等待至某一状态同时执行。  
     CountDownLatch 不能重用，而CyclicBarrier 是可以重用的。 
  * Semaphore 则不同于另外两个，主要用于控制对某组资源的访问权限。  
### []()其它组件

 **FutureTask**  
 前面有介绍可以用于封装 Callable 的返回值，或者说 FutureTask 可以用于封装一个需要执行的任务，并获取执行结果：

 
```
		FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>{
			public Integer call() throws Exception{
				return 520;
			}
		});

```
 **BlockingQueue**  
 该接口主要有以下阻塞队列的实现：

  
  * FIFO队列：LinkedBlockingQueue、ArrayBlockingQueue 
  * 优先级队列：PriorityBlockingQueue  **ForkJoin**  
 是一个并行框架，主要用于并行计算中，可以把大的计算任务拆分成多个小任务并行计算。  
 使用方法：

  
  2. 创建一个 ForkJoin 任务，一般是继承 **RecursiveAction**（无返回值） 或 **RcursiveTask**（有返回值）。 
  4. 而执行任务**ForkJoinPool**来执行，被分割的子任务会添加到当前工作线程的双端队列中，当一个工作线程没有任务时会从其它工作线程的队列**尾部窃取**一个任务。  
```
		public class Demo extends RecursiveTask<Integer>{
			private int l;
			private int r;
			public Demo(int l,int r){
				this.l = l;
				this.r = r;
			}
			@Override
			protect Integer compute(){
				int result = 0;
				if(r-l<10){
					result = r-l;
				}else{
					int mid = l + (r-l)/2;
					Demo ldemo = new Demo(l,mid);
					Demo rdemo = new Demo(mid+1,r);
					ldemo.fork();
					rdemo.fork();
					result = ldemo.join() + rdemo.join();
				}
				return result;
			}
		}
		...
		Demo test = new Demo(1,10000);
		ForkJoinPool fjp = new ForkJoinPool();
		Future result = forkJoinPool.submit(test);
		System.out.println(result.get());

```
 
--------
 
## []()线程安全

 线程安全：一个类在可以被多个线程安全调用时就是线程安全的。

 
### []()线程安全的分类

 其实是按照共享数据的安全程度划分的，可以由强到弱划分为五类：不可变、绝对线程安全、相对线程安全、线程兼容和线程对立。

 **不可变：** 不可变的对象一定是线程安全的。  
 主要的不可变类型：

  
  * final 修饰的基本数据类型 
  * String 类 
  * 枚举类型 
  * Long、double、BigInteger、BigDecimal等 
  * 集合类可以使用 Collections.unmodifiableXXX() 方法  **绝对线程安全：** 无论运行的环境如何，调用者都不需要任何额外的同步措施。

 **相对线程安全：** 保证了对这个对象的单独操作是线程安全的，但是对于一些特定顺序的连续调用，可能需要使用额外的同步措施来保证调用的正确性。  
 在 Java 中大部分的线程安全类属于这种类型，如 Vector、HashTable 或 Collections.synchronizedCollection() 包装的集合等。

 **线程兼容：** 对象本身并不是线程安全的，但是可以通过使用相应的同步手段来包装对象咋并发环境中可以安全地使用，Java 绝大部分类都是属于这一类，如 ArrayList、HashMap 等。

 **线程对立：** 无论调用端是否采取同步措施都无法达到线程安全。由于 Java 语言原本就具有多线程特性，所以线程对立类型的类是基本没有的。

 
### []()实现线程安全

 **阻塞同步：**  
 即互斥同步，属于一种**悲观的并发策略**，可以使用 synchronized 和 ReentrantLock？。[前面](https://mp.csdn.net/mdeditor/89423910#_154)

 **非阻塞同步：**  
 基于冲突检测的**乐观并发策略**，乐观锁的实现需要操作和冲突检测两个步骤具有原子性，因此只能由硬件来完成，常见的就是**比较并交换**（CAS）。  
 J.U.C 包里的 AtomicInteger 就是使用了 Unsafe 类的 CAS 操作来实现，而 CAS 会有一个问题：  
 ABA，即初次读为 A，修改为 B 后又修改回 A，那么 CAS 操作就会认为该变量没有被改变过。  
 J.U.C 包下提供了一个带有标记的原子引用类 AtomicStampedReference 来解决这个问题，它通过控制变量值的版本来保证 CAS 的正确性。

 **无同步措施的实现：**  
 即不使用共享的数据，使用的变量都由参数或其它非共享变量获得。  
 **1. 可重入代码**  
 即纯代码，可以带执行过程中的任何时刻中断，然后执行其它代码后回来继续执行，原来的程序不会出现任何错误。不依赖堆上的数据和公用的系统资源，用的都是参数传入的变量值。  
 **2. 栈封闭**  
 多个线程访问同一个方法的局部变量时，不会出现线程安全问题，局部变量存储在栈中属于线程私有的。  
 **3. ThreadLocal**  
 可以使用 ThreadLocal 来实现线程本地的存储功能。

 
### []()ThreadLocal

 **概述与用法**  
 作用：实现了本地存储的功能，即将共享数据的可见范围限制在同一个线程内，这样就无需同步措施。  
 例：

 
```
		public class Demo{
			ThreadLocal<String> tc = new ThreadLocal<String>();
			tc.set("Main");
			System.out.println("主线程："+tc.get());
			new Thread("Thread-0"){
				public void run(){
					tc.set(Thread-0);
					System.out.println("线程0："+tc.get());
				}
			}
		}

```
 输出结果：

 
> 主线程：Main  
>  线程0：Thread-0
> 
>  
 **底层原理**  
 每个 Thread 中都有一个 ThreadLocalMap，用以存储以 ThreadLocal 为 key 的键值对。  
 上面调用了 ThreadLocal 的 set() 方法：

 
```
		public void set(T value){
			THread t = Thread.currentThread();//获取当前线程
			ThreadLocalMap map = getMap(t);//根据当前线程获取其Map
			if(map!=null)
				map.set(this,value);
			else
				createMap(t,value);
		}

```
 get() 方法：

 
```
		public T get() {
	    	// 获取Thread对象t
		    Thread t = Thread.currentThread();
		    // 获取t中的map
		    ThreadLocalMap map = getMap(t);
		    if (map != null) {
		        ThreadLocalMap.Entry e = map.getEntry(this);
		        if (e != null) {
		            @SuppressWarnings("unchecked")
		            T result = (T)e.value;
		            return result;
		        }
		    }
		    // 如果t中的map为空
		    return setInitialValue();
		}

```
 setInitialValue() 方法：

 
```
		private T setInitialValue() {
		    T value = initialValue();//null
		    Thread t = Thread.currentThread();
		    ThreadLocalMap map = getMap(t);
		    if (map != null)
		        map.set(this, value);
		    else
		        createMap(t, value);
		    return value;
		}

```
 **ThreadLocalMap：** 是 ThreadLocal 的一个内部类。  
 常见属性：

 
```
		private static final int INITIAL_CAPACITY = 16;//初始容量
		private Entry[] table;//存放多个 ThreadLocal
		private int size = 0;//entry个数
		private int threshold;//扩容数值

```
 内部类：

 
```
		static class Entry extends WeakReference<ThreadLocal<?>> {
         Object value;
         Entry(ThreadLocal<?> k, Object v) {
             super(k);
             value = v;
         }
     }

```
 构造方法：

 
```
		ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {//参数：ThreadLocal实例，要保存的线程本地变量
	    table = new Entry[INITIAL_CAPACITY];
	    int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);//计算存放位置
	    table[i] = new Entry(firstKey, firstValue);
	    size = 1;
	    setThreshold(INITIAL_CAPACITY);
	}

```
 set()方法：

 
```
		private void set(ThreadLocal<?> key, Object value) {
		    Entry[] tab = table;
		    int len = tab.length;
		    int i = key.threadLocalHashCode & (len-1);
		
		    for (Entry e = tab[i];e != null;e = tab[i = nextIndex(i, len)]) {//冲突了则调用nextIndex方法，线性探测法（hashCode+1<len）
			        ThreadLocal<?> k = e.get();
			
			        if (k == key) {
			            e.value = value;
			            return;
			        }
			
			        if (k == null) {
			            replaceStaleEntry(key, value, i);
			            return;
			        }
			 }
		
		    tab[i] = new Entry(key, value);
		    int sz = ++size;
		    if (!cleanSomeSlots(i, sz) && sz >= threshold)//cleanSOmeSlots 清理无用的 entry
		        rehash();
		}

```
 
--------
 
## []()锁优化

 锁优化，即对 synchronized 的优化，分为以下几个方面：  
 **自旋锁**  
 让线程在请求一个共享数据的锁时执行忙循环（自旋）一段时间（由前一次的自旋次数和锁拥有者状态决定），如果在时间内获得锁就能避免进入阻塞状态而减少性能的开销。

 **锁消除**  
 对共享的数据进行逃逸分析，如果不会被其它线程访问到，就可以将它们的锁进行消除，因为在 Java 中有些类隐式地加了锁。

 **锁粗化**  
 频繁地加锁解锁操作会导致性能损耗，所以可以将多个同步代码块变为一整块，即把加锁的范围粗化到整个操作序列的外部。

 JDK1.6加入了轻量级锁和偏向锁，使得锁拥有了四个状态：**无锁状态、偏向锁状态、轻量级锁状态和重量级锁状态**。

 **偏向锁**  
 偏向于让第一个获取锁对象的线程，第一次获得锁对象，进入偏向状态，即这个线程在之后获取该锁就不再需要进行同步操作。当有另外一个线程尝试获取这个锁对象时，偏向状态结束回复为未锁定状态或轻量级锁状态。

 **轻量级锁**  
 它使用 CAS 操作来进行同步，如果有两条以上的线程竞争，则膨胀为重量级锁。等待轻量级锁的线程不会阻塞而是一直自旋等待锁。

 **JVM处理 synchronized 同步代码块的过程：**  
 在所有的锁都启用的情况下线程进入临界区时会先去获取偏向锁，如果已经存在偏向锁了，则会尝试获取轻量级锁，如果以上两种都失败，则启用自旋锁，如果自旋也没有获取到锁，则使用重量级锁，没有获取到锁的线程阻塞挂起，直到持有锁的线程执行完同步块唤醒他们。  
 ![1](https://img-blog.csdnimg.cn/20190502120807583.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

   
  