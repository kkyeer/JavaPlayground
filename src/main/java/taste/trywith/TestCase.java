package taste.trywith;


/**
 * @Author: kkyeer
 * @Description: 测试trywith会不会吞掉异常
 * @Date:Created in 9:13 12-11
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) {
        try (TestStream testStream =new TestStream()) {
            throw new RuntimeException("读取过程日常");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
