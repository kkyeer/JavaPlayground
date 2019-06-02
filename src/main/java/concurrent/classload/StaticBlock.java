package concurrent.classload;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 测试静态代码块
 * @Date:Created in 17:52 2019/6/2
 * @Modified By:
 */
class StaticBlock {
    private static Map<Integer,Integer> map;

    static {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map = new HashMap<>();
        int rnd = new Random().nextInt(10);
        map.put(rnd, rnd);
    }

    public static Map<Integer, Integer> getMap() {
        return map;
    }
    // ------------------------------TEST CASE---------------------------------------

    private static final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            executorService.execute(
                    ()->{
                        System.out.println(StaticBlock.getMap());
                    }
            );
        }
    }
}
