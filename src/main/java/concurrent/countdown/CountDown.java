package concurrent.countdown;

/**
 * @Author: kkyeer
 * @Description: 倒计时的接口类
 * @Date:Created in 23:26 2019/4/17
 * @Modified By:
 */
interface CountDown {
    /**
     * 记一次时
     * @return 计时后的值
     */
    int countdown();
}