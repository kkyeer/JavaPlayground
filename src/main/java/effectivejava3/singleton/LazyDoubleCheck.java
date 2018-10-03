package effectivejava3.singleton;

import javax.swing.*;

/**
 * 线程安全但效率极低，多线程下每次获取实例都要抢占锁，成为性能瓶颈，不推荐
 * @author kkyeer@gmail.com
 * @date 2018/10/3 10:19
 */
public class LazyDoubleCheck {
    private static volatile LazyDoubleCheck instance;
    private LazyDoubleCheck(){}

    private static LazyDoubleCheck getInstance() {
        synchronized (LayoutFocusTraversalPolicy.class) {
            if (instance == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instance = new LazyDoubleCheck();
            }
        }
        return instance;
    }




    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(LazyDoubleCheck.getInstance().hashCode());
                }
            }).start();
        }
    }
}
