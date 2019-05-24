package concurrent.cancel.shutdown;

/**
 * @Author: kkyeer
 * @Description: 日志接口
 * @Date:Created in 10:06 2019/5/24
 * @Modified By:
 */
interface LogWriter {
    /**
     * 开启
     */
    void start();

    /**
     * 打印
     *
     * @param content 内容
     * @throws InterruptedException 当无法打印时，抛出异常，client应该对此异常作出相应处理
     */
    void append(String content) throws InterruptedException;

    /**
     * 关闭
     */
    void shutdown();
}
