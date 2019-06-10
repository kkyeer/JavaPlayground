package concurrent.semaphore;

import utils.Assertions;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试一下有界阻塞式缓存
 * @Date:Created in 11:09 2019/6/9
 * @Modified By:
 */
class BoundedBufferTestCase {
    private static final int BUFFER_SIZE = 100;
    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer<String> boundedBuffer = new NotSafeBoundedBuffer<>(BUFFER_SIZE);
//        testFullAndEmptySequential(boundedBuffer);
//        testFullAndEmptyConcurrently(boundedBuffer);
//        testBlockPutWhenFull(boundedBuffer);
//        testBlockTakeWhenEmpty(boundedBuffer);
//        testConcurrentCorrectness(new SafeBoundedBuffer<>(BUFFER_SIZE));
//        testConcurrentCorrectness(new NotSafeBoundedBuffer<>(BUFFER_SIZE));
        testPerformance();
    }

    private static void testPerformance() throws InterruptedException {
        BoundedBuffer<Integer> boundedBuffer;
        int count = 100 * (Runtime.getRuntime().availableProcessors() + 1);
        for (int i = 10; i <= 10000; i*=10) {
            long start = System.nanoTime();
            boundedBuffer = new SafeBoundedBuffer<>(i);
            testConcurrentCorrectness(boundedBuffer);
            long duration = System.nanoTime()-start;
            int throughput = (int) (duration/count);
            System.out.println("Buffer size["+i+"],average throughput "+throughput+" nano second per data ");
        }
    }

    /**
     * 利用随机数测试有界缓存的并发读取
     * @param boundedBuffer 有界缓存
     * @throws InterruptedException 抛出异常
     */
    private static void testConcurrentCorrectness(BoundedBuffer<Integer>  boundedBuffer) throws InterruptedException {
        int testTimesPerThread = 100;
        int testThreadCount = Runtime.getRuntime().availableProcessors() + 1;
        // 可以循环利用
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2 * testThreadCount+1);
        AtomicInteger addSum = new AtomicInteger(0);
        AtomicInteger takeSum = new AtomicInteger(0);
        for (int i = 0; i < testThreadCount; i++) {
            new Thread(
                    ()->{
                        // producer
                        try {
                            cyclicBarrier.await();
                            int seed = (1<<8)^(int) System.nanoTime();
                            for (int j = 0; j < testTimesPerThread; j++) {
                                boundedBuffer.put(seed);
                                addSum.getAndAdd(seed);
                                seed = xorShift(seed);
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }

                    }
            ).start();
            new Thread(
                    () -> {
                        // consumer
                        try {
                            cyclicBarrier.await();
                            for (int j = 0; j < testTimesPerThread; j++) {
                                int value = boundedBuffer.take();
                                takeSum.getAndAdd(value);
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }

                    }
            ).start();
        }
        try {
            // Start all Thread
            cyclicBarrier.await();
            // end all Thread
            cyclicBarrier.await();
            Assertions.assertTrue(addSum.get()==takeSum.get());
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试从空的缓存里拿是否阻塞
     * @param boundedBuffer 缓存
     * @throws InterruptedException 异常
     */
    private static void testBlockTakeWhenEmpty(BoundedBuffer<String> boundedBuffer) throws InterruptedException {
        Thread testThread= new Thread(
                ()->{
                    try {
                        boundedBuffer.take();
                        throw new AssertionError();
                    } catch (InterruptedException e) {
                        System.out.println("Blocked");
                    }
                }
        );
        testThread.start();
        Thread.sleep(2000);
        testThread.interrupt();
        testThread.join();
        Assertions.assertTrue(!testThread.isAlive());
    }

    /**
     * 测试往满的缓存里放是否阻塞
     * @param boundedBuffer 缓存
     * @throws InterruptedException 异常
     */
    private static void testBlockPutWhenFull(BoundedBuffer<String> boundedBuffer) throws InterruptedException {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            boundedBuffer.put("" + i);
        }
        Thread testThread= new Thread(
                ()->{
                    try {
                        boundedBuffer.put("1");
                        throw new AssertionError();
                    } catch (InterruptedException e) {
                        System.out.println("Blocked");
                    }
                }
        );
        testThread.start();
        Thread.sleep(2000);
        testThread.interrupt();
        testThread.join();
        Assertions.assertTrue(!testThread.isAlive());
    }

    /**
     * 并发测试是否能准确判断空与满的状态
     * @throws InterruptedException 抛出异常
     */
    private static void testFullAndEmptyConcurrently(BoundedBuffer<String> boundedBuffer) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier startCyclicBarrier = new CyclicBarrier(BUFFER_SIZE+1);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            new Thread(
                    ()->{
                        try {
                            startCyclicBarrier.await();
                            Thread.sleep(10);
                            boundedBuffer.put("a");
                            System.out.println("put");
                            startCyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        }
        startCyclicBarrier.await();
        System.out.println("--------------after put------------");
        Assertions.assertTrue(!boundedBuffer.isEmpty());
        Assertions.assertTrue(boundedBuffer.isFull());
        startCyclicBarrier.reset();
        CountDownLatch endLatch2 = new CountDownLatch(BUFFER_SIZE);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            new Thread(
                    ()->{
                        try {
                            startCyclicBarrier.await();
                            Thread.sleep(10);
                            boundedBuffer.take();
                            System.out.println("take");
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        endLatch2.countDown();
                    }
            ).start();
        }
        endLatch2.await();
        System.out.println("---------------after take------------");
        Assertions.assertTrue(!boundedBuffer.isFull());
        Assertions.assertTrue(boundedBuffer.isEmpty());
    }

    /**
     * 线性测试是否能准确判断空与满的状态
     * @throws InterruptedException 抛出异常
     */
    private static void testFullAndEmptySequential(BoundedBuffer<String> boundedBuffer) throws InterruptedException {
        System.out.println("--------------before put");
        Assertions.assertTrue(boundedBuffer.isEmpty());
        Assertions.assertTrue(!boundedBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            boundedBuffer.put("" + i);
        }
        System.out.println("--------------after put");
        Assertions.assertTrue(!boundedBuffer.isEmpty());
        Assertions.assertTrue(boundedBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            boundedBuffer.take();
        }
        System.out.println("---------------after take");
        Assertions.assertTrue(boundedBuffer.isEmpty());
        Assertions.assertTrue(!boundedBuffer.isFull());
    }

    /**
     * 一定范围内生成随机数
     * @param y 种子
     * @return 随机数
     */
    private static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
