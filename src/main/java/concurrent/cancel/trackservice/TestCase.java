package concurrent.cancel.trackservice;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 11:13 2019/5/25
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) throws InterruptedException {
        TrackingExecutorService trackingExecutorService = new TrackingExecutorService(Executors.newFixedThreadPool(4));
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(8);
        for (int i = 0; i < 4; i++) {
            trackingExecutorService.execute(
                    ()->{
                        while (true) {
                            try {
                                blockingQueue.put("hh");
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
//                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
            );
        }
        Thread.sleep(2000);
        List<Runnable> runnableList = trackingExecutorService.shutdownNow();
        System.out.println("canceled:" + runnableList);
    }
}
