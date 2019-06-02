package concurrent.classload;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: static方法修改共享的static变量的线程安全
 * @Date:Created in 19:38 2019/6/2
 * @Modified By:
 */
class StaticMethodModifyStatic {
    private static Map<Integer,Integer> map  = new HashMap<>();

    static void modifyStatic(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int rnd = map.size()+1;
        map.put(rnd, rnd);
        System.out.println(map);
    }

    private static void modifyStatic(boolean shouldBeThreadSafe){
        if (!shouldBeThreadSafe) {
            modifyStatic();
        }else {
            synchronized (ConstructorModifyStatic.class) {
                modifyStatic();
            }
        }
    }


    public static Map<Integer, Integer> getMap() {
        return map;
    }

    private static final int TEST_THREAD_COUNT = 12;

    public static void main(String[] args) {
        class TestThread implements Runnable{
            private final boolean shouldBeSafe;

            private TestThread(boolean shouldBeSafe) {
                this.shouldBeSafe = shouldBeSafe;
            }

            @Override
            public void run() {
                StaticMethodModifyStatic.modifyStatic(shouldBeSafe);
            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            executorService.execute(new TestThread(true));
        }
    }
}
