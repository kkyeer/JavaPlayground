package concurrent.cancel.jvmshutdownhook;

/**
 * @Author: kkyeer
 * @Description: 试一下JVM的ShutdownHook
 * @Date:Created in 19:06 2019/5/26
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("cleaning some info");
        }));
    }
}
