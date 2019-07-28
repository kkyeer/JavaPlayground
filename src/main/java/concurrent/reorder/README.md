# Java指令重排序与volatile关键字

## 1. 重现代码重排序

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

表明实际运行中会有两个线程都执行完毕，然而x和y都是0的情况，这时就是发生了指令重排序，即代码运行的顺序，与源代码的顺序不一致，具体到测试代码，即可能实际运行顺序如下

- 0,0的情况（发生指令重排序）

|线程1|线程2|x| y|
|-|-|-|-|
|y=b;||0|0|
||x=a;|0|0|
|a=1;||0|0|
||b=1;|0|0|

此时最终打印x=0;y=0;

## 2. 什么是指令重排序

### 2.1 Java源代码到运行时指令

Java编译出来的class文件，仅能被Java虚拟机（JVM）识别，实际在运行时，会由实际运行的JVM编译成机器码运行，粗浅的理解为：Java.class文件 -> JVM运行时解析为机器码 (-> JIT优化过后的机器码) -> 操作系统的CPU指令，其中JVM解析为机器码、JIT优化成机器码，CPU执行CPU指令的过程中均有可能发生指令重排序

### 2.2 宿主机的内存模型与变量操作

所谓宿主机，即运行JVM的机器，可能是个人开发的电脑，线上的生产服务器，Docker容器等，操作系统、硬件的不同，内存模型和指令也不尽相同，鉴于目前多核CPU无论在开发环境和生产环境均为主流，一般认为宿主机的内存模型简化为主内存和多级CPU内部缓存再到寄存器，简化后的模型如下

>**寄存器  <->  CPU内部共享高速缓存(L1\L2\L3 Cache)  <->  主内存(RAM)**

高速缓存仅仅是用作寄存器和主内存之间缓存用，CPU通过各种技术保证寄存器读取时缓存内的值与主内存的对应值一致，因此进一步简化为

>**寄存器 <-> 内存(Cache和RAM)**

在此简化模型下，假设当前主内存中一个变量x初始值为0，一个简单的赋值操作```a=0;x=a+1```的执行顺序如下

1. 从内存读取a的值0到CPU Processor的寄存器，并赋予临时地址r1，可看作 r1 = a;
2. 寄存器内累加r1 = r1 + 1;
3. r1的值写回内存，x=1

假设MOV [v1, v2]代表v2变量复制到v1变量，S1表示Step1，r开头的变量表示寄存器变量，则上述步骤简写为

- S1: MOV [r1, a]
- S2: MOV [x, ++r1]

执行顺序为 **S1 -> S2** ，后面也按此约定说明

### 2.3 CPU指令重排序

#### 2.3.0 测试程序

将1.1中的测试程序改写为

```java
public Test{
    int a = 0,b = 0,x = 0,y = 0;

    void test1(){
        a = 1;
        x = b;
    }

    void test2()[
        b = 1;
        y = b;
    ]
}
```

则按照2.2的写法，将test1方法内部CPU指令简写为:

- S1: MOV[a, 1]
- S2: MOV[r1, b]
- S3: MOV[x, r1]

test2方法内部CPU指令简写为:

- S4: MOV[b, 1]
- S5: MOV[r2, a]
- S6: MOV[x, r2]

后面的程序均围绕此程序展开

CPU指令重排序的定义为：CPU允许在**某些条件**下进行**指令重排序**，仅需保证**重排序后单线程下的语义一致**，这句话比较绕口，其中有三个加粗后的关键字，具体解释如下：

#### 2.3.1 某些条件

我们把变量读到寄存器的操作称为Load，把变量从寄存器写出到内存的操作称之为Store，则下面的操作称之为Store-Load操作：

- MOV[r1, x]
- MOV[y, r1]

类似的还有Load-Load,Load-Store,Store-Store操作，对于这几种操作，Intel规定Store-Load操作，且Store中涉及到的外存变量与Load中涉及到的外存变量不同的情况下，可以发生指令重排序，当然对于不同的CPU、指令集，可重排序的指令不同，一般情况下认为大多数CPU均支持Store-Load重排序，具体的支持操作请参考最后的参考资料或自行查阅相关网站

#### 2.3.2 指令重排序

假设一个线程执行2.3.0中程序的test1()方法，由于S1为Store指令，S2为Load指令，且涉及的外存变量不同，根据2.3.1的说明，允许发生重排序，即允许指令执行顺序为**S2 -> S1 -> S3**,类似的test2()方法可被重排序为**S5 -> S4 -> S6**

#### 2.3.3 重排序后单线程下的语义一致

如果仅有一个线程顺序执行test1()和test2()方法，正常执行的结果为```a=1;b=1;x=0;y=1;```即使指令被重排序为**S2 -> S1 -> S3 -> S5 -> S4 -> S6**，最终执行结果仍旧为```a=1;b=1;x=0;y=1;```，与源码中直接推导或者说重排序前的执行结果是一致的，这就叫做重排序后单线程下的语义一致

#### 2.3.4 指令重排序与多线程程序

2.3.3中阐明了，指令重排序对于单线程程序没有影响，但是假如有两个线程分别运行test1()方法和test2()方法，假设发生指令重排序，由于多线程程序执行顺序的不确定性，可能的一种执行顺序为：

|线程1|线程2|r1| r2|x|y|a|b|
|-|-|-|-|-|-|-|-|
|S2( MOV[r1, b] )||0|-|0|0|0|0|
||S5( MOV[r2, a] )|0|0|0|0|0|0|
|S1( MOV[a, 1] )||0|0|0|0|1|0|
|S3( MOV[x, r1] )||0|0|0|0|1|0|
||S4( MOV[b, 1] )|0|0|0|0|1|1|
||S6( MOV[x, r2] )|0|0|0|0|1|1|

在这种情况下，最终x=0;y=0;这就是1.3中出现反直觉的结果的原因

## 3. 如何避免多线程程序中指令重排序造成的错误

### 3.1 Java内存模型（JMM）

JVM运行时，机器码会操作对应的CPU进行缓存，JMM规定了一系列Java代码与内存交互中的原则，如happens-before,serial-as-if原则等

### 2.4 JMM的happens-before原则

## 3.2 volatile关键字

### 3.2.1 volatile关键字对指令重排序的影响

### 3.2.2 验证volatile关键字对内存的影响

### 3.2.3 验证代码解析

### 3.2.4 volatile关键字适用范围

## 4 参考

- [JVM(十一)Java指令重排序](https://blog.csdn.net/yjp198713/article/details/78839698)
- [深入理解Java内存模型2](https://www.infoq.cn/article/java-memory-model-2/)
- [Java内存模型(JMM)规范(JSR133)](http://www.cs.umd.edu/~pugh/java/memoryModel/jsr133.pdf)
- [JSR133的FAQ](https://www.cs.umd.edu/users/pugh/java/memoryModel/jsr-133-faq.html)
- [Intel对指令重排序的说明](http://www.cs.cmu.edu/~410-f10/doc/Intel_Reordering_318147.pdf)
- [从多核硬件架构，看Java内存模型](https://www.jianshu.com/p/f5883ca0348f)

## 备注

1. 也有可能是多个处理器之间内存可见性问题

