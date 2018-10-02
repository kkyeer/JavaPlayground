package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/2 21:40
 */
public class TestCase {
    public static void main(String[] args) {
        Student.Builder stuBuilder = new Student.Builder(10,"John");
        stuBuilder = stuBuilder.id("10293");
        stuBuilder.classNo(2012);
        Student student = stuBuilder.build();
        System.out.println(student);
    }
}
