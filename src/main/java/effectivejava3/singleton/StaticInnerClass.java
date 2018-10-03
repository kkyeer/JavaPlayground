package effectivejava3.singleton;

/**
 * 静态内部类，懒加载+线程安全+高效，推荐
 * @author kkyeer@gmail.com
 * @date 2018/10/3 11:05
 */
public class StaticInnerClass {
    private StaticInnerClass(){}
    private static class Inner {
        private static final StaticInnerClass INSTANCE = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return Inner.INSTANCE;
    }
}
