package taste.annotations.try2use.ioccontainer;

/**
 * @Author: kkyeer
 * @Description: 简单的服务类
 * @Date:Created in 22:46 2019/4/6
 * @Modified By:
 */
@Service
public class AuditService implements CommonService{
    @Autowire
    private AuditorPojo auditor;

    /**
     * 主方法，假设所有Service有且只有这一个方法
     */
    @Override
    public void exec() {
        System.out.println(String.format("level %d - %s auditing",auditor.getLevel(),auditor.getName()));
    }

    public void setAuditor(AuditorPojo auditor) {
        this.auditor = auditor;
    }
}
