package design.pattern.singleton;

/**
 * @Author: kkyeer
 * @Description: 普通，非线程安全的单例类，懒加载
 * @Date:Created in 10:32 2019/6/24
 * @Modified By:
 */
class PlainNotSafe implements Singleton{
    private static PlainNotSafe instance;
    private PlainNotSafe(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public static PlainNotSafe getInstance() {
        if (instance == null) {
            instance = new PlainNotSafe();
        }
        return instance;
    }
}
