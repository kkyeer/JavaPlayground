package lab.annotations.try2use.ioccontainer;

/**
 * @Author: kkyeer
 * @Description: 测试一下
 * @Date:Created in 22:54 2019/4/6
 * @Modified By:kk in 12:55 2019/4/7
 */
class TestCase {
    public static void main(String[] args) throws Exception {
        IocContainer container = new IocContainer();
        CommonService service = container.getService(AuditService.class);
        service.exec();
    }
}
