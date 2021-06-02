package issue.threadstarvation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 线程池内部Task提交异步线程到本线程池导致的死锁
 * @Date:Created in 4:20 PM 2021/5/20
 * @Modified By:
 */
public class ThreadStarvation {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ThreadStarvation().test();
    }

    private ExecutorService executorService = new ThreadPoolExecutor(10, 10,1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100),Thread::new,
            new ThreadPoolExecutor.CallerRunsPolicy());

    public void test() throws ExecutionException, InterruptedException {
        List<Future<String>> outerFutureList = new ArrayList<>(10);
        for (int i = 0; i < 11; i++) {
            outerFutureList.add(
                    executorService.submit(
                            new BadTask(executorService)
                    )
            );
        }
        for (Future<String> outerFuture : outerFutureList) {
            System.out.println(outerFuture.get());
        }
        executorService.shutdownNow();
        System.out.println("All good");
    }

    private static class BadTask implements Callable<String>{
        private final ExecutorService executorService;

        private BadTask(ExecutorService executorService) {
            this.executorService = executorService;
        }

        @Override
        public String call() throws InterruptedException {
//            rpc等耗时操作
            TimeUnit.MILLISECONDS.sleep(10L);
            List<Future<String>> innerFutureList = new ArrayList<>(5);
//            因为有些SDK的接口有参数数量限制，所以多次调用，为提高RT，复用线程池并发调用
            for (int i = 0; i < 5; i++) {
                innerFutureList.add(
                        this.executorService.submit(
                                ()->{
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(20L);
                                        return "OK";
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }
                        )
                );
            }
            try {
                for (Future<String> innerFuture : innerFutureList) {
                    innerFuture.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return "OK";
        }
    }
}
