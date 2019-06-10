package concurrent.semaphore;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 运行以后可以观察到，在boundHashSet达到上限后，必须取出一个才能再放进一个，即有界集合
 *
 * @Author: kkyeer
 * @Description: 测试有上界的Set集合
 * @Date:Created in 20:27 2019/5/17
 * @Modified By:
 */
class BoundedHashSetTestCase {
    /**
     * 测试线程数
     */
    private static final int TEST_THREAD_COUNT = 4;
    /**
     * 关闭阈值
     */
    private static final int SHUT_DOWN_THRESHOLD = 30;

    public static void main(String[] args) {
        CountDownLatch startLatch = new CountDownLatch(1);
        BoundedHashSet<Integer> boundedHashSet = new BoundedHashSet<>(20);
        BlockingDeque<Integer> added = new LinkedBlockingDeque<>();
        // 生产者计数
        AtomicInteger producerCount = new AtomicInteger(0);
        // 消费者计数
        AtomicInteger consumerCount = new AtomicInteger(0);

        // 使用线程池来测试
        // producer 线程池
        ExecutorService producerExecutor = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        // consumer 线程池
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(TEST_THREAD_COUNT);


        Random random = new Random();
        // 生产者
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            producerExecutor.execute(() -> {
                while (producerCount.addAndGet(1) < SHUT_DOWN_THRESHOLD) {
                    int nextInt = 1000 + random.nextInt(1000);
                    boundedHashSet.add(nextInt);
                    added.add(nextInt);
                    System.out.println("added:" + nextInt + ",size:" + boundedHashSet.getSize());
                    try {
                        Thread.sleep(nextInt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("shutting down producer");
                producerExecutor.shutdown();
                System.out.println("shut down producer");
            });
        }
        // 消费者
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            consumerExecutor.execute(() -> {
                        while (consumerCount.addAndGet(1) < SHUT_DOWN_THRESHOLD) {
                            int toRemove = added.remove();
                            boundedHashSet.remove(toRemove);
                            System.out.println("consumed 1 number");
                            int nextInt = 5000 + random.nextInt(3000);
                            try {
                                Thread.sleep(nextInt);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("shutting down consumer");
                        consumerExecutor.shutdown();
                        System.out.println("shut down consumer");
                    }

            );
        }
        startLatch.countDown();
    }
}
