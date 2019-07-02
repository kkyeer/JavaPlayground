package concurrent.deadlock;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author: kkyeer
 * @Description: 简单使用ThreadInfo类来查看死锁信息，需要手动停止
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */

class TasteThreadInfo {
    public static void main(String[] args) throws IOException {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        String lockA = "lock";
        String lockB = "lockB";
        class Transfer implements Runnable {
            private Object lock1;
            private Object lock2;

            private Transfer(Object lock1, Object lock2) {
                this.lock1 = lock1;
                this.lock2 = lock2;
            }

            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("Enter thread");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {
                        System.out.println("Leave thread");
                    }
                }
            }
        }
        Thread a2b = new Thread(new Transfer(lockA, lockB));
        long a2bId = a2b.getId();
        Thread b2a = new Thread(new Transfer(lockB, lockA));
        long b2aId = b2a.getId();
        ThreadInfo a2bInfo;
        ThreadInfo b2aInfo;
        a2b.start();
        b2a.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a2bInfo = threadMXBean.getThreadInfo(a2bId);
        String a2bBlockLock = a2bInfo.getLockInfo().getIdentityHashCode() == System.identityHashCode(lockB) ? "lockB" : "unknown";
        System.out.println("a2b lock:" + a2bBlockLock);
        b2aInfo = threadMXBean.getThreadInfo(b2aId);
        String b2aBlockLock = b2aInfo.getLockInfo().getIdentityHashCode() == System.identityHashCode(lockA) ? "lock" : "unknown";
        System.out.println("b2a lock:" + b2aBlockLock);
    }
}
