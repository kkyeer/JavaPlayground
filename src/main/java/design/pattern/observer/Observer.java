package design.pattern.observer;

import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 观察者接口
 * @Date:Created in 23:44 2019/5/26
 * @Modified By:
 */
interface Observer {
    /**
     * 被Subject调用的方法
     * @param data 数据，必须是Immutable，不然被其他观察者修改会有奇怪的问题
     */
    void update(@Immutable Map<String,Object> data);
}
