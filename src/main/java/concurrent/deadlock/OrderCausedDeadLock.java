package concurrent.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 因为锁的调用顺序不同导致的死锁
 * @Date:Created in 17:06 2019/6/4
 * @Modified By:
 */
class OrderCausedDeadLock {
    /**
     * 潜在死锁风险
     */
    private static class ErrorProneAccount implements Account{
        public void transfer(Account target, int amount) {
            try {
                synchronized (this) {
                    System.out.println("before transfer,check balance and so on");
                    // IO time estimated 1 second
                    Thread.sleep(1000);
                    synchronized (target) {
                        // IO time estimated 1 second
                        Thread.sleep(1000);
                        System.out.println("transferred money");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取不到锁时，报错
     */
    private static class NoneBlockAccount implements Account{
        ReentrantLock lock = new ReentrantLock();

        public ReentrantLock getLock() {
            return lock;
        }

        public void transfer(Account target, int amount) {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println("before transfer,check balance and so on");
                    // IO time estimated 1 second
                    Thread.sleep(1000);
                    if (((NoneBlockAccount)target).getLock().tryLock(1, TimeUnit.SECONDS)) {
                        // IO time estimated 1 second
                        Thread.sleep(1000);
                        System.out.println("Transfer SUCCESS");
                        ((NoneBlockAccount)target).getLock().unlock();
                    }else {
                        System.out.println("Error while query lock,transfer failed");
                    }
                    lock.unlock();
                }else {
                    System.out.println("Error while query lock,transfer failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SolvedDeadlockAccount implements Account{
        public void transfer(Account target, int amount) {
            // 根据锁的大小来决定加锁顺序
            Account lockA = System.identityHashCode(this)<=System.identityHashCode(target)?this:target;
            Account lockB = System.identityHashCode(this)<=System.identityHashCode(target)?target:this;
            try {
                synchronized (lockA) {
                    System.out.println("before transfer,check balance and so on");
                    // IO time estimated 1 second
                    Thread.sleep(1000);
                    synchronized (lockB) {
                        // IO time estimated 1 second
                        Thread.sleep(1000);
                        System.out.println("transferred money");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {


//        Account accountA = new ErrorProneAccount();
//        Account accountB = new ErrorProneAccount();


//        Account accountA = new SolvedDeadlockAccount();
//        Account accountB = new SolvedDeadlockAccount();

        Account accountA = new NoneBlockAccount();
        Account accountB = new NoneBlockAccount();
        new Thread(
                ()-> accountA.transfer(accountB,100)
        ).start();
        new Thread(
                ()-> accountB.transfer(accountA,100)
        ).start();
    }
}
