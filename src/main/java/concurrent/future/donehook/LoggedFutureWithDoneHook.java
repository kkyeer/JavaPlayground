package concurrent.future.donehook;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: kkyeer
 * @Description: 当FutureTask执行完成时，记录日志
 * @Date:Created in 16:45 2019/6/3
 * @Modified By:
 */
class LoggedFutureWithDoneHook<T>  extends FutureTask<T> {
    LoggedFutureWithDoneHook(Callable<T> callable) {
        super(callable);
    }
    LoggedFutureWithDoneHook(Runnable runnable, T result) {
        super(runnable, result);
    }

    @Override
    protected void done() {
        super.done();
        System.out.println("Log:"+Thread.currentThread().getName()+" done!");
    }
}
