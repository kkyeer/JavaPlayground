package concurrent.locks.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 使用AQS实现一个可重入锁，使用AQS，state为0是未上锁，state为1是已上锁
 * @Date:Created in 13:49 2019/7/12
 * @Modified By:
 */
class ReentrantLockUsingAQS extends AbstractQueuedSynchronizer implements Lock {
    /**
     * 当前持有锁的线程
     */
    private Thread lockingThread;
    /**
     * 加锁的数量
     */
    private int count;

    @Override
    public void lock() {
        acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * AQS的互斥方法
     * @param arg 参数
     * @return 是否获取成功
     */
    @Override
    protected synchronized boolean tryAcquire(int arg) {
        if (getState() == 0) {
            lockingThread = Thread.currentThread();
            setState(1);
            count++;
            return true;
        } else if (Thread.currentThread().equals(lockingThread)) {
            count++;
            return true;
        }
        return false;
    }

    @Override
    protected synchronized boolean tryRelease(int arg) {
        if (getState() == 0) {
            return false;
        } else if (Thread.currentThread().equals(lockingThread)) {
            if (--count == 0) {
                setState(0);
            }
            return true;
        }
        return false;
    }

    // 测试一下
    public static void main(String[] args) {
        ReentrantLockTest.test(ReentrantLock.class);
    }
}
