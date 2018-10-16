package share;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/6 20:09
 */
public class Person {
    int age;
    String name;

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
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person() {
    }
}
