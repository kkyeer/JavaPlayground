package concurrent.lazyinit;

import concurrent.annotations.NotThreadSafe;

/**
 * @Author: kkyeer
 * @Description: 非线程安全的类
 * @Date:Created in 22:53 2019/4/17
 * @Modified By:
 */
@NotThreadSafe
public class LazyInitRace {
    private static LazyInitRace instance;
    private LazyInitRace(){
        System.out.println("Created an instance");
    }

    public static LazyInitRace getInstance(){
        if (instance == null) {
            instance = new LazyInitRace();
        }
        return instance;
    }
}
