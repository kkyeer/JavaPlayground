# Thread的UncaughtExceptionHandler

##　UncaughtExceptionHandler类
```java
@FunctionalInterface
    public interface UncaughtExceptionHandler {
        /**
         * Method invoked when the given thread terminates due to the
         *exceptionhandler
         *exceptionhandler
         * Java Virtual Machine.
         * @param t the thread
         * @param e exceptionhandler
         */
        void uncaughtException(Thread t, Throwable e);
    }
```
**这个方法里丢出的异常会被JVM无视**

## 准备Runnable和UncaughtException类
```java
    /**
     * 测试用线程，抛出运行时异常以模拟运行时抛出异常的情况
     */
    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("开始运行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }
    
    /**
     * 测试用线程，抛出运行时异常以模拟处理异常过程中，再次发生异常的过程
     */    
    static class MyHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName() + " 碰到问题了:");
            e.printStackTrace();
            throw new RuntimeException("处理异常过程中出错");
        }
    }
```
说明：在MyHandler里

## 设置某个线程的handler
参考concurrent.cancel.exception.handler.TestCase的setIndividualThread方法
```java
    /**
     * 单个线程
     */
    static void setIndividualThread(Thread.UncaughtExceptionHandler handler){
        Thread thread = new Thread(new MyThread());
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
    }
```
结果
```jshelllanguage
开始运行
Thread-0 碰到问题了:
java.lang.RuntimeException
	at concurrent.cancel.exception.handler.TestCase.lambda$sconcurrent.cancel.exceptionhandlerse.java:26)
	at java.lang.Thread.run(Thread.java:748)

Exception: java.lang.RuntimeException thrown from the UncaughtExceptionHandler in thread "Thread-0"
```

## 设置默认UncaughtExceptionHandler
```java
    /**
     * 设置默认handler
     * @param handler 默认handler
     */
    static void setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
        new Thread(new MyThread()).start();
    }
```

## Executor中，使用ThreadFactory

```java

    /**
     * 设置Executor里的默认异常处理器
     * @param handler 处理器
     */
    private static void inExecutor(Thread.UncaughtExceptionHandler handler) {
        ThreadFactory threadFactory = r -> {
            Thread.setDefaultUncaughtExceptionHandler(handler);
            return new Thread(r);
        };
        Executor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), threadFactory);
        for (int i = 0; i < 4; i++) {
             executor.execute(new MyThread());
        }
    }
```
```jshelllanguage
开始运行
开始运行
开始运行
开始运行
Thread-2 碰到问题了:
Thread-1 碰到问题了:
Thread-3 碰到问题了:
Thread-0 碰到问题了:
java.lang.RuntimeException
	at concurrent.cancel.exception.handler.TestCase$MyThreadconcurrent.cancel.exceptionhandlerjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
java.lang.RuntimeException
	at concurrent.cancel.exception.handler.TestCase$MyThreadconcurrent.cancel.exceptionhandlerjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
java.lang.RuntimeException
	at concurrent.cancel.exception.handler.TestCase$MyThreadconcurrent.cancel.exceptionhandlerjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
java.lang.RuntimeException
	at concurrent.cancel.exception.handler.TestCase$MyThreadconcurrent.cancel.exceptionhandlerjava.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)

```


