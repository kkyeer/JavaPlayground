package concurrent.cancel.afterexecute;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 试一试ThreadPool的AfterExecute
 * @Date:Created in 16:55 2019/5/26
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new LogAfterShutdownThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4));
        for (int i = 0; i < 4; i++) {
            executorService.execute(
                    ()->{
                        System.out.println(Thread.currentThread().getName() + "运行中");
                        Random random = new Random();
                        int dice = random.nextInt(5);
                        if (dice > 3) {
                            throw new RuntimeException("too small,lose");
                        }
                    }
            );
        }
    }
}
