package effectivejava3.builderinheritance;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/2 21:40
 */
public class TestCase {
    public static void main(String[] args) {
        Student.StuBuilder stuBuilder = new Student.StuBuilder(10,"John",981);
        stuBuilder = stuBuilder.classNo(12345);
        stuBuilder = stuBuilder.id("askdfj");
        stuBuilder.classNo(2012);
        Student student = stuBuilder.build();
        System.out.println(student);
    }
}
