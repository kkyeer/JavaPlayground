package design.pattern.singleton;

/**
 * @Author: kkyeer
 * @Description: 饿汉式，在类初始化前加载，好处是保证线程安全且实现简单，坏处是，此实例可能不需要用到
 * @Date:Created in 10:32 2019/6/24
 * @Modified By:
 */
class LoadAhead implements Singleton{
    private static LoadAhead instance = new LoadAhead();
    private LoadAhead(){
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
    public static LoadAhead getInstance(){
        return instance;
    }
}
