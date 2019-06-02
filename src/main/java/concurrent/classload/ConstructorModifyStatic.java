package concurrent.classload;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 构造器修改共享的static变量
 * @Date:Created in 18:02 2019/6/2
 * @Modified By:
 */
class ConstructorModifyStatic {
    private static Map<Integer,Integer> map;

    ConstructorModifyStatic(boolean shouldBeThreadSafe){
        if (!shouldBeThreadSafe) {
            modifyStatic();
        }else {
            synchronized (ConstructorModifyStatic.class) {
                modifyStatic();
            }
        }
    }

    private static void modifyStatic(){
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int rnd = map.size()+1;
        map.put(rnd, rnd);
        System.out.println(map);
    }


    public static Map<Integer, Integer> getMap() {
        return map;
    }

    private static final int TEST_THREAD_COUNT = 12;

    public static void main(String[] args) {
        class TestThread implements Runnable{
            private final boolean shouldBeSafe;

            TestThread(boolean shouldBeSafe) {
                this.shouldBeSafe = shouldBeSafe;
            }

            @Override
            public void run() {
                new ConstructorModifyStatic(shouldBeSafe);
            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            executorService.execute(new TestThread(true));
        }
    }
}
