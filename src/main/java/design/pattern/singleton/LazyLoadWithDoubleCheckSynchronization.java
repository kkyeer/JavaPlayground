package design.pattern.singleton;

/**
 * @Author: kkyeer
 * @Description: 懒汉式2，在获取的方法内做双重判断，兼顾多线程安全和效率
 * @Date:Created in 10:32 2019/6/24
 * @Modified By:
 */
class LazyLoadWithDoubleCheckSynchronization implements Singleton{
    private static LazyLoadWithDoubleCheckSynchronization instance;
    private LazyLoadWithDoubleCheckSynchronization(){
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
    public static LazyLoadWithDoubleCheckSynchronization getInstance(){
        if (instance == null) {
            synchronized (LazyLoadWithDoubleCheckSynchronization.class) {
                if (instance == null) {
                    instance = new LazyLoadWithDoubleCheckSynchronization();
                }
            }
        }
        return instance;
    }
}
