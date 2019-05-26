package concurrent.cancel.exceptionhandler;

import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 试一试Thread的ExceptionHandler
 * @Date:Created in 16:21 2019/5/26
 * @Modified By:
 */
class TestCase {
    /**
     * 测试用线程，抛出运行时异常以模拟运行时抛出异常的情况
     */
    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("开始运行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }

    /**
     * 测试用线程，抛出运行时异常以模拟处理异常过程中，再次发生异常的过程
     */
    static class MyHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName() + " 碰到问题了:");
            e.printStackTrace();
            throw new RuntimeException("处理异常过程中出错");
        }
    }

    /**
     * 设置默认handler
     * @param handler 默认handler
     */
    static void setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
        new Thread(new MyThread()).start();
    }

    /**
     * 单个线程
     */
    static void setIndividualThread(Thread.UncaughtExceptionHandler handler){
        Thread thread = new Thread(new MyThread());
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
    }

    /**
     * 设置Executor里的默认异常处理器
     * @param handler 处理器
     */
    private static void inExecutor(Thread.UncaughtExceptionHandler handler) {
        ThreadFactory threadFactory = r -> {
            Thread.setDefaultUncaughtExceptionHandler(handler);
            return new Thread(r);
        };
        Executor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), threadFactory);
        for (int i = 0; i < 4; i++) {
             executor.execute(new MyThread());
        }
    }




    public static void main(String[] args) {
//        setIndividualThread(new MyHandler());
//        setDefaultUncaughtExceptionHandler(new MyHandler());
        inExecutor(new MyHandler());
    }
}
