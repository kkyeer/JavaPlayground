package concurrent.asynctask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: kkyeer
 * @Description: 从网络加载
 * @Date:Created in 0:55 2019/5/17
 * @Modified By:
 */
class WebDataLoader {
    private FutureTask<Map<String, Object>> futureTask = new FutureTask<>(() -> {
        // 模拟网络请求
        System.out.println("Send request");
        Thread.sleep(1000);
        System.out.println("Got result");
        Map<String, Object> result = new HashMap<>();
        result.put("sum", 9999);
        return result;
    });

    public Map<String, Object> getResult() throws ExecutionException, InterruptedException {
        new Thread(futureTask).start();
        return futureTask.get();
    }

}
