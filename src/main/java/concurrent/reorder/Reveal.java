package concurrent.reorder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 展示指令重排序导致的结果：可能输出根据源代码无法推导的结果
 * @Date:Created in 17:16 2019/7/7
 * @Modified By:
 */
class Reveal extends Thread {
    private int a = 0;
    private volatile int c = 0;
    private volatile int b = 0;
    private int x = 0, y = 0;
    private ExecutorService executorService;

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public Reveal(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run() {
        Thread thread1 = new Thread(
                () -> {
                    a = 1;
                    c = 1;
                    y = b;
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    b = 1;
                    x = a;
                }
        );
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 这里的分析详见README
        if (x == 0 && y == 0) {

            System.err.println("Wrong,x = 0 and y = 0");
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        while (true) {
            executorService.submit(new Reveal(executorService));
        }
    }
}
