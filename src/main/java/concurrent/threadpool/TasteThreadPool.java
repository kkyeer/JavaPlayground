package concurrent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 2:08 PM 2021/4/8
 * @Modified By:
 */
public class TasteThreadPool {
    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger();
        Executor executor = new ThreadPoolExecutor(
                2,
                4,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                r -> new Thread(r, "TP" + index.getAndIncrement()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        for (int i = 0; i < 10; i++) {
            executor.execute(
                    ()->{
                        System.out.println("Thread " + Thread.currentThread().getName() + " RUNNING");
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
