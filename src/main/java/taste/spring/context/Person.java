package taste.spring.context;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 18:42 2018/12/1
 * @Modified By:
 */
public class Person {
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("age=" + age)
                .add("name='" + name + "'")
                .toString();
    }
}
