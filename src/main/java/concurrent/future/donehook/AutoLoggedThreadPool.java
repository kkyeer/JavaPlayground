package concurrent.future.donehook;

import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 自动记录日志的线程池（通过自定义FutureTask的done方法）
 * @Date:Created in 16:47 2019/6/3
 * @Modified By:
 */

/**
 * todo 用了继承来实现，不好，应该用组合
 */
class AutoLoggedThreadPool extends ThreadPoolExecutor {

    public AutoLoggedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public AutoLoggedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public AutoLoggedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public AutoLoggedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
    /**
     * todo 直接复制了父类的代码，不好
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        LoggedFutureWithDoneHook<T> ftask = new LoggedFutureWithDoneHook<>(task);
        execute(ftask);
        return ftask;
    }

    public static void main(String[] args) {
        ExecutorService executorService = new AutoLoggedThreadPool(0, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>());
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Future<String> futureTask = executorService.submit(
                    ()->{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "hh";
                    }
            );
            executorService.execute(
                    ()->{
                        try {
                            System.out.println("Got result:"+futureTask.get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
