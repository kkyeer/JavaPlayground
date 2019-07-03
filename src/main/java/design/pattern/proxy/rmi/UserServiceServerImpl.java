package design.pattern.proxy.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 * @Author: kkyeer
 * @Description: 用户服务的服务端实现
 * @Date:Created in 17:15 2019/7/3
 * @Modified By:
 */
class UserServiceServerImpl extends UnicastRemoteObject implements UserService {

    protected UserServiceServerImpl() throws RemoteException {
    }

    @Override
    public String getName() throws RemoteException {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
