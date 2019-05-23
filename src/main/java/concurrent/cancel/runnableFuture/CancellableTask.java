package concurrent.cancel.runnableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @Author: kkyeer
 * @Description: 可取消任务接口
 * @Date:Created in 10:34 2019/5/22
 * @Modified By:
 */
interface CancellableTask<T> extends Callable<T> {
    /**
     * 取消任务
     * @return 取消请求是否发送成功
     */
    boolean cancel();

    /**
     * 新任务
     * @return
     */
    RunnableFuture<T> newTask();
}
