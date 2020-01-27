package taste.jmx;

/**
 * @Author: kkyeer
 * @Description: Hello MBean
 * @Date:Created in  2020-1-27 23:32
 * @Modified By:
 */
public interface HelloMBean {
    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    void helloWorld();

    void helloWorld(String string);

    void getTelephone();
}
