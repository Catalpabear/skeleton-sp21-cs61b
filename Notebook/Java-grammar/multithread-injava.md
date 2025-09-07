从基本概念上来说, 一个程序就是一个进程, 一个进程之中可以并发多个线程, 线程之间存在优先级  
一个线程通常有五个状态:
- 新建   -> 初始化线程对象  
- 就绪   -> 对线程对象调用`start()` , 对象进入调度队列(存在优先级)中, 等待JVM调用  
- 运行   -> JVM调用, 开始运行  
- 阻塞   -> 程序调用相关方法进入该状态  
- 死亡   -> 运行结束或主动终止  

## Create thread in java

### implements Runnable
在类中声明Runnable接口后, 可以在类中定义Thread对象的实例, 对于这个线程要做的事情, 我们需要重写 `run()`方法, 在这个方法里写你这个线程里进行的操作代码, `run()` 方法不需要程序员调用, 当你启动线程时(调用`start()`) ,调度队列中轮到它时, JVM会自行调用`run()` 方法.  使用实例如下,来自[菜鸟](https://www.runoob.com/java/java-multithreading.html):
```java
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   RunnableDemo( String name) {
      threadName = name;
   }   
   public void run() {
      try {
	         //写你这个线程里要运行的代码
         }
      }catch (InterruptedException e) {
      // 捕获异常
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      //提示线程退出
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      if (t == null) {
	     //初始化线程变量
	     //构造函数Thread(Runnable threadOb,String threadName);
         t = new Thread (this, threadName);
         //将thread变量送入调度队列
         t.start ();
      }
   }
}
 
public class TestThread {
   public static void main(String args[]) {
      RunnableDemo R1 = new RunnableDemo( "Thread-1");
      R1.start();
      RunnableDemo R2 = new RunnableDemo( "Thread-2");
      R2.start();
   }   
}
```

### extends Thread
在类中声明继承 Thread 类时, 也可以在类中定义Thread对象的实例, 具体操作形式看起来和上述Runnable 使用是一样的.  不过， 由于继承了Thread后，类再不能继承别的类，所以一般采用实现
Runnable接口的方法来创建线程  

### Thread define
我们了解一下Thread类的相关方法声明:
- void start() <<  启动该线程
- void run()   << JVM识别的线程执行代码块
- void setName()
- void setPriority() JVM的优先级从1-10, 有不同的Thread类常量成员表示 
- void setDaemon()
- void join()   << 让当前线程强制阻塞, 等待调用此方法的对象线程运行结束, 参数表示等待最长时间, 不写表示无限等待
- void interrupt()
- boolean isAlive()  << 判断线程是否 run
- State getState() << 返回线程的状态, 一共六种
	- NEW
	- RUNNABLE
	- BLOCKED
	- WAITING
	- TIMED_WAITING
	- TERMIATED 
- static void yield()  << 让步, 使当前线程延后, 但是可能瞬间又会回来
- static void sleep()  << 使当前线程延后一定时间, 当前线程指代码块所属的线程, 不会释放锁(如果有锁)
- static boolean holdsLock()
- static Thread currentThread()  << 返回当前运行的线程
- static void dumpStack()
标 << 的是比较常用或简单的方法

### tips
调用Thread对象的start()方法才会开始线程, 我们可以使用任意实现Runnable的对象作为Thread的初始化参数, 这个对象需要实现JVM会调用的`run()` 方法, 我们启动这个线程对象JVM就会找时间运行这个`run()` 方法. 我们可以加入Interrupted[^1]Exception异常 try-catch语句  


### callable Thread
- 创建 Callable 接口的实现类，并实现 call() 方法，该 call() 方法将作为线程执行体，并且有返回值。
    
- 创建 Callable 实现类的实例，使用 FutureTask 类来包装 Callable 对象，该 FutureTask 对象封装了该 Callable 对象的 call() 方法的返回值。
    
- 使用 FutureTask 对象作为 Thread 对象的 target 创建并启动新线程。
    
- 调用 FutureTask 对象的 get() 方法来获得子线程执行结束后的返回值。


## 多线程的概念理解

多线程编程中, 还有如下概念需要理解, 我们才能更好的使用这一块(无关语言)  
- 线程同步  synchronization
- 守护线程 Daemon Thread
- 线程安全工具包 juc  
	- java.util.concurrent.atomic
	- java.util.concurrent.locks
	- 线程池 java.util.concurrent.Executor
- 线程协作问题 生产者-消费者
	- 线程死锁 dead lock
	- 竞态条件 race condition
- 线程控制 interrupt wait notify

### 线程同步锁
我们使用 `synchronized(lock)` 关键字来包围一个代码块, 当某个非主线程执行到这一块时, 会获取括号里的参数"lock", 并开始执行代码块里面的语句, 若未执行完, 且有另外一个线程(?[^2])访问到了这一块代码, 无法获取锁, 则强行阻塞, 等待锁的释放.  
拿到锁的线程在执行完之后会释放锁, sleep() 和yield() 方法 均无法释放锁[^3]  
synchronized 关键字还可以修饰方法, 无锁线程调用该方法时, 会被强行阻塞   

### 守护线程
守护线程

### 协作问题

### 线程控制



[^1]: 意为 "中断"  
[^2]: 这个知识就是为了防止多个线程一起修改某个共享的值, 从而导致数据混乱  
[^3]: 使用Object类里的方法wait() 可以直接释放锁又不会终止线程  