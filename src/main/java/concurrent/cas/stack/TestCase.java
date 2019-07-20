package concurrent.cas.stack;

import concurrent.ConcurrentTest;
import utils.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 测试栈
 * @Date:Created in 10:43 2019/7/20
 * @Modified By:
 */
class TestCase {
    static void test(Class<? extends Stack> stackClass) {
        testConcurrentWrite(stackClass);
        System.out.println("并发写测试通过");
        testConcurrentReadWrite(stackClass);
        System.out.println("并发读写测试通过");
        testPerformance(stackClass);
    }
    /**
     * 测试并发写
     */
    static void testConcurrentWrite(Class<? extends Stack> stackClass) {
        // N个线程均向同一个栈顺序写入X个数，写完后进行验证，确认1:总数量是否为N*X;2:1到X的每个数是否均出现N次
        final int WRITE_COUNT_PER_THREAD = 100;
        try {
            Stack<Integer> stack = stackClass.newInstance();
            ConcurrentTest.test(
                    ()->{
                        for (int i = 0; i < WRITE_COUNT_PER_THREAD; i++) {
                            stack.push(i);
                        }
                    }
            );
            System.out.println("并发写完毕");
            // 验证阶段
            Map<Integer, Integer> verifyMap = new HashMap<>();
            Integer next;
            while ((next = stack.pop()) != null) {
                Integer count = verifyMap.get(next);
                if (count == null) {
                    verifyMap.put(next, 1);
                }else {
                    verifyMap.put(next, ++count);
                }
            }
            int presumeCount = Runtime.getRuntime().availableProcessors();
            for (int i = 0; i < WRITE_COUNT_PER_THREAD; i++) {
                Integer count = verifyMap.get(i);
                if (count != presumeCount) {
                    throw new RuntimeException(String.format("验证失败，数字%d需要%d,实际%d", i, presumeCount, count));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试并发读写
     */
    static void testConcurrentReadWrite(Class<? extends Stack> stackClass) {
        // N个写线程均向同一个栈顺序写入X个数，N个读线程向同一个栈读X次
        // 全部验证完成后验证栈是否为空，为空则线程安全
        final int COUNT_PER_THREAD = 100;
        try {
            Stack<Integer> stack = stackClass.newInstance();
            int threadCount = Runtime.getRuntime().availableProcessors();
            ExecutorService threadPool = Executors.newFixedThreadPool(2 * threadCount);
            CyclicBarrier barrier = new CyclicBarrier(2 * threadCount + 1);
            for (int i = 0; i < threadCount; i++) {
                threadPool.submit(
                        ()->{
                            try {
                                barrier.await();
                                for (int j = 0; j < COUNT_PER_THREAD; j++) {
                                    stack.push(j);
                                }
                                barrier.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
            for (int i = 0; i < threadCount; i++) {
                threadPool.submit(
                        ()->{
                            try {
                                barrier.await();
                                for (int j = 0; j < COUNT_PER_THREAD; j++) {
                                    // 非阻塞方法靠自旋来保证一定会pop
//                                    System.out.println(stack.pop());
                                    while (stack.pop()==null);
                                }
                                barrier.await();
                            } catch (InterruptedException | BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
            barrier.await();
            barrier.await();
            threadPool.shutdown();
            Assertions.assertTrue(stack.pop() == null);
        } catch (InstantiationException | IllegalAccessException | InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试性能
     *
     * @param stackClass 要测试的栈类
     */
    static void testPerformance(Class<? extends Stack> stackClass) {
        // N个写线程均向同一个栈顺序写入X（10000，100000，1000000）个数，测试全部写完所需时间
        int COUNT_PER_THREAD = 10000;
        // 每组测试重复的次数
        int TEST_REPEAT_TIMES = 5;
        for (int i = 0; i < 3; i++) {
            System.out.println("测试数据量：" + COUNT_PER_THREAD);
            // 测试5次求平均值
            long sumTime = 0;
            int threadCount = Runtime.getRuntime().availableProcessors();
            ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
            for (int j = 0; j < TEST_REPEAT_TIMES; j++) {
                long start = System.nanoTime();
                try {
                    Stack<Integer> stack = stackClass.newInstance();
                    CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);
                    for (int k = 0; k < threadCount; k++) {
                        int finalCOUNT_PER_THREAD = COUNT_PER_THREAD;
                        threadPool.submit(
                                ()->{
                                    try {
                                        barrier.await();
                                        for (int l = 0; l < finalCOUNT_PER_THREAD; l++) {
                                            stack.push(l);
                                        }
                                        barrier.await();
                                    } catch (InterruptedException | BrokenBarrierException e) {
                                        e.printStackTrace();
                                    }
                                }
                        );
                    }
                    barrier.await();
                    barrier.await();
                } catch (InstantiationException | IllegalAccessException | InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                long duration = System.nanoTime() - start;
//                System.out.println("测试" + j + ":" + (duration / 1000000) + "毫秒");
                sumTime += duration;
            }
            threadPool.shutdown();
            System.out.println("平均：" + (sumTime / TEST_REPEAT_TIMES / 1000000) + "毫秒");
            COUNT_PER_THREAD *= 10;
        }
    }
}
