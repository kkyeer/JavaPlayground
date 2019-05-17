package concurrent.boundedset;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: kkyeer
 * @Description: 测试有上界的Set集合
 * @Date:Created in 20:27 2019/5/17
 * @Modified By:
 */
public class TestCase {
    private static final int TEST_THREAD_COUNT = 4;
    public static void main(String[] args) {
        // 运行以后可以观察到，在boundHashSet达到上限后，必须取出一个才能再放进一个，即有界集合
        CountDownLatch startLatch = new CountDownLatch(1);
        BoundedHashSet<Integer> boundedHashSet = new BoundedHashSet<>(20);
        BlockingDeque<Integer> added = new LinkedBlockingDeque<>();
        Random random = new Random();
        // 开一些线程往里放
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            new Thread(
                    ()->{
                        while (true){
                            int nextInt = 1000 + random.nextInt(1000);
                            boundedHashSet.add(nextInt);
                            added.add(nextInt);
                            System.out.println("added:" + nextInt + ",size:" + boundedHashSet.getSize());
                            try {
                                Thread.sleep(nextInt);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).start();
        }
        // 开一些线程往外拿
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            new Thread(
                    ()->{
                        while (true){
                            int toRemove = added.remove();
                            boundedHashSet.remove(toRemove);
                            System.out.println("removed 1 number");
                            int nextInt = 5000 + random.nextInt(3000);
                            try {
                                Thread.sleep(nextInt);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).start();
        }
        startLatch.countDown();
    }
}
