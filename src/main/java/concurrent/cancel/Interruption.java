package concurrent.cancel;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: kkyeer
 * @Description: 通过调用Interrupt方法来终止任务
 * @Date:Created in 21:32 2019/5/21
 * @Modified By:
 */
class Interruption extends Thread{
    public static void main(String[] args) throws InterruptedException {
        Interruption runningThread = new Interruption();
        runningThread.start();
        Thread.sleep(1000);
        System.out.println("shutting down:" + System.currentTimeMillis());
        runningThread.cancel();
    }

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
            while (!Thread.currentThread().isInterrupted()) {
                // some repeat work  ----> 可用
                System.out.println("running");
                // blocking work,may be blocked for some reason
                if(!Thread.currentThread().isInterrupted()){
                    testQueue.put("hh");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupted:"+System.currentTimeMillis());
    }

    private void cancel(){
        this.interrupt();
    }
}
