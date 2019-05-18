package concurrent.cache;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: kkyeer
 * @Description: 测试缓存
 * @Date:Created in 16:41 2019/5/18
 * @Modified By:
 */
class TestCase {
    private static final int MAX_TEST_THREAD_COUNT = 4;
    private static void test(int count){
        Computable<Integer,Integer> computation = new ExpensiveComputation();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(MAX_TEST_THREAD_COUNT);
        HighEffCache<Integer, Integer> highEffCache = new HighEffCache<>(computation);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX_TEST_THREAD_COUNT; i++) {
            new Thread(
                    ()->{
                        try {
                            startLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int times = 0;
                        Random random =new Random();
                        while (times < count) {
                            int input = random.nextInt(5);
                            highEffCache.compute(input);
                            times++;
                        }
                        endLatch.countDown();
                    }
            ).start();
        }
        startLatch.countDown();
        try {
            endLatch.await();
            long duration = System.currentTimeMillis()-startTime;
            System.out.println("Count:"+count+",time:"+duration+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test(1000);
        test(10000);
        test(100000);
        test(1000000);
    }
}
