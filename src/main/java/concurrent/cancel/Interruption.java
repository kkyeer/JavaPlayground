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
                System.out.println("running");
                try {
                    testQueue.put("hh");
                } catch (InterruptedException e) {
                    // 打印这段话，说明被阻塞的put方法会探测到interrupted状态并主动抛出异常
                    System.out.println("catch InterruptedException in blocked put process");
                    throw e;
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        System.out.println("interrupted:"+System.currentTimeMillis());
    }

    private void cancel(){
        this.interrupt();
    }
}
