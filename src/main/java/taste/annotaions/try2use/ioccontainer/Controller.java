package taste.annotaions.try2use.ioccontainer;

/**
 * @Author: kkyeer
 * @Description: 模拟Controller，假设当前Controller不在容器管理中
 * @Date:Created in 22:54 2019/4/6
 * @Modified By:
 */
public class Controller {
    public static void main(String[] args) throws Exception {
        IocContainer container = new IocContainer();
        CommonService service = container.getService(AuditService.class);
        service.exec();
    }
}
