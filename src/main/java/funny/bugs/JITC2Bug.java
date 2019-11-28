package funny.bugs;

/**
 * @Author: kkyeer
 * @Description: JvmC2bug
 * @Date:Created in 10:02 11-3
 * @Modified By:
 */
public class JITC2Bug {
    public void test() {
        int i = 8;
        while ((i -= 3) > 0);
        System.out.println("i = " + i);
    }

    public static void main(String[] args) {
        JITC2Bug hello = new JITC2Bug();
        for (int i = 0; i < 50_000; i++) {
            hello.test();
        }
    }
}
