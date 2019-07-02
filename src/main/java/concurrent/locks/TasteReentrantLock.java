package concurrent.locks;

import concurrent.ConcurrentTest;

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
    public static void main(String[] args){
        ReentrantLock reentrantLock = new ReentrantLock();
        ConcurrentTest.test(
                ()->{
                    System.out.println("before");
                    reentrantLock.lock();
                    Random random = new Random();
                    int testNum = random.nextInt(1000);
                    if (testNum < 500) {
                        throw new RuntimeException("Random exception");
                    }
                    System.out.println("Over");
                    reentrantLock.unlock();
                }
        );
    }
}
