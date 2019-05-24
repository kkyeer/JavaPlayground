package concurrent.cancel;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: kkyeer
 * @Description: 通过轮询来终止任务
 * @Date:Created in 21:32 2019/5/21
 * @Modified By:
 */
class PollState extends Thread{

    public static void main(String[] args) throws InterruptedException {
        PollState runningThread = new PollState();
        runningThread.start();
        Thread.sleep(1000);
        System.out.println("shutting down:" + System.currentTimeMillis());
        runningThread.cancel();
    }

    volatile private boolean canceled = false;

    private BlockingDeque<String> testQueue = new LinkedBlockingDeque<>(1);

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
        try {
            while (!canceled) {
                // some repeat work  ----> 可用
                System.out.println("running");
                // blocking work,may be blocked for some reason
                if(!canceled){
                    testQueue.put("hh");
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        System.out.println("canceled:"+System.currentTimeMillis());
    }

    private void cancel(){
        this.canceled =true;
    }
}
