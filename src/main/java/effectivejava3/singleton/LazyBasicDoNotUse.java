package effectivejava3.singleton;

/**
 * 懒汉式单例模式的基础，按需加载，但线程不安全
 * @author kkyeer@gmail.com
 * @date 2018/10/3 10:19
 */
public class LazyBasicDoNotUse {
    private static LazyBasicDoNotUse instance;
    private LazyBasicDoNotUse() {

    }

    private static LazyBasicDoNotUse getInstance() {
        if (instance == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new LazyBasicDoNotUse();
        }
        return instance;
    }




    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(LazyBasicDoNotUse.getInstance().hashCode());
                }
            }).start();
        }
    }
}
