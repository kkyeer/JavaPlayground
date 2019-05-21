# 多线程中的线程关闭

## 方法1：轮询某状态字段

代码参见**concurrent.cancel.PollState**

### 重点
状态字段用volatile关键字修饰

### 缺点

1. 当方法体内是block方法时，有可能无法及时关闭，最坏的情况，当这个方法被永久block时（比如
放入一个没有消费者的已满队列，此时put方法被block一直到消费者消费一个数据）
2. 不通用

## 方法2：使用Thread的interrupt方法

代码参见**concurrent.cancel.Interruption**

### 重点与原理
1. 执行queue.put的过程中会探测interrupt状态
2. 在执行有可能被block或者长时间的代码前，判断当前是否interrupt

### 缺点
1. 不通用
2. 需要判断的地方较多

