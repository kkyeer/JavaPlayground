package concurrent.monitorpattern;

import java.awt.*;
import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 追踪器的接口类
 * @Date:Created in 16:50 2019/5/1
 * @Modified By:
 */
interface Tracker {
    /**
     * 获取所有的位置数据
     * @return 获取所有的位置数据
     */
    Map<String, Point> getLocations();

    /**
     * 获取某个Id对应的位置数据
     * @param id 数据
     * @return ID对应的位置数据
     */
    Point getLocation(String id);

    /**
     * 更新某个ID对应的位置数据
     * @param id ID
     * @param location 位置
     */
    void setLocation(String id, Point location);
}
