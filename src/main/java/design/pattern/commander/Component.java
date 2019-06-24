package design.pattern.commander;

/**
 * @Author: kkyeer
 * @Description: 零件接口
 * @Date:Created in 17:15 2019/6/24
 * @Modified By:
 */
interface Component {
    /**
     * 打开
     */
    void turnOn();

    /**
     * 关闭
     */
    void turnOff();
}
