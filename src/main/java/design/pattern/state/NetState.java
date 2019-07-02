package design.pattern.state;

/**
 * @Author: kkyeer
 * @Description: 网络状态抽象接口
 * @Date:Created in 22:41 2019/7/2
 * @Modified By:
 */
interface NetState {
    /**
     * 尝试连接
     */
    void link();

    /**
     * 尝试断开
     */
    void unlink();
}
