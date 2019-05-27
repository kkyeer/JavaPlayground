package design.pattern.observer;

/**
 * @Author: kkyeer
 * @Description: 主题接口
 * @Date:Created in 23:41 2019/5/26
 * @Modified By:
 */
interface Subject {
    /**
     * 注册观察者
     * @param observer 新观察者
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     * @param observer 要移除的观察者
     */
    void removeObserver(Observer observer);

    /**
     * 通知所有观察者
     */
    void notifyObservers();
}
