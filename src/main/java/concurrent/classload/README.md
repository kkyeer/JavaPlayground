[博客资料](https://www.cnblogs.com/aspirant/p/7200523.html)

# 探究多个线程同时进行类、实例相关操作时的安全性

## 理论依据
1. JVM必须确保一个类在初始化的过程中，如果是多线程需要同时初始化它，仅仅只能允许其中一个线程对其执行初始化操作，其余线程必须等待，只有在活动线程执行完对类的初始化操作之后，才会通知正在等待的其他线程。(所以可以利用静态内部类实现线程安全的单例模式)

## 相关测试要点
1. 静态代码块
2. 多个实例构造器与static变量
3. static方法与static变量

## 静态代码块
参考concurrent.classload.StaticBlock，因为静态代码块只会执行一次，且无参数，因此即使访问非线程安全的共享
HashMap变量，操作本身也是线程安全的

## 多个实例构造器与static变量

代码参考concurrent.classload.ConstructorModifyStatic

按道理推测，如果是线程安全的话，那么map里应该有1-12,12个数字，但是实际运行时，因为代码会被并发运行且没有加锁，
所以会有各种情况出现，比如抛出java.util.ConcurrentModificationException，或者map数量小于12

### 线程安全的版本
使用ConstructorModifyStatic.class作为同步锁，来同步构造器，不使用默认的锁(this)的原因是，对于不同的构造器，
this是不同的对象，无法起到同步作用

## static方法与共享的static变量

代码参考concurrent.classload.StaticMethodModifyStatic

情况与构造器一样，必须使用第三方锁

##
