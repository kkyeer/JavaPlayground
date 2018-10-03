package effectivejava3.singleton;

/**
 * 双重检查法，线程安全，推荐
 * @author kkyeer@gmail.com
 * @date 2018/10/3 10:19
 */
public class LazySynchronizedBlock {
    private static LazySynchronizedBlock instance;
    private LazySynchronizedBlock(){}

    private static LazySynchronizedBlock getInstance() {
        if (instance == null) {
            synchronized (LazySynchronizedBlock.class) {
                if (instance == null) {
                    instance = new LazySynchronizedBlock();
                }
            }
        }
        return instance;
    }




    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(LazySynchronizedBlock.getInstance().hashCode());
                }
            }).start();
        }
    }
}
