package lab.annotations.meta.repeatable;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 22:41 2019/4/13
 * @Modified By:
 */
@Info("Created by kk")
@Info("On 2019/4/13")
public class TestCase {
    public static void main(String[] args) {
        Class<TestCase> testCaseClass = TestCase.class;
        Info infoAnnotation = testCaseClass.getAnnotation(Info.class);
        System.out.println(infoAnnotation);

        Info[] infoArray = testCaseClass.getDeclaredAnnotationsByType(Info.class);
        for (Info info : infoArray) {
            System.out.println(info.value());
        }
    }
}
