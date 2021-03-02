package concurrent.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 源码
 * @Date:Created in 3:42 PM 2021/3/1
 * @Modified By:
 */
public class ExploreThreadPool {
    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger(0);
        ExecutorService executorService = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1),
                r -> new Thread(r, "T-"+index.addAndGet(1)),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (int i = 0; i < 10; i++) {
            executorService.submit(
                    ()->{
                        try {
                            System.out.println(Thread.currentThread().getName()+" executing");
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

    }
}
