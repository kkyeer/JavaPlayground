package concurrent.vehicletracker;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试跟踪器是否线程安全
 * @Date:Created in 17:06 2019/5/31
 * @Modified By:
 */

class TestCase {
    private static final int TEST_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    public static void main(String[] args) {

        Map<String, Point> initLocations = new HashMap<>();
        Tracker tracker = new UnSafeTracker(initLocations);
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            new Thread(
                    ()->{
                        while (true) {
                            Random random = new Random();
                            int sleepTime = random.nextInt(10);
                            System.out.println();
                        }
                    }
            ).start();
        }
    }
}
