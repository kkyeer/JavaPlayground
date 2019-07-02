package concurrent.locks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 测试下自定义的缓存
 * @Date:Created in 15:39 2019/7/2
 * @Modified By:
 */
class CacheTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        testCache(Collections.synchronizedMap(new HashMap<>()));
        testCache(new Cache());
        testCache(new ConcurrentHashMap<>());
    }

    static void testCache(Map<String, Object> cache) throws BrokenBarrierException, InterruptedException {
        final int TEST_WRITE_TIMES = 100;
        final int TEST_READ_TIMES = 1000;
        final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT * 2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2 * THREAD_COUNT + 1);
        for (int i = 1; i < THREAD_COUNT+1; i++) {
            int finalI = i;
            threadPool.submit(
                    ()->{
                        try {
                            cyclicBarrier.await();
                            for (int j = 0; j < TEST_WRITE_TIMES; j++) {
                                if (j % finalI == 0) {
                                    cache.put("key-" + j, j);
                                }
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            );
            threadPool.submit(
                    ()->{
                        try {
                            cyclicBarrier.await();
                            for (int j = 0; j < TEST_READ_TIMES; j++) {
                                for (int k = 0; k < TEST_WRITE_TIMES; k++) {
                                    cache.get("key-" + k);
                                }
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        long start = System.nanoTime();
        cyclicBarrier.await();
        cyclicBarrier.await();
        long duration = System.nanoTime() - start;
        System.out.println("duration:" + duration);
        threadPool.shutdown();
    }
}
