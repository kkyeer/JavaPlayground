package design.pattern.observer;

import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 17:46 2019/5/27
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        DataCenter dataCenter = new DataCenter();
        runMonitor(new ResourceMonitor(),dataCenter);
        runMonitor(new ProgressMonitor(),dataCenter);
    }

    private static void runMonitor(AbstractMonitor monitor,DataCenter dataCenter) {
        Random random = new Random();
        new Thread(()->{
            monitor.setDataSource(dataCenter);
            monitor.start();
            try {
                Thread.sleep(random.nextInt(5000));
                monitor.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
