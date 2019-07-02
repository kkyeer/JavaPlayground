package concurrent.locks;

import concurrent.ConcurrentTest;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 通过单一信道发送信息
 * @Date:Created in 9:49 2019/6/29
 * @Modified By:
 */
class MessageSendingOnSingleChannel {
    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Messaging on Single Channel");
        ReentrantLock reentrantLock = new ReentrantLock();
        ConcurrentTest.test(
                ()->{
                    try {
                        Thread currentThread = Thread.currentThread();
                        while (!currentThread.isInterrupted()){
                            if(reentrantLock.tryLock(1000, TimeUnit.MILLISECONDS)){
                                int rnd = random.nextInt(1000);
                                System.out.print(currentThread.getId() + ":" + rnd);
                                try {
                                    Thread.sleep(rnd);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("--unlocked");
                            }else {
                                System.out.println(currentThread.getId() + "--discard");
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        System.out.println("discard");
                    }
                }
        );
    }
}
