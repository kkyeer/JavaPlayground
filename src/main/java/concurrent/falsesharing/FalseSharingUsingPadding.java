package concurrent.falsesharing;

import concurrent.ConcurrentTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 演示一下伪共享，以及通过padding、Contended的方法来避免，注释内部类里面的6个padding变量后，时间大幅上升
 * @Date:Created in 11:04 2019/7/26
 * @Modified By:
 */
class FalseSharingUsingPadding {
    public static void main(String[] args) {
        final long TEST_COUNT = 1000 * 1000 * 500L;
        final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
        // 使用数组保证对象会被多个Core共享
        TestCacheLine[] objArray = new TestCacheLine[TEST_THREAD_COUNT];
        for (int i = 0; i < objArray.length; i++) {
            objArray[i] = new TestCacheLine();
        }

        long start = System.nanoTime();
        AtomicInteger arrayIndex = new AtomicInteger(0);
        ConcurrentTest.test(
                ()->{
                    int threadArrayIndex = arrayIndex.getAndIncrement();
                    for (long i = 0; i < TEST_COUNT; i++) {
                        objArray[threadArrayIndex].value = i;
                    }
                }
        );
        long duration = System.nanoTime() - start;
        System.out.println("Time:" + (duration / 1000 / 1000) + " milliseconds");
    }

    /**
     * Java8以后，使用下面的注解会自动补齐缓存行，也可以避免出现伪共享问题，但是需要在jvm启动时加上参数：-XX:-RestrictContended
     */
    @sun.misc.Contended
    private final static class TestCacheLine {
        public volatile long value = 0L;
        // 添加下面的padding变量，可以保证不会共用Cache line
//        private long l1,l2,l3,l4,l5,l6;
    }
}
