package design.pattern.observer;

/**
 * @Author: kkyeer
 * @Description: 监控任务进度
 * @Date:Created in 17:39 2019/5/27
 * @Modified By:
 */
class ProgressMonitor extends AbstractMonitor {
    /**
     * 监视器必须可以显示东西
     */
    @Override
    public void display() {
        String progress = (String) data.get("progress");
        System.out.println(this.getClass().getSimpleName() + ",progress:" + progress);
    }
}
