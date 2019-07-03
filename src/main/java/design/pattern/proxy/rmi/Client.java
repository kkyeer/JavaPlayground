package design.pattern.proxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @Author: kkyeer
 * @Description: 客户端
 * @Date:Created in 17:19 2019/7/3
 * @Modified By:
 */
class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        UserService userService = (UserService) Naming.lookup("rmi://localhost:8888/userService");
        System.out.println(userService.getName());
        System.out.println(userService.getName());
    }
}
