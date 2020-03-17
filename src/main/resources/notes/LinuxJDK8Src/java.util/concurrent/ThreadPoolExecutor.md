# java.util.concurrent.ThreadPoolExecutor源码解析

[toc]

ThreadPoolExecutor是JDK提供的原生线程池实现，提供线程缓存，动态增减容量，线程生命周期钩子等功能，线程池通过缓存复用线程实例，来降低大量异步操作的开销

## 1. ThreadPoolExecutor的生命周期

![未命名文件.png](https://cdn.jsdelivr.net/gh/kkyeer/picbed/未命名文件.png)

ThreadPool

## 2. 核心参数与成员变量

### 2.1 核心参数

- corePoolSize:核心线程数，一般来说线程池内部的线程数会一直增长直到达到此数量，默认情况达到此数量后就不再减少，但可以通过allowCoreThreadTimeOut(true)来让核心线程也在空闲一段时间后销毁
- maximumPoolSize:最大线程数，一般情况下线程池仅需要核心线程来保证运行，但是当任务队列满的时候，会临时扩充线程数量，最大到maximumPoolSize来应付任务的激增，但临时扩充的这部分线程会在空闲指定时间后被销毁，将此值设置为与corePoolSize一致则表示不会临时扩充线程，最大可将此值设为```Integer.MAX_VALUE```
- keepAliveTime:非核心线程空闲此时间后被销毁，设为```Long.MAX_VALUE```则表示空闲线程不回收

### 2.2 核心成员变量

- ThreadFactory:负责创建线程，如果不传入则默认的ThreadFactory创建的线程为同一个ThreadGroup，Priority为Norm且非守护线程
- BlockingQueue:一个BlockingQueue实例，负责存储未执行的任务，根据不同类型的Queue，有三种通用策略：
  - SynchronizeQueue:内部不存储任务，直接交给线程池执行，一般需要将maximumPoolSize设为```Integer.MAX_VALUE```
  - Unbounded Queue:内部存储任务上限，常用LinkedBlockingQueue，只使用Core Thread来执行任务，几乎不Block，但是有资源耗尽的危险，而且线程数增长到一定程度，线程切换的开销会大于线程执行的开销，所以慎用，如果确实有此需求，可以考虑使用协程来代替线程，但是后者目前暂时没有稳定的开源实现
  - Bounded Queue:一般策略，常用ArrayBlockingQueue,内部有上限存储任务，非Full的状态下使用CoreThread执行任务，Full以后会临时扩充线程到maximumPoolSize
- RejectedExecutionHandler:调用submit被拒绝的处理策略，在Thread数达到Maximum且Queue也是Full的状态（调用Queue的offer方法校验），或者线程池正在shutdown过程中，提交任务会被拒绝，此时需要调用此策略，内置的4种策略如下：
  - ```AbortPolicy```:默认策略，抛出RejectedExecutionException异常
  - ```CallerRunsPolicy```:由调用线程执行任务
  - ```DiscardPolicy```:丢弃被拒绝的任务
  - ```DiscardOldestPolicy```:queue的第一个任务被丢弃，然后重试执行

### 2.3 生命周期钩子

#### 2.3.1 方法执行钩子

ThreadPoolExecutor内置了protected修饰的任务执行方法```beforeExecute(Thread, Runnable)```和```afterExecute(Runnable, Throwable)```，由于protected修饰，因此使用必须继承ThreadPoolExecutor类来自定义子类

#### 2.3.2 线程池生命周期钩子

ThreadPoolExecutor提供了```terminated```方法来允许子类覆盖线程池完全关闭后的逻辑，比如资源清理

