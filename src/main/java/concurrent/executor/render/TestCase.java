package concurrent.executor.render;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 16:03 2019/5/20
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) {
//        test(new SequentialRenderer());
//        test(new ConcurrentRenderer());
//        test(new ConcDownInstantRenderer());
        test(new TimeLimitConcurrentRenderer());
    }

    private static void test(Renderer renderer) {
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            textList.add("TEXT:" + i);
        }
        List<String> imageList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            imageList.add("IMAGE:" + i);
        }
        long sum = 0;
        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            renderer.render(textList, imageList);
            long duration = System.currentTimeMillis() - startTime;
            sum+=duration;
            System.out.println("duration:" + duration);
        }

        System.out.println("avg:" + sum/5);
    }
}
