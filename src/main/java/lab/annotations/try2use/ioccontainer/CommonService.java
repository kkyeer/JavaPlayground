package lab.annotations.try2use.ioccontainer;

/**
 * @Author: kkyeer
 * @Description: 为了代理的方便，假设所有Service都继承这个接口，这样可以使用原生动态代理
 * @Date:Created in 12:14 2019/4/7
 * @Modified By:
 */
public interface CommonService {
    /**
     * 主方法，假设所有Service有且只有这一个方法
     */
    void exec();
}
