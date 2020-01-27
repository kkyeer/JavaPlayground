package taste.jmx;

import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @Author: kkyeer
 * @Description: 通过RMI调用MBean方法的客户端
 * @Date:Created in  2020-1-28 00:09
 * @Modified By:
 */
class JMXClient {
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8082/jmxrmi");
        JMXConnector jmxConnector = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        ObjectName objectName = new ObjectName("jmxBean:name=hello");
        System.out.println("domains");
        String[] domains = mBeanServerConnection.getDomains();
        for (String domain : domains) {
            System.out.println(domain);
        }
        mBeanServerConnection.setAttribute(objectName, new Attribute("Name", "hhhhhhh"));
        mBeanServerConnection.setAttribute(objectName, new Attribute("Age", 12));
        HelloMBean proxy = MBeanServerInvocationHandler.newProxyInstance(mBeanServerConnection, objectName, HelloMBean.class, false);
        System.out.println(proxy.getAge());
        System.out.println(proxy.getName());
        proxy.helloWorld("eeee");
        proxy.helloWorld();
    }
}
