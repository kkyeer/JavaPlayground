# 代码重排序与volatile关键字

## 代码重排序

看下面一段代码
```java
class Test{
    int a;
    void test(){
       while(a<10000){
           
       }
    }
}
```
    // volatile 关键字，保证线程间的原子性，即假如某一个线程修改了共享变量，则另外的线程一定可见
    // 反之，若是没有增加volatile关键字，则可能发生代码重排序
