package taste.jdk.thread.join;

/**
 * @Author: kkyeer
 * @Description: 试一下Thread类的join方法
 * @Date:Created in 0:45 2019/5/28
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                ()->{
                    try {
                        Thread.sleep(5000);
                        System.out.println("alskdjf");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        );
        thread.start();
        thread.join();
        System.out.println("main thread executing");
    }
}
