package concurrent.tvolatile;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: kkyeer
 * @Description: 测试volatile关键字对线程安全的作用
 * @Date:Created in 13:36 2019/6/12
 * @Modified By:
 */
class Test {
    private static final int THREAD_COUNT = 8;
    private int globalCount = 0;

    void listen() throws InterruptedException {
        // volatile 关键字关键是保证，某个线程对于变量的修改，一定会写入缓存中被其他线程读取，即保证线程可见性
        // 在这个例子里，线程读取的是缓存，如果没有volatile关键字，则另外一个线程
        // 对变量的修改不会写入cpu cache，导致本线程读取的值为旧值，无法探测到变化
        int local = 0;
        while (globalCount < THREAD_COUNT) {
            if (local != globalCount) {
                System.out.println("Changed to:" + globalCount);
                local = globalCount;
            }
            Thread.sleep(1);
        }
    }

    void change() throws InterruptedException {
        while (globalCount < THREAD_COUNT) {
            globalCount++;
            Thread.sleep(500);
        }
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        Test notSafe = new Test();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(
                    () -> {
                        try {
                            cyclicBarrier.await();
                            notSafe.listen();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
        }
        new Thread(
                () -> {
                    try {
                        cyclicBarrier.await();
                        notSafe.change();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        cyclicBarrier.await();

    }
}
