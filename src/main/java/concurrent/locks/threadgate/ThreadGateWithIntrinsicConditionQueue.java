package concurrent.locks.threadgate;

import java.util.concurrent.BrokenBarrierException;

/**
 * @Author: kkyeer
 * @Description: 阻塞所有线程直到门开
 * @Date:Created in 22:59 2019/7/3
 * @Modified By:
 */
class ThreadGateWithIntrinsicConditionQueue implements ThreadGate{
    volatile int state = 0;
    /**
     * 使用version来控制，原因是，理论上，此门的作用为：当开门时，所有已经在等待门开的线程全部放过
     */
    volatile int version;
    volatile int blockedCount = 0;

    /**
     * 等待门开方法，使用version来进行控制，当调用时version为1，只是因为当前未开门而阻塞时，在被notifyAll方法唤醒后，
     * 发现当前version
     * 校验可知当前version
     *
     * @throws InterruptedException 异常
     */
    public synchronized void waitDoor() throws InterruptedException {
        // 等待门开时候的version
        int versionOnWait = version;
        // 如果本次校验中version还是这个version并且门还是关着的，继续阻塞直到state变成门开，或者version提高
        while (state == 0 && versionOnWait == version) {
            blockedCount++;
            wait();
        }
    }

    public synchronized int openDoor() {
        int blocked = blockedCount;
        version++;
        state = 1;
        notifyAll();
        return blocked;
    }

    @Override
    public synchronized void closeDoor() {
        state = 0;
        blockedCount = 0;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        TestCase.test(new ThreadGateWithIntrinsicConditionQueue());
    }
}
