package concurrent.serialgenerator;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 16:19 2019/4/15
 * @Modified By:
 */
class TestCase {
    /**
     * 测试线程数
     */
    private static final int TEST_THREAD_SIZE = 4;

    /**
     * 测试用的序列最大值
     */
    private static final int TEST_MAX_INDEX_BOUNDS = 1000;

    public static void main(String[] args) {
        System.out.println("Testing unsafe generator");
        SerialGenerator unsafeSerialGenerator = new UnsafeSerialGenerator();
        testGenerator(unsafeSerialGenerator);
        System.out.println("--------------------------");
        System.out.println("Testing safe generator");
        SerialGenerator safe = new SafeSerialGenerator();
        testGenerator(safe);
    }

    static void testGenerator(SerialGenerator serialGenerator){
        // 记录所有生成的索引，本身线程安全
        final Set<Integer> serialSet = new ConcurrentSkipListSet<>();
        // 控制所有线程启动完成的CountDownLatch，数量等于测试用的线程数
        final CountDownLatch startControlCountDownLatch = new CountDownLatch(1);
        // 控制所有线程运行完成的CountDownLatch，数量等于测试用的线程数
        final CountDownLatch runCompleteCountDownLatch = new CountDownLatch(TEST_THREAD_SIZE);
        AtomicBoolean threadSafe = new AtomicBoolean(true);
        for (int i = 0; i < TEST_THREAD_SIZE; i++) {
            new Thread(()->{
                try {
                    // 等所有线程全部初始完成一起启动
                    startControlCountDownLatch.await();
                    int serial;
                    while ((serial = serialGenerator.getNext()) <= TEST_MAX_INDEX_BOUNDS) {
                        if (serialSet.contains(serial)) {
                            System.out.println("Serial already exists:"+serial);
                            threadSafe.set(false);
                        }
                        serialSet.add(serial);
                        Thread.sleep(1);
                    }
                    // 线程运行完成，结束倒数计数减1
                    runCompleteCountDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        // 所有线程初始化完毕，所以倒数计数减1
        startControlCountDownLatch.countDown();

        try {
            runCompleteCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Result: generator thread safe?"+threadSafe.get());
    }
}
