package concurrent.cancel;

import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 使用ExecutorService的schedule方法写一个定时结束任务的方法
 * @Date:Created in 22:18 2019/5/21
 * @Modified By:
 */
class TimedRun{
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

    static void cancelTimeout(Runnable runnable,long timeout,TimeUnit timeUnit){
        Thread currentThread = Thread.currentThread();
        executorService.schedule(()-> currentThread.interrupt(), timeout, timeUnit);
        runnable.run();
    }

    public static void main(String[] args) {
        final BlockingDeque<String> testQueue = new LinkedBlockingDeque<>(1);
        class Test implements Runnable{
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                System.out.println("run start:" + start);
                try {
                    while (true){
                        testQueue.put("hh");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("run over in:" + (System.currentTimeMillis() - start));
            }
        }
        TimedRun.cancelTimeout(new Test(),20,TimeUnit.SECONDS);
    }
}
