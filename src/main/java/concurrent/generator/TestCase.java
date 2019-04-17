package concurrent.generator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 16:19 2019/4/15
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
//        Generator safe = new SafeGenerator();
//        testGenerator(safe);
        Generator unsafe = new UnsafeGenerator();
        testGenerator(unsafe);
    }

    static void testGenerator(Generator generator){
        final Map<Integer, Thread> indexThreadMap = new ConcurrentHashMap<>();
        AtomicBoolean safeFlag = new AtomicBoolean(true);
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                int index;
                boolean normalExit = true;
                while ((index=generator.getNext())<=200){
                    if (indexThreadMap.keySet().contains(index)) {
                        System.out.println(Thread.currentThread().getName()+"与"+indexThreadMap.get(index).getName()+"已重复--" + index);
                        normalExit=false;
                        safeFlag.set(false);
                        break;
                    }
                    indexThreadMap.put(index, Thread.currentThread());
                    System.out.println(Thread.currentThread().getName()+"---"+index);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName()+" went as expected:"+normalExit);
                System.out.println("测试结果，线程安全？"+safeFlag);
            }).start();
        }
    }
}
