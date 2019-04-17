package concurrent.countdown;

import concurrent.annotations.NotThreadSafe;

/**
 * @Author: kkyeer
 * @Description: 非线程安全的倒计时
 * @Date:Created in 23:27 2019/4/17
 * @Modified By:
 */
@NotThreadSafe
class NotThreadsafeCountDown implements CountDown{
    private int count = 20;

    /**
     * 记一次时
     *
     * @return 计时后的值
     */
    @Override
    public int countdown() {
        count--;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }
}
