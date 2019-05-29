package concurrent.executor.tune;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: kkyeer
 * @Description: 限制大小的线程池
 * @Date:Created in 9:40 2019/5/29
 * @Modified By:
 */
class BoundedExecutor {
    private final Executor executor;
    private Semaphore semaphore;

    // 构造方法
    BoundedExecutor(int size) {
        this.semaphore = new Semaphore(size);
        this.executor = Executors.newFixedThreadPool(size);
    }
    // 提交任务
    void submitTask(Runnable runnable){
        try {
            this.semaphore.acquire();
            this.executor.execute(
                    ()->{
                        runnable.run();
                        this.semaphore.release();
                    }
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BoundedExecutor boundedExecutor = new BoundedExecutor(4);
        for (int i = 0; i < 10; i++) {
            boundedExecutor.submitTask(new LengthyTask(""+i));
        }
    }

}
