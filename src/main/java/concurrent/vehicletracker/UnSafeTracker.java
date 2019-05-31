package concurrent.vehicletracker;

import concurrent.annotations.GuardedBy;
import concurrent.annotations.NotThreadSafe;
import concurrent.annotations.ThreadSafe;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 不安全的
 * @Date:Created in 17:45 2019/5/6
 * @Modified By:
 */
@NotThreadSafe
class UnSafeTracker implements Tracker {
    @GuardedBy("this")
    private Map<String,Point> locations;

    UnSafeTracker(Map<String, Point> locations){
        this.locations = locations;
    }
    /**
     * 获取所有的位置数据
     *
     * @return 获取所有的位置数据
     */
    @Override
    public synchronized Map<String, Point> getLocations() {
        return new HashMap<>(locations);
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
        this.locations.put(id, location);
    }
}
