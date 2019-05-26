package concurrent.cancel.afterexecute;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kkyeer
 * @Description: 当执行完成后，打印日志
 * @Date:Created in 17:01 2019/5/26
 * @Modified By:
 */
class LogAfterShutdownThreadPoolExecutor extends ThreadPoolExecutor {
    public LogAfterShutdownThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        StringBuilder stringBuilder = new StringBuilder(Thread.currentThread().getName());
        stringBuilder.append(",任务执行完成");
        if (t != null) {
            stringBuilder.append(",有异常："+t.getMessage());
        }
        System.out.println(stringBuilder.toString());
    }
}
