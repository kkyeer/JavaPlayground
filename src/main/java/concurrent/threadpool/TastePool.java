package concurrent.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 试用线程池
 * @Date:Created in 下午6:04 2021/4/4
 * @Modified By:
 */
public class TastePool {
    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1), r -> new Thread(r, "Thread-" + index.getAndIncrement()), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(
                    ()->{
                        System.out.println(Thread.currentThread().getName() + " running");
                        try {
                            TimeUnit.HOURS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }
}
