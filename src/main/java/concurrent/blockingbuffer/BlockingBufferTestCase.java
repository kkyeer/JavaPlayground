package concurrent.blockingbuffer;

import utils.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 测试一下有界阻塞式缓存
 * @Date:Created in 11:09 2019/6/9
 * @Modified By:
 */
class BlockingBufferTestCase {
    static final int BUFFER_SIZE = 100;
    public static void main(String[] args) throws InterruptedException {
        BlockingBuffer<String> blockingBuffer = new NotSafeBlockingBuffer<>(BUFFER_SIZE);
//        testFullAndEmptySequential(blockingBuffer);
//        testFullAndEmptyConcurrently(blockingBuffer);
//        testBlockPutWhenFull(blockingBuffer);
//        testBlockTakeWhenEmpty(blockingBuffer);
//        testConcurrentCorrectness(new SafeBlockingBufferWithIntrinsicLock<>(BUFFER_SIZE));
//        testConcurrentCorrectness(new NotSafeBlockingBuffer<>(BUFFER_SIZE));
        testPerformance();
    }

    static void testPerformance() throws InterruptedException {
        BlockingBuffer<Integer> blockingBuffer;
        int count = 100 * (Runtime.getRuntime().availableProcessors() + 1);
        for (int i = 10; i <= 10000; i*=10) {
            long start = System.nanoTime();
            blockingBuffer = new BlockingBufferWithIntrinsicConditionQueue<>(i);
            testConcurrentCorrectness(blockingBuffer);
            long duration = System.nanoTime() - start;
            int throughput = (int) (duration / count);
            System.out.println("Buffer size[" + i + "],average throughput " + throughput + " nano second per data ");
        }
    }

    /**
     * 利用随机数测试有界缓存的并发读取
     * @param blockingBuffer 有界缓存
     * @throws InterruptedException 抛出异常
     */
    static void testConcurrentCorrectness(BlockingBuffer<Integer> blockingBuffer) throws InterruptedException {
        int testTimesPerThread = 1000;
        int testThreadCount = Runtime.getRuntime().availableProcessors() + 1;
        // 可以循环利用
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2 * testThreadCount+1);
        ExecutorService executorService = Executors.newFixedThreadPool(testThreadCount*2);
        List<Future<Integer>> producerFutureList = new ArrayList<>(testThreadCount);
        List<Future<Integer>> consumerFutureList = new ArrayList<>(testThreadCount);
        for (int i = 0; i < testThreadCount; i++) {
            Future<Integer> producerSumFuture = executorService.submit(
                    ()->{
                        int sum = 0;
                        // producer
                        try {
                            cyclicBarrier.await();
                            int seed = (1<<8)^(int) System.nanoTime();
                            for (int j = 0; j < testTimesPerThread; j++) {
                                blockingBuffer.put(seed);
                                sum+=seed;
                                seed = xorShift(seed);
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        return sum;
                    }
            );
            producerFutureList.add(producerSumFuture);
            Future<Integer> consumerSumFuture = executorService.submit(
                    () -> {
                        int sum = 0;
                        // consumer
                        try {
                            cyclicBarrier.await();
                            for (int j = 0; j < testTimesPerThread; j++) {
                                int value = blockingBuffer.take();
                                sum+=value;
                            }
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        return sum;
                    }
            );
            consumerFutureList.add(consumerSumFuture);
        }
        try {
            // Start all Thread
            cyclicBarrier.await();
            // end all Thread
            cyclicBarrier.await();
            // calc sum and validate
            int producerSum = 0;
            for (Future<Integer> integerFuture : producerFutureList) {
                producerSum+=integerFuture.get();
            }
            int consumerSum = 0;
            for (Future<Integer> integerFuture : consumerFutureList) {
                consumerSum+=integerFuture.get();
            }
            Assertions.assertTrue(producerSum == consumerSum);
            System.out.println("Pass test");
            executorService.shutdown();
        } catch (BrokenBarrierException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试从空的缓存里拿是否阻塞
     * @param blockingBuffer 缓存
     * @throws InterruptedException 异常
     */
    static void testBlockTakeWhenEmpty(BlockingBuffer<String> blockingBuffer) throws InterruptedException {
        Thread testThread= new Thread(
                ()->{
                    try {
                        blockingBuffer.take();
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
     * @param blockingBuffer 缓存
     * @throws InterruptedException 异常
     */
    static void testBlockPutWhenFull(BlockingBuffer<String> blockingBuffer) throws InterruptedException {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            blockingBuffer.put("" + i);
        }
        Thread testThread= new Thread(
                ()->{
                    try {
                        blockingBuffer.put("1");
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
    static void testFullAndEmptyConcurrently(BlockingBuffer<String> blockingBuffer) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier startCyclicBarrier = new CyclicBarrier(BUFFER_SIZE+1);
        for (int i = 0; i < BUFFER_SIZE; i++) {
            new Thread(
                    ()->{
                        try {
                            startCyclicBarrier.await();
                            Thread.sleep(10);
                            blockingBuffer.put("a");
                            startCyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        }
        startCyclicBarrier.await();
        startCyclicBarrier.await();
        Assertions.assertTrue(!blockingBuffer.isEmpty());
        Assertions.assertTrue(blockingBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            new Thread(
                    ()->{
                        try {
                            startCyclicBarrier.await();
                            Thread.sleep(10);
                            blockingBuffer.take();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        try {
                            startCyclicBarrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        }
        startCyclicBarrier.await();
        startCyclicBarrier.await();
        Assertions.assertTrue(!blockingBuffer.isFull());
        Assertions.assertTrue(blockingBuffer.isEmpty());
        System.out.println("Passed");
    }

    /**
     * 线性测试是否能准确判断空与满的状态
     * @throws InterruptedException 抛出异常
     */
    static void testFullAndEmptySequential(BlockingBuffer<String> blockingBuffer) throws InterruptedException {
        System.out.println("--------------before put");
        Assertions.assertTrue(blockingBuffer.isEmpty());
        Assertions.assertTrue(!blockingBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            blockingBuffer.put("" + i);
        }
        System.out.println("--------------after put");
        Assertions.assertTrue(!blockingBuffer.isEmpty());
        Assertions.assertTrue(blockingBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            blockingBuffer.take();
        }
        System.out.println("---------------after take");
        Assertions.assertTrue(blockingBuffer.isEmpty());
        Assertions.assertTrue(!blockingBuffer.isFull());
    }

    /**
     * 一定范围内生成随机数
     * @param y 种子
     * @return 随机数
     */
    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
