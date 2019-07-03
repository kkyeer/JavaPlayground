package concurrent.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 阻塞所有线程直到门开
 * @Date:Created in 22:59 2019/7/3
 * @Modified By:
 */
class ThreadGate {
    volatile int state = 0;

    /**
     * 等待门开方法
     * @throws InterruptedException 异常
     */
    synchronized void waitDoor() throws InterruptedException {
        while (state == 0) {
            wait();
        }
    }

    synchronized void  openDoor(){
        state = 1;
        notifyAll();
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGate gate = new ThreadGate();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 8; i++) {
            executorService.submit(
                    ()->{
                        try {
                            gate.waitDoor();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Gate Opened");
                    }
            );
        }
        Thread.sleep(2000);
        gate.openDoor();
    }
}
