package concurrent.executor.tune;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: CoreSize=0的线程池执行完毕后自动退出
 * @Date:Created in 17:15 2019/5/28
 * @Modified By:
 */
class AutoQuitExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executorService.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 6; i++) {
            executorService.submit(new LengthyTask(""+i));
        }
    }
}
