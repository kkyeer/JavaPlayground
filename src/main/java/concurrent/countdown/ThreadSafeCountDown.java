package concurrent.countdown;

import concurrent.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 线程安全的倒计时器
 * @Date:Created in 23:36 2019/4/17
 * @Modified By:
 */
@ThreadSafe
public class ThreadSafeCountDown implements CountDown {
    private AtomicInteger count = new AtomicInteger(20);

    /**
     * 记一次时
     *
     * @return 计时后的值
     */
    @Override
    public int countdown() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count.decrementAndGet();
    }
}
