package concurrent.locks;

import concurrent.ConcurrentTest;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: kkyeer
 * @Description: 使用可外部打断的锁,模拟一个定时器线程，当被调用时，新增一个xxx毫秒的定时器，但是新增定时器有消耗，若时间大于传入的毫秒数，则定时器创建失败
 * @Date:  Created in 9:58 2019/7/2
 * @Modified By:
 */
class TasteTimeLimitLock {
    static class MyTimer extends TimerTask{
        private final int time;

        MyTimer(int time) {
            this.time = time;
        }

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            System.out.println("Tick:" + time);
        }
    }
    public static void main(String[] args) {
        BlockingQueue<Integer> timerQueue = new LinkedBlockingQueue<>();
        new Thread(
                ()->{
                    Random random = new Random();
                    while (true) {
                        int next = random.nextInt(1000) + 100;
                        timerQueue.add(next);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        ReentrantLock reentrantLock = new ReentrantLock();
        ConcurrentTest.test(
                ()->{
                    while (true) {
                        int time;
                        try {
                            time = timerQueue.take();
                        } catch (InterruptedException e) {
                            System.out.println("Error while getting next time");
                            e.printStackTrace();
                            return;
                        }
//                        System.out.println(Thread.currentThread().getId() + "Got time:" + time);

                        try {
                            if (reentrantLock.tryLock(time, TimeUnit.MILLISECONDS)) {
                                Timer timer = new Timer();
                                timer.schedule(new MyTimer(time),time);

                                try {
                                    Thread.sleep(time);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
//                                System.out.println(Thread.currentThread().getId() + "Unlock:" + time);
                                reentrantLock.unlock();
                            }else {
                                System.out.println("Lock failed");
                                try {
                                    Thread.sleep(time);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
