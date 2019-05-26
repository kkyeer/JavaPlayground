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

### 重点代码与原理

```java
    while (!Thread.currentThread().isInterrupted()) {
        testQueue.put("hh");
    }
```
1. testQueue.put()方法执行的过程中，假如探测到宿主线程被interrupt,会抛出interrupt异常，
避免手动维护状态字段的阻塞问题,可以通过查看测试代码的打印来证明


## 方法4：通过ExecutorService的shutdown方法

参考concurrent.cancel.shutdown.PrintBlockedOnExitLogWriter

Executor方法的shutdown方法会主动关闭所有线程，通过与线程的方法耦合（即在任务执行完毕
后调用shutdown方法来关闭相关线程

## 方法5：毒药丸

参考 concurrent.cancel.poisonpill，用于生产者-消费者模型
### 优点
可以将队列中阻塞的部分处理完成
### 缺点
生产者-消费者数量必须是确定值，假如生产者僵死或者异常，可能出现内存泄漏


## 监控关闭的任务

参考concurrent.cancel.trackservice
### 重点
#### 代码
```java
    @Override
    public void execute(Runnable command) throws RejectedExecutionException {
        executorService.execute(
                ()->{
                        try {
                            command.run();
                        }finally {
                            if (isShutdown()&& Thread.currentThread().isInterrupted()) {
                                cancelledWorkSet.add(command);
                            }
                        }
                }
        );
    }
    @Override
        public List<Runnable> shutdownNow() {
            executorService.shutdownNow();
            // 阻塞等到内部的Executor结束再返回
            try {
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ArrayList<>(cancelledWorkSet);
        }
```
#### 说明
1. 包裹的任务执行完成后，根据isShutdown()，和线程的interrupted状态共同确认线程是否是在关闭过程中终结
2. 调用shutdownNow方法时，使用awaitTermination来阻塞到内部service完成，否则返回的List不全





## 使用RunnableFuture的cancel方法

具体见concurrent.cancel.runnableFuture.TestCase



