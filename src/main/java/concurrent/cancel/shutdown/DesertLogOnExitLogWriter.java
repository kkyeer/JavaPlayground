package concurrent.cancel.shutdown;

import concurrent.annotations.GuardedBy;

import java.io.PrintWriter;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 关闭时，把所有日志全部丢弃
 * @Date:Created in 10:02 2019/5/24
 * @Modified By:
 */
class DesertLogOnExitLogWriter implements LogWriter {
    private final BlockingQueue<String> toBeWritten = new LinkedBlockingQueue<>(10);
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @GuardedBy("this")
    private boolean shuttingDown = false;

    /**
     * 开启
     */
    @Override
    public void start() {
        executorService.execute(
                ()->{
                    Random random = new Random();
                    while (true) {
                        if (!shuttingDown) {
                            try {
                                String logContent = toBeWritten.take();
                                Thread.sleep(random.nextInt(100)+50);
                                System.out.println("Log:"+logContent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        }else {
                            break;
                        }
                    }
                    System.out.println("end of print thread");
                    executorService.shutdown();
                }
        );
    }

    /**
     * 打印
     *
     * @param content 内容
     */
    @Override
    public void append(String content) throws InterruptedException {
        synchronized (this) {
            if (shuttingDown) {
                throw new InterruptedException();
            }
        }
        toBeWritten.put(content);
    }

    /**
     * 关闭
     */
    @Override
    public synchronized void shutdown() {
        shuttingDown = true;
    }
}
