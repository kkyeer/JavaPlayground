# Thread的实例方法，join()

## 定义

```java
public final void join() throws InterruptedException {
        join(0);
    }
```

## 作用
当在a线程调用b线程的b.join()方法后，a线程等到b线程执行以后，再执行下面的指令

## 测试用例执行结果
```jshelllanguage
alskdjf
main thread executing
```
