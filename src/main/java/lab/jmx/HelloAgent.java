package lab.jmx;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * @Author: kkyeer
 * @Description: JMX的Agent层
 * @Date:Created in  2020-1-27 23:37
 * @Modified By:
 */
class HelloAgent {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException, IOException {
        viaRmi();
    }

    /**
     * 普通的mBeanServer,可以通过JDK自带的JConsole来进行管理与调用
     * @throws MalformedObjectNameException
     * @throws NotCompliantMBeanException
     * @throws InstanceAlreadyExistsException
     * @throws MBeanRegistrationException
     * @throws InterruptedException
     */
    private static void viaPid() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("jmxBean:name=hello");
        HelloMBean helloMBean = new Hello();
        mBeanServer.registerMBean(helloMBean, objectName);
        Thread.sleep(60*60 * 60 * 1000);
    }

    /**
     * 通过RMI发布的mBeanServer，可以通过url+端口形式调用，具体可以通过JConsole或者自定义客户端
     * @throws MalformedObjectNameException
     * @throws NotCompliantMBeanException
     * @throws InstanceAlreadyExistsException
     * @throws MBeanRegistrationException
     * @throws IOException
     */
    private static void viaRmi() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("jmxBean:name=hello");
        mBeanServer.registerMBean(new Hello(), objectName);

        LocateRegistry.createRegistry(8082);
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8082/jmxrmi");
        JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mBeanServer);
        System.out.println("Prepare to start jmx RMI");
        jmxConnectorServer.start();
        System.out.println("RMI started");
    }
}
