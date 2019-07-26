# 伪共享

## 缘起

CPU缓存的读写最小单位是cache line，对于不同的CPU，cache line的大小不同，一般是64Byte。
CPU内部缓存并不是一个整块，而是被分成L1,L2,L3几个等级，数字越小距离CPU越近，速度越快，容量越小，
一般来说，L3缓存是CPU内多个Core公用的，L1\L2缓存是CPU Core内私有的。对于同一个Cache line，若Cpu Core1
修改了line内某个变量的值，后续Cpu Core2内对此Line内任意变量进行操作，都必须重新读取整个Cache line。
因此若同一个Cache Line内的两个变量X,Y分别被两个CPU Core进行修改的话，会引起缓存不断同步，影响性能

## 演示

要想演示伪共享导致的性能下降，需要构造某种数据结构，某个对象自己可以占一个Cache Line时，并发修改不会
触发伪共享，某个对象不能自己独占一个CacheLine时，并发修改会触发伪共享问题，考虑到64位系统下对象头
占用12Byte（默认开启压缩的情况下），一个long类型的数据占用8Byte，因此，如果一个对象包含7个long型数据，
则总计占用68字节以上，肯定不会与其他数据公用一个Cache Line，比如下列对象
```Java
public NoneShareCacheLine{
    private volatile long value = 0L;
    private long l1,l2,l3,l4,l5,l6;
}
```
一定不会跟其他线程公用cache line，而下列对象则有可能与其他对象公用cache line
```java
public PossiblyShareCacheLine{
    private volatile long value = 0L;
}
```

验证代码参考concurrent.falsesharing.FalseSharingUsingPadding

## 避免

1. 方法1：通过手动增加额外的变量来padding，保证单对象大小超过64Byte，
则一定不会与其他对象共用Cache line

2. 方法2：Java8中新增了一个注解：@sun.misc.Contended。加上这个注解的类会自动补齐缓存行，需要注意的是此注解默认是无效的，需要在jvm启动时设置-XX:-RestrictContended才会生效。

## 参考资料

- [Java中的伪共享及应对方案](https://yq.aliyun.com/articles/62865)
