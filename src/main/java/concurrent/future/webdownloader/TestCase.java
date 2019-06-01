package concurrent.future.webdownloader;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 1:02 2019/5/17
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args){
        WebDataLoader loader = new WebDataLoader();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Function<Map<String, Object>, Integer> reduceFunction = map -> {
            Optional<Object> result = map.values().parallelStream().reduce((a, b)->(Integer)a+(Integer)b);
            return (Integer)result.get();
        };
        for (int i = 0; i < 4; i++) {
            String url = "URL___"+i;
            executorService.execute(
                    ()->
                    {
                        try {
                            System.out.println(loader.getResult(url,reduceFunction));
                        } catch (Exception e) {
                            System.out.println("No result:"+e.getMessage());
                        }
                    }
            );
        }

    }
}
