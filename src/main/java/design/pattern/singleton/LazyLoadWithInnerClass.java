package design.pattern.singleton;

import sun.security.jca.GetInstance;

/**
 * @Author: kkyeer
 * @Description: 懒汉式3，使用内部类来进行懒加载，原理是内部类初始化时，使用
 * @Date:Created in 14:57 2019/6/24
 * @Modified By:
 */
class LazyLoadWithInnerClass implements Singleton{
    private static LazyLoadWithInnerClass instance;
    private LazyLoadWithInnerClass(){
        try {
            // 耗时的初始化操作
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Inner{
        static LazyLoadWithInnerClass instance = new LazyLoadWithInnerClass();
    }

    /**
     *
     * @return
     */
    public static LazyLoadWithInnerClass getInstance(){
        return Inner.instance;
    }
}
