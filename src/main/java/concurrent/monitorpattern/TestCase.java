package concurrent.monitorpattern;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        Tracker tracker = new NormalSafeTracker(initLocations);
        for (int i = 0; i < 100; i++) {
            tracker.setLocation("v-" + i, new Point(i, i + 1));
        }
        for (int i = 0; i < TEST_THREAD_COUNT; i++) {
            new Thread(
                    ()->{
                        Random random = new Random();
                        int toChangeIndex = random.nextInt(100);
                        Point p = tracker.getLocation("v-" + toChangeIndex);
                        p.setLocation(1000, 1000);
                    }
            ).start();
        }
    }
}
