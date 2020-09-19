package lab.jmx;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: MBean实现类
 * @Date:Created in  2020-1-27 23:35
 * @Modified By:
 */
class Hello implements HelloMBean {
    private String name;

    private int age;

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void helloWorld() {
        System.out.println(this.toString());
        System.out.println("Hello world");
    }

    @Override
    public void helloWorld(String string) {
        System.out.println("Hello " + string);
    }

    @Override
    public void getTelephone() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Hello.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("age=" + age)
                .toString();
    }
}
