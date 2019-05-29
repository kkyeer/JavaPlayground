# 线程池使用与优化

## 线程池大小

当无资源（数据库连接数，文件句柄数）等限制时，简单情况下使用Runtime.getRuntime().availableProcessors()+1

## 线程池自动关闭

线程池自动关闭有多种方法，比如关联任务结束与线程池关闭方法等，另外一种方法是，设置一个CoreSize=0的线程池，当线程池在一段时间内无任务执行时，自动关闭

## 线程池和线程池满后的策略

全部代码参考concurrent.executor.tune.RejectPolicies

### 1. 丢弃并抛出异常-ThreadPoolExecutor.AbortPolicy

### 2. 丢弃不抛出异常-ThreadPoolExecutor.DiscardPolicy

### 3. 退回到调用线程-ThreadPoolExecutor.CallerRunsPolicy

在测试案例中运行上述策略时，可以看到，中间有任务的运行线程为main，即调用线程

### 4. 丢弃最老的-ThreadPoolExecutor.DiscardOldestPolicy

对于对于FIFO队列，表示丢弃下一个可能执行的任务,对于Priviledged队列，表示丢弃优先级最高的，慎用！
