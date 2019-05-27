package design.pattern.observer;

import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 可以手动开启与结束的监视器
 * @Date:Created in 20:26 2019/5/27
 * @Modified By:
 */
abstract class AbstractMonitor implements Observer,Monitor{
    protected Subject dataSource;
    public void setDataSource(Subject dataSource) {
        this.dataSource = dataSource;
    }

    protected Map<String, Object> data;

    /**
     * 被Subject调用的方法
     *
     * @param data 数据，必须是Immutable，不然被其他观察者修改会有奇怪的问题
     */
    @Override
    public void update(Map<String, Object> data){
      this.data = data;
      this.display();
    }

    /**
     * 启动监听
     */
    void start(){
        this.dataSource.registerObserver(this);
    }

    /**
     * 结束监听
     */
    void  stop(){
        System.out.println("Stopping "+this.getClass().getSimpleName());
        this.dataSource.removeObserver(this);
    }
}
