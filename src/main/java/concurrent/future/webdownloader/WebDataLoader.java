package concurrent.future.webdownloader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @Author: kkyeer
 * @Description: 从网络加载
 * @Date:Created in 0:55 2019/5/17
 * @Modified By:
 */
class WebDataLoader {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);

    private class DownloadTask implements Callable<Map<String,Object>>{
        private final String url;

        DownloadTask(String url) {
            this.url = url;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Map<String, Object> call() throws Exception {
            // 模拟网络请求
            System.out.println("Send request ["+url+"]");
            Random random = new Random();
            int sleepTime = random.nextInt(1000);
            if (sleepTime >= 500) {
                throw new Exception("连接超时");
            }
            Thread.sleep(sleepTime);
            Map<String, Object> result = new HashMap<>(2);
            result.put("num1", 9999);
            result.put("num2", sleepTime);
            return result;
        }
    }

    Map<String, Object> getResult(String url) throws InterruptedException{
        Future<Map<String, Object>> futureTask = EXECUTOR_SERVICE.submit(new DownloadTask(url));
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            System.out.println("Interrupted while performing task");
            throw e;
        } catch (ExecutionException e) {
            System.out.println("Error while performing task's inner runnable");
            throw new RuntimeException(e);
        } catch (CancellationException e) {
            System.out.println("Cancelled while performing task's inner runnable");
        }
        return null;
    }

    Integer getResult(String url,Function<Map<String, Object>, Integer> function) throws InterruptedException{
        Map<String, Object> downloadResult = getResult(url);
        return function.apply(downloadResult);
    }

}
