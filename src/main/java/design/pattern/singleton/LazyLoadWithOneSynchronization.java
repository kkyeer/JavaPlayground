package design.pattern.singleton;

/**
 * @Author: kkyeer
 * @Description: 懒汉式1，在获取的方法外加单层锁，线程安全，但效率低
 * @Date:Created in 10:32 2019/6/24
 * @Modified By:
 */
class LazyLoadWithOneSynchronization implements Singleton{
    private static LazyLoadWithOneSynchronization instance;
    private LazyLoadWithOneSynchronization(){
        try {
            // 耗时的初始化操作
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public static synchronized LazyLoadWithOneSynchronization getInstance(){
        if (instance == null) {
            instance = new LazyLoadWithOneSynchronization();
        }
        return instance;
    }
}
