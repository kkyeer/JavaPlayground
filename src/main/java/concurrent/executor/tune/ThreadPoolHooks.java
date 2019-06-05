package concurrent.executor.tune;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: ThreadPool的钩子方法
 * @Date:Created in 10:52 2019/6/3
 * @Modified By:
 */
class ThreadPoolHooks extends ThreadPoolExecutor{

    ThreadPoolHooks(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        System.out.println("Before execute thread:" + t.getName());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t != null) {
            System.out.println("After execute thread with throwable:");
            t.printStackTrace();
        }else {
            System.out.println("After execute:"+Thread.currentThread().getName());
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        System.out.println("Terminated");
    }

    public static void main(String[] args) {
        AtomicInteger threadIndex = new AtomicInteger();
        ThreadFactory threadFactory = (runnable)->{
            Thread thread = new Thread(runnable);
            thread.setName("custom-thread-" + threadIndex.getAndAdd(1));
            return thread;
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolHooks(0, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>(),threadFactory);
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threadPoolExecutor.execute(
                    ()->{
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
