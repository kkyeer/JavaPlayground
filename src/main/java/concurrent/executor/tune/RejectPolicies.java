package concurrent.executor.tune;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kkyeer
 * @Description: 拒绝任务的策略
 * @Date:Created in 17:47 2019/5/28
 * @Modified By:
 */
class RejectPolicies {
    public static void main(String[] args) {
        // 1. 丢弃并抛出异常
//        test(new ThreadPoolExecutor.AbortPolicy());
        // Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task concurrent.executor.tune.LengthyTask@5e2de80c rejected from java.util.concurrent.ThreadPoolExecutor@1d44bcfa[Running, pool size = 4, active threads = 4, queued tasks = 4, completed tasks = 0]

        // 2. 退回到调用线程
//        test(new ThreadPoolExecutor.CallerRunsPolicy());
        // 中间有main running/main end 也就是说，没执行的任务被推回到主线程

        // 3. 丢弃最老的,对于FIFO队列，表示丢弃下一个可能执行的任务
//        test(new ThreadPoolExecutor.DiscardOldestPolicy());
        // 运行结果中，只有8个running，中间某些任务没有运行，直接被丢弃

        // 4. 静默丢弃
        test(new ThreadPoolExecutor.DiscardPolicy());
        // 运行结果中，只有8个running，9和10号直接被丢弃没有异常抛出
    }

    private static void test(RejectedExecutionHandler handler) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(4));
        executor.setRejectedExecutionHandler(handler);
        for (int i = 0; i < 10; i++) {
            executor.execute(new LengthyTask(""+i));
        }
    }
}
