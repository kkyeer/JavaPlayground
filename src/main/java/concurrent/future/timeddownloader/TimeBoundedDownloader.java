package concurrent.future.timeddownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 有时间限制的下载器，超过时间的不返回
 * @Date:Created in 11:24 2019/6/1
 * @Modified By:
 */
class TimeBoundedDownloader {
    public static void main(String[] args) {
        List<Callable<String>> tasks = new ArrayList<>();
        int size = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            new Thread(
                    ()->{
                        Callable<String> callable = () -> {
                            int milli = new Random().nextInt(100);
                            Thread.sleep(milli);
                            return ""+milli;
                        };
                        tasks.add(callable);
                        Future<String> stringFuture = executorService.submit(callable);
                        try {
                            String result = stringFuture.get(50,TimeUnit.MILLISECONDS);
                            System.out.println(result);
                        } catch (TimeoutException e) {
                            System.out.println("timeOut");
                            stringFuture.cancel(true);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            stringFuture.cancel(true);
                        }
                    }
            ).start();
        }
    }
}
