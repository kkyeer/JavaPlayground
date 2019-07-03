package design.pattern.proxy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @Author: kkyeer
 * @Description: 用户服务接口
 * @Date:Created in 17:14 2019/7/3
 * @Modified By:
 */
interface UserService extends Remote {
    /**
     * 获取姓名
     * @return 姓名
     * @throws RemoteException 异常时抛出
     */
    String getName() throws RemoteException;
}
