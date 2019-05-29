package concurrent.executor.tune;

import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 耗时的任务
 * @Date:Created in 17:55 2019/5/28
 * @Modified By:
 */
public class LengthyTask implements Runnable {
    private final String name;

    public LengthyTask(String name) {
        this.name = name;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("Thread " + name + " running");
        Random random = new Random();
        int sleepTime = random.nextInt(2000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + name + " end");
    }
}
