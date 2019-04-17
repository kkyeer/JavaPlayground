package concurrent.countdown;

/**
 * @Author: kkyeer
 * @Description: 测试类
 * @Date:Created in 23:29 2019/4/17
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
//        testCountDown(new NotThreadsafeCountDown());
        testCountDown(new ThreadSafeCountDown());
    }

    static void testCountDown(CountDown countDown) {
        final int testThreadCount = 4;
        for (int i = 0; i < testThreadCount; i++) {
            new Thread(()->{
                int value;
                while ((value = countDown.countdown()) >= 0) {
                    System.out.println(value);
                }

            }).start();
        }
    }
}
