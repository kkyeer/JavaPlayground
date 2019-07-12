package concurrent.locks.reentrant;

import utils.Assertions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 测试一下各种可重入锁
 * @Date:Created in 14:13 2019/7/12
 * @Modified By:
 */
class ReentrantLockTest {
    /**
     * 测试锁是否正确互斥锁，即lock方法是否能保证在某一个线程获取时，另外一个线程无法获取锁
     * 思路: 第一个线程获取锁，睡眠1秒钟，第二个线程晚于第一个线程启动，第二个线程一秒内无法操作，1秒后可以操作
     * 有理由相信lock方法运行正常
     * @param lock 要测试的锁对象
     */
    static void testExclusion(Lock lock) {
        AtomicInteger state = new AtomicInteger(0);
        Thread thread1 = new Thread(
                ()->{
                    lock.lock();
                    state.getAndIncrement();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
        );
        thread1.start();
        Assertions.assertTrue(state.get() == 0);
        long start = System.currentTimeMillis();
        lock.lock();
        long duration = System.currentTimeMillis() - start;
        // 测试获取锁过程中阻塞的时间是否大致在1000毫秒左右
        Assertions.assertTrue(duration >= 1000 && duration <= 2000);
        Assertions.assertTrue(state.get() == 1);
        lock.unlock();
    }

    /**
     * 测试可重入性，如果程序正常运行完成即为可重入性正常
     * @param lock 可重入锁
     */
    static void testReentrancy(Lock lock) {
        Thread thread1 = new Thread(
                ()->{
                    lock.lock();
                    new Thread(
                            ()->{
                              lock.lock();
                              lock.unlock();
                            }
                    ).start();
                    lock.unlock();
                }
        );
        thread1.start();
        lock.lock();
        lock.unlock();
    }

    public static void test(Class<? extends Lock> lockClass) {
        Lock lock  = null;
        try {
            lock = lockClass.newInstance();
            testExclusion(lock);
            lock = lockClass.newInstance();
            testReentrancy(lock);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test(ReentrantLock.class);
    }
}
