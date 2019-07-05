package concurrent.locks.threadgate;

/**
 * @Author: kkyeer
 * @Description: 阻塞所有线程直到门开
 * @Date:Created in 22:59 2019/7/3
 * @Modified By:
 */
interface ThreadGate {
    /**
     * 等待门开
     */
    void waitDoor()  throws InterruptedException ;

    /**
     * 开门，开门后全部都可以通过
     * @return 返回当前被阻塞的线程数
     */
    int openDoor();

    /**
     * 关门，关门后，原来的线程依旧可以通过，但新请求的线程不可以
     */
    void closeDoor();
}
