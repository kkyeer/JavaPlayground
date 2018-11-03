package effectivejava3.lambdas;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 16:50 2018/10/28
 * @Modified By:
 */
public class TestCase {
    /**
     * 试一试自己写的非线程安全的Cache
     * @author kkyeer
     * @date 2018/10/28 11:50
     */
    void tasteCache(){
        int maxSize = 3;
        MapCacheUnsafe<String,Object> mapCacheUnsafe = new MapCacheUnsafe<>(maxSize,(oriMap, eldestEntry)-> oriMap.size()>maxSize);
        mapCacheUnsafe.put("1", "asldf");
        mapCacheUnsafe.put("2", "asldf");
        mapCacheUnsafe.put("3", "asldf");
        System.out.println("Current cache size:" + mapCacheUnsafe.size());
        mapCacheUnsafe.put("4", "asldfkj");
        System.out.println("After put fourth:" + mapCacheUnsafe);
//        Todo 线程安全测试
        System.out.println("----线程安全测试开始-------");
    }

    /**
     * 测试Cache的多线程安全性
     * @author kkyeer
     * @date 2018/10/28 16:51
     */
    void prepareUnsafeCache(int maxSize){
        MapCacheUnsafe<Integer,Object> mapCacheUnsafe = new MapCacheUnsafe<>(maxSize,(oriMap, eldestEntry)-> oriMap.size()>maxSize);
        for (int i = 0; i < maxSize; i++) {
            mapCacheUnsafe.put((mapCacheUnsafe.size()+1), "One" + (mapCacheUnsafe.size()+1));
        }
        System.out.println(mapCacheUnsafe);
    }

    /**
     * 测试Cache的多线程安全性
     * @author kkyeer
     * @date 2018/10/28 16:51
     */
    Map<Integer,Object> prepareSafeCache(int maxSize){
        MapCacheSafe<Integer,Object> mapCache = new MapCacheSafe<>(maxSize,(map)->{
            if(map.size()>maxSize){
                map.remove(Collections.min(map.keySet()));
            }
        });
        for (int i = 0; i < maxSize; i++) {
            mapCache.put((mapCache.size()+1), "One" + (mapCache.size()+1));
        }
        System.out.println("Done prepared:" + mapCache);
        return mapCache;
    }

    Map<Integer,Object> prepareConcurrent(int maxSize){
        ConcurrentHashMap<Integer,Object> mapCache = new ConcurrentHashMap<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            mapCache.put((mapCache.size()+1), "One" + (mapCache.size()+1));
        }
        System.out.println("Done prepared:" + mapCache);
        return mapCache;
    }

    Map<Integer,Object> prepareSyncronizedMap(int maxSize){
        Map<Integer,Object> mapCache = Collections.synchronizedMap(new HashMap<>(maxSize));
        for (int i = 0; i < maxSize; i++) {
            mapCache.put((mapCache.size()+1), "One" + (mapCache.size()+1));
        }
        System.out.println("Done prepared:" + mapCache);
        return mapCache;
    }

    Map<Integer,Object> prepareNormalMap(int maxSize){
        Map<Integer,Object> mapCache = new HashMap<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            mapCache.put((mapCache.size()+1), "One" + (mapCache.size()+1));
        }
        System.out.println("Done prepared:" + mapCache);
        return mapCache;
    }

    void testSafety(Map<Integer,Object> mapCache,int repeatTimes) throws InterruptedException {
        final int MAX_THREAD_COUNT = 10;
        long start = System.currentTimeMillis();
//        使用ExecutorService来管理线程
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        for (int i = 0; i < repeatTimes; i++) {
            executorService.execute(new TestThread(mapCache,i*repeatTimes,repeatTimes));
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("-------All Done------");
//        System.out.println(mapCache);
        System.out.println(mapCache.size());
        System.out.println("Time Spent:"+(System.currentTimeMillis()-start)+"ms");

    }

    class TestThread implements Runnable{
        private Map<Integer,Object> map;
        private int start;
        private int repeatCount;

        public TestThread(Map<Integer, Object> map, int start, int repeatCount) {
            this.map = map;
            this.start = start;
            this.repeatCount = repeatCount;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            for (int i = start; i < start+repeatCount; i++) {
                Integer toPutValue = i;
//                System.out.println("before:"+Thread.currentThread().getName()+":  "+toPutValue);
                map.put(toPutValue, "Two+" + toPutValue);
//                System.out.println("after:"+Thread.currentThread().getName()+":  "+map.size());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestCase ts = new TestCase();
        Map<Integer,Object> testMap;
        testMap = ts.prepareConcurrent(0);
//        Unsafe
//        ts.testSafety(test);
//        Unsafe
//        testMap = ts.prepareSafeCache(0);

        ts.testSafety(testMap,1000);
        testMap = ts.prepareSyncronizedMap(0);
        ts.testSafety(testMap,1000);
    }
}
