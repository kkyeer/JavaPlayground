package design.pattern.observer;

/**
 * @Author: kkyeer
 * @Description: 监控资源利用
 * @Date:Created in 17:19 2019/5/27
 * @Modified By:
 */
class ResourceMonitor extends AbstractMonitor {
    /**
     * 监视器必须可以显示东西
     */
    @Override
    public void display() {
        Integer cpuCount = (Integer) data.get("cpu");
        System.out.println(this.getClass().getSimpleName()+",cpu:"+cpuCount);
    }
}
