package concurrent.asynctask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 1:02 2019/5/17
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebDataLoader loader = new WebDataLoader();
        Function<Map<String, Object>, Map<String, Object>> function = map -> {
            AtomicInteger sum = new AtomicInteger(0);
            map.values().forEach(
                    val -> {sum.addAndGet((Integer) val);}
            );
            Map<String, Object> sumResult = new HashMap<>();
            sumResult.put("sum", sum.get());
            return sumResult;
        };
        System.out.println(loader.getResult());
        System.out.println(loader.getResult(function));
    }
}
