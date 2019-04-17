package concurrent.lazyinit;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 23:03 2019/4/17
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        LazyInitRace lazyInitRace;
        Thread[] allThreads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            allThreads[i] = new Thread(()->{
                LazyInitRace.getInstance();
            });
        }
        for (int i = 3; i >= 0; i--) {
            allThreads[i].start();
        }

        // print >1 then prove non thread safe
    }
}
