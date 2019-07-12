package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 并发测试类，保证线程池中的线程同时收到启动指令，结束后执行相关方法（若有）
 * @Date:Created in 10:14 2019/6/29
 * @Modified By: kkyeer 2019/7/12 增加全部线程运行完毕后运行指定方法的功能
 */
public class ConcurrentTest {
    //    测试线程数
    private static final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    //    测试用的线程池
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(TEST_THREAD_COUNT);

    /**
     * 使用时，只需要将runnable对象传入
     * @param threadRunnable 需要在线程池运行的runnable对象
     */
    public static void test(Runnable threadRunnable){
        CyclicBarrier barrier = new CyclicBarrier(TEST_THREAD_COUNT + 1);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            THREAD_POOL.submit(
                    ()->{
                        try {
                            barrier.await();
                            threadRunnable.run();
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        try {
            barrier.await();
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("-----TEST FINISH-----");
        THREAD_POOL.shutdown();
    }
}
