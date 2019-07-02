package concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 并发测试类
 * @Date:Created in 10:14 2019/6/29
 * @Modified By:
 */
public class ConcurrentTest {
    private static final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
    public static void test(Runnable runnable){
        CyclicBarrier barrier = new CyclicBarrier(TEST_THREAD_COUNT + 1);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            THREAD_POOL.submit(
                    ()->{
                        try {
                            barrier.await();
                            runnable.run();
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
