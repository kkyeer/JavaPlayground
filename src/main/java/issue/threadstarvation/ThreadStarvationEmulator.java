package issue.threadstarvation;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;


/**
 * @Author: kkyeer
 * @Description: 线程池内部Task提交异步线程到本线程池导致的死锁
 * @Date:Created in 4:20 PM 2021/5/20
 * @Modified By:
 */
public class ThreadStarvationEmulator {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadStarvationEmulator.simulateFullBlock();
        ThreadStarvationEmulator.simulateHalfBlock();
        ThreadStarvationEmulator.solveWithSynchronousQueue();
    }


    private final ExecutorService shareThreadPool;
    private final int batchInvokeCount;

    public ThreadStarvationEmulator(ExecutorService shareThreadPool, int batchInvokeCount){
        this.shareThreadPool = shareThreadPool;
        this.batchInvokeCount = batchInvokeCount;
    }

    /**
     * 模拟线程池所有线程死锁的情况
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void simulateFullBlock() throws ExecutionException, InterruptedException {
        System.out.println("----------------------模拟全死锁-------------------------");
        ExecutorService executorService = new ThreadPoolExecutor(10, 30,1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(100),Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy());
        new ThreadStarvationEmulator(executorService,11).test();
        executorService.shutdownNow();
        System.out.println("----------------------完成-------------------------");
    }

    /**
     * 模拟线程池因为部分死锁导致速度减慢的情况
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void simulateHalfBlock() throws ExecutionException, InterruptedException {
        System.out.println("----------------------模拟半死锁--------------------");
        ExecutorService executorService = new ThreadPoolExecutor(10, 100,1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(100),Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy());
        new ThreadStarvationEmulator(executorService,9).test();
        executorService.shutdownNow();
        System.out.println("----------------------完成-------------------------");
    }

    /**
     * 使用同步队列线程池解决上述问题
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void solveWithSynchronousQueue() throws ExecutionException, InterruptedException {
        System.out.println("----------------------模拟问题解决------------------");
        ExecutorService executorService = new ThreadPoolExecutor(10, 100,1, TimeUnit.MINUTES,
                new SynchronousQueue<>(),Thread::new,
                new ThreadPoolExecutor.CallerRunsPolicy());
        new ThreadStarvationEmulator(executorService,9).test();
        executorService.shutdownNow();
        System.out.println("----------------------完成-------------------------");
    }

    public void test() throws ExecutionException, InterruptedException {
        List<Future<String>> outerFutureList = new ArrayList<>(10);
        for (int i = 0; i < batchInvokeCount; i++) {
            outerFutureList.add(
                    shareThreadPool.submit(new MainTask(shareThreadPool))
            );
        }
        blockUntilAllFinished(outerFutureList,true,2000L);
    }

    private static class MainTask implements Callable<String> {
        private final ExecutorService shareThreadPool;

        public MainTask(ExecutorService executorService) {
            this.shareThreadPool = executorService;
        }

        @Override
        public String call() throws InterruptedException {
//            rpc等耗时操作
            TimeUnit.MILLISECONDS.sleep(10L);
            List<Future<String>> innerFutureList = new ArrayList<>(3);
//            因为有些SDK的接口参数有size限制，所以并发调用多次，考虑节省线程资源，复用已有线程池
            for (int i = 0; i < 5; i++) {
                innerFutureList.add(
                        this.shareThreadPool.submit(new SubTask())
                );
            }
            blockUntilAllFinished(innerFutureList,false,2000L);
            return "OK";
        }

        public static class SubTask implements Callable<String> {
            @Override
            public String call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(10L);
                return "Sub ok";
            }
        }
    }

    /**
     * 阻塞直到所有future均done
     * @param futureList future列表
     * @param printTimeConsume 是否打印耗时
     * @param timeoutMillis 超时毫秒数
     * @param <E> 结果泛型
     * @return 结果列表
     * @throws InterruptedException 异常
     */
    private static <E> List<E> blockUntilAllFinished(List<Future<E>> futureList,boolean printTimeConsume,Long timeoutMillis) throws InterruptedException {
        List<E> resultList = new ArrayList<>();
        long start = System.currentTimeMillis();
        while (true){
            if (futureList.isEmpty()) {
                break;
            }
            Iterator<Future<E>> futureIterator = futureList.iterator();
            while (futureIterator.hasNext()) {
                Future<E> future = futureIterator.next();
                if((System.currentTimeMillis() - start) > timeoutMillis){
                    if (printTimeConsume) {
                        System.out.println("Timeout:consume "+(System.currentTimeMillis()-start)+" millis.");
                    }
                    futureIterator.remove();
                    continue;
                }
                if (future.isDone() ) {
                    try {
                        E futureResult = future.get();
                        if (printTimeConsume) {
                            System.out.println("Time consume:" + (System.currentTimeMillis() - start) + "ms");
                        }
                        resultList.add(futureResult);
                        futureIterator.remove();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        futureIterator.remove();
                    }
                }
            }
            Thread.yield();

        }
        return resultList;
    }

}
