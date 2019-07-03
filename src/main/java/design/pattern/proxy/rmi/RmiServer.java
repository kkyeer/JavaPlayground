package design.pattern.proxy.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @Author: kkyeer
 * @Description: RMI服务端的入口
 * @Date:Created in 17:16 2019/7/3
 * @Modified By:
 */
class RmiServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        LocateRegistry.createRegistry(8888);
        UserService userService = new UserServiceServerImpl();
        Naming.bind("rmi://localhost:8888/userService",userService);
    }
}
