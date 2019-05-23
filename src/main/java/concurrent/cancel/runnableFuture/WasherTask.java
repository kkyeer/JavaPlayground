package concurrent.cancel.runnableFuture;

import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @Author: kkyeer
 * @Description: 洗碗工
 * @Date:Created in 15:40 2019/5/23
 * @Modified By:
 */
abstract class WasherTask<T> implements CancellableTask<T> {
    /**
     * 取消任务
     *
     * @return 取消请求是否发送成功
     */
    @Override
    public boolean cancel() {
        System.out.println("取消中");
        return true;
    }

    /**
     * 新任务
     *
     * @return
     */
    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    WasherTask.this.cancel();
                } finally {
                    return super.cancel(true);
                }
            }
        };
    }
}
