package concurrent.asynctask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

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
        result.put("num1", 9999);
        result.put("num2", 8888);
        return result;
    });

    public Map<String, Object> getResult() throws ExecutionException, InterruptedException {
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public Map<String, Object> getResult(Function<Map<String,Object>,Map<String,Object>> function) throws ExecutionException, InterruptedException {
        new Thread(futureTask).start();
        Map<String, Object> result = futureTask.get();
        return function.apply(result);
    }

}
