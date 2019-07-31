package concurrent.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 试用下ThreadLocal
 * @Date:Created in 11:02 2019/7/30
 * @Modified By:
 */
class TasteThreadLocal {
    public static void main(String[] args) {
        AtomicInteger id = new AtomicInteger(0);
        int processorCount = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < processorCount; i++) {
            new Thread(
                    () -> {
                        ThreadLocal<Integer> index = new ThreadLocal<>();
                        index.set(id.getAndIncrement());
                        System.out.println(Thread.currentThread().getId() + ",atomic id " + index.get());
                    }
            ).start();
        }
    }
}
