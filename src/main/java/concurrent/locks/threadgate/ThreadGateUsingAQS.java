package concurrent.locks.threadgate;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author: kkyeer
 * @Description: 使用AQS来构建二元线程的门，支持开关
 * @Date:Created in 10:35 2019/7/5
 * @Modified By:
 */
class ThreadGateUsingAQS extends AbstractQueuedSynchronizer implements ThreadGate{
    private int blockedCount = 0;
    private ThreadGateUsingAQS() {
        setState(0);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return getState() == arg ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        setState(arg);
        return true;
    }

    @Override
    public void waitDoor() {
        blockedCount ++;
        tryAcquireShared(1);
    }

    @Override
    public int openDoor() {
        tryReleaseShared(1);
        return blockedCount;
    }

    @Override
    public void closeDoor() {
        tryReleaseShared(0);
    }

    public static void main(String[] args) {
        TestCase.test(new ThreadGateUsingAQS());
    }
}
