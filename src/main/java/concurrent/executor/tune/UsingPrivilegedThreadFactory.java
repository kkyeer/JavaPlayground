package concurrent.executor.tune;

import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 带权限的线程工厂类
 * @Date:Created in 9:52 2019/5/29
 * @Modified By:
 */
class UsingPrivilegedThreadFactory {
    public static void main(String[] args) {
        ThreadFactory threadFactory = Executors.privilegedThreadFactory();
        Executor executor = new ThreadPoolExecutor(0, 1, 1, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
        executor.execute(
                ()->{
                    Thread thread = Thread.currentThread();
                    System.out.println(thread.getContextClassLoader());
                }
        );
    }
}
