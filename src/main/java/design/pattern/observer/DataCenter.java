package design.pattern.observer;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 数据中心
 * @Date:Created in 17:13 2019/5/27
 * @Modified By:
 */
class DataCenter implements Subject {
    private final Set<Observer> observerSet = new HashSet<>(3);
    private final Map<String, Object> data = new HashMap<>(3);
    private final ExecutorService notificationThreadPool = Executors.newCachedThreadPool();

    DataCenter() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Random random = new Random();
        scheduledExecutorService.scheduleAtFixedRate(()->{
            int cpu = random.nextInt(10);
            int progress = random.nextInt(100);
            data.put("cpu", cpu);
            data.put("progress", "完成" + progress + "%,剩余" + (100 - progress) + "%");
            notifyObservers();
        },0,1, TimeUnit.SECONDS);
    }

    /**
     * 注册观察者
     *
     * @param observer 新观察者
     */
    @Override
    public void registerObserver(Observer observer) {
        observerSet.add(observer);
    }

    /**
     * 移除观察者
     *
     * @param observer 要移除的观察者
     */
    @Override
    public void removeObserver(Observer observer) {
        observerSet.remove(observer);
    }

    /**
     * 通知所有观察者
     */
    @Override
    public void notifyObservers() {
        // 使用局部变量避免并发问题
        Set<Observer> currentObserverSet = new HashSet<>(observerSet);
        Map<String, Object> currentData = Collections.unmodifiableMap(data);
        currentObserverSet.forEach(
                observer -> notificationThreadPool.execute(
                        ()-> observer.update(currentData)
                )
        );
    }
}
