# Java指令重排序与volatile关键字

## 1 重现代码重排序

### 1.1 测试代码

完整代码参见concurrent.reorder.Reveal
关键代码如下：

```java
Thread thread1 = new Thread(
        () -> {
            a = 1;
            y = b;
        }
);
Thread thread2 = new Thread(
        () -> {
            b = 1;
            x = a;
        }
);
```

### 1.2 理论推断

因为thread1和thread2都join到当前线程，则代码执行到这里以后，两个线程都执行完毕，因为多线程的原因，代码执行顺序不同，理论上xy的值可能为(1,0)(0,1)或者(1,1)，分别对应如下的执行顺序（从上到下）

- 1,0的情况

|线程1|线程2|x| y|
|-|-|-|-|
|a=1;||0|0|
|y=b;||0|0|
||b=1;|0|0|
||x=a;|1|0|

- 0,1的情况

|线程1|线程2|x| y|
|-|-|-|-|
||b=1;|0|0|
||x=a;|0|0|
|a=1;||0|0|
|y=b;||0|1|

- 1,1的情况

|线程1|线程2|x| y|
|-|-|-|-|
||b=1;|0|0|
|a=1;||0|0|
||x=a;|1|0|
|y=b;||1|1|

### 1.3 指令重排序导致的特殊情况

实际运行中，运行上述的代码足够长的时间后，会有某个线程进入错误分支，打印如下错误并关闭线程池

```shell
Wrong,x = 0 and y = 0
Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@d716361 rejected from java.util.concurrent.ThreadPoolExecutor@3764951d[Shutting down, pool size = 12, active threads = 12, queued tasks = 23280, completed tasks = 2713]
	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
	at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:112)
	at concurrent.reorder.Reveal.main(Reveal.java:59)
Wrong,x = 0 and y = 0
```

表明实际运行中会有两个线程都执行完毕，然而x和y都是0的情况，这时就是**可能**（见备注1）发生了指令重排序，即代码运行的顺序，与源代码的顺序不一致，
具体到测试代码，即可能实际运行顺序如下

- 0,0的情况（发生指令重排序）

|线程1|线程2|x| y|
|-|-|-|-|
|y=b;||0|0|
||x=a;|0|0|
|a=1;||0|0|
||b=1;|0|0|

## 2 什么是指令重排序

### 2.1 Java源代码到运行时指令

Java编译出来的class文件，仅能被Java虚拟机（JVM）识别，实际在运行时，会由实际运行的JVM编译成机器码运行，粗浅的理解为：Java源代码->.class文件->JVM运行时解析为机器码->操作系统的CPU指令->具体的机器码

### 2.2 宿主机的内存模型与变量操作

所谓宿主机，即运行JVM的机器，可能是个人开发的电脑，线上的生产服务器，Docker容器等，操作系统、硬件的不同，内存模型和指令也不尽相同，鉴于目前多核CPU无论在开发环境和生产环境均为主流，一般认为宿主机的内存模型简化为主内存和多个CPU内部缓存，内存中的数据先读入CPU的内部缓存，CPU在内部缓存进行操作后，统一刷写回内存，多个CPU之间的缓存互相不可见，CPU1对某数据的操作只有在刷写回内存，且CPU2重新从内存读取到缓存后，此操作才对CPU2可见

考虑如下简单代码

```Java
public class Test{
    int a = 0，b = 0;

    void test(){
        a = 1;
        b = a;
    }
}
```

调用test方法时，实际上CPU缓存内，CPU缓存与内存间有多个操作，简化的操作流如下

1. 读取内存中a，b的值到CPU缓存
2. 将缓存中a的值改写为1
3. 将缓存中b的值改写为1
4. 将缓存刷写回内存，内存中的a和b变为1

对于现代化的CPU来说，这些步骤的顺序并不一定与上面所写的顺序一致，CPU本身保证单线程下的实际一致性，这句话的意思是，即使执行顺序为

### 2.3 Java内存模型（JMM）与宿主机内存模型的关系

JVM运行时，机器码会操作对应的CPU进行缓存，JMM规定了一系列Java代码与内存交互中的原则，如happens-before,serial-as-if原则等

### 2.4 JMM的happens-before原则

### 2.5 指令重排序与内存可见性的关系

### 2.6 指令重排序与多线程程序的关系

## 3 volatile关键字对指令重排序的影响

### 3.1 验证volatile关键字对内存的影响

### 3.2 验证代码解析

### 3.3 volatile关键字适用范围

## 参考

- [JVM(十一)Java指令重排序](https://blog.csdn.net/yjp198713/article/details/78839698)
- [深入理解Java内存模型2](https://www.infoq.cn/article/java-memory-model-2/)
- [Java内存模型(JMM)规范(JSR133)](http://www.cs.umd.edu/~pugh/java/memoryModel/jsr133.pdf)
- [JSR133的FAQ](https://www.cs.umd.edu/users/pugh/java/memoryModel/jsr-133-faq.html)
- [Intel对重排序的说明](http://www.cs.cmu.edu/~410-f10/doc/Intel_Reordering_318147.pdf)

## 备注

1. 也有可能是多个处理器之间内存可见性问题

