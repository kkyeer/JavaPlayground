package concurrent.monitorpattern;

import concurrent.annotations.GuardedBy;
import concurrent.annotations.ThreadSafe;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kkyeer
 * @Description: 普通的汽车追踪器
 * @Date:Created in 17:45 2019/5/6
 * @Modified By:
 */
@ThreadSafe
class NormalSafeTracker implements Tracker {
    @GuardedBy("this")
    private Map<String,Point> locations;

    NormalSafeTracker(Map<String, Point> locations){
        this.locations = new ConcurrentHashMap<>();
        this.locations.putAll(locations);
    }
    /**
     * 获取所有的位置数据
     *
     * @return 获取所有的位置数据
     */
    @Override
    public synchronized Map<String, Point> getLocations() {
        return Collections.unmodifiableMap(this.locations);
    }

    /**
     * 获取某个Id对应的位置数据
     *
     * @param id 数据
     * @return ID对应的位置数据
     */
    @Override
    public synchronized Point getLocation(String id) {
        return this.locations.get(id);
    }

    /**
     * 更新某个ID对应的位置数据
     *
     * @param id       ID
     * @param location 位置
     */
    @Override
    public synchronized void setLocation(String id, Point location) {
        ImmutablePoint point = new ImmutablePoint(location);
        this.locations.put(id, point);
    }
}
