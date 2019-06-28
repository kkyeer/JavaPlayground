package concurrent.locks;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 试用一下可重入锁
 * @Date:Created in 15:07 2019/6/28
 * @Modified By:
 */
class TasteReentrantLock {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        final int TEST_THREAD_COUNT = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        CyclicBarrier barrier = new CyclicBarrier(TEST_THREAD_COUNT + 1);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            executorService.submit(
                    () -> {
                        try {
                            barrier.await();
                            System.out.println("before");
                            reentrantLock.lock();
                            Random random = new Random();
                            int testNum = random.nextInt(1000);
                            if (testNum < 500) {
                                throw new RuntimeException("Random exception");
                            }
                            System.out.println("Over");
                            barrier.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        } finally {
                            reentrantLock.unlock();

                        }
                    }
            );
        }
        barrier.await();
        barrier.await();
        System.out.println("Should all be done.");

    }
}
