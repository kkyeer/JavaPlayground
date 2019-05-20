package concurrent.barrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: kkyeer
 * @Description: 模拟攻城，约定当四个城门都被打破才算成功，然后继续去打下一座城
 * @Date:Created in 21:24 2019/5/17
 * @Modified By:
 */
public class StormCastles {
    private static final int CITY_COUNT = 3;
    private static final int WALL_COUNT = 4;
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(4);
        CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < WALL_COUNT; i++) {
            new Thread(
                    ()->{
                        try {
                            startLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (int j = 0; j < CITY_COUNT; j++) {
                            System.out.println("军队准备攻击城市："+j);
                            // 模拟攻城的时间
                            int countDownTime = new Random().nextInt(3000)+3000;
                            try {
                                Thread.sleep(countDownTime);
                                System.out.println("军队攻击完成城市："+j);
                                barrier.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
        System.out.println("开始攻城:");
        startLatch.countDown();
    }

}
