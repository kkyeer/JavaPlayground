package concurrent.cancel.shutdown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 10:53 2019/5/24
 * @Modified By:
 */
class TestCase {
    private static final int TEST_THREAD_COUNT = 4;

    public static void main(String[] args) throws InterruptedException {
//        LogWriter logWriter = new DesertLogOnExitLogWriter();
        LogWriter logWriter = new PrintBlockedOnExitLogWriter();
        test(logWriter);

    }

    static void test(LogWriter logWriter) throws InterruptedException {
        logWriter.start();
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        AtomicInteger logCount = new AtomicInteger(0);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            new Thread(()->{
                while (true){
                    try {
                        Thread.sleep(100);
                        logWriter.append(Thread.currentThread().getName() + ":" + logCount.addAndGet(1));
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        break;
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println("------------------log count should be-----------:"+logCount.get());
        logWriter.shutdown();
        executorService.shutdown();
    }
}
