package concurrent.escape;

/**
 * @Author: kkyeer
 * @Description: 测试逃逸
 * @Date:Created in 16:29 2019/4/22
 * @Modified By:
 */
public class TestCase {
    private final String NAME="TEST ESCAPE";
    public TestCase(){
        new Thread(()-> {
            System.out.println("Child:"+this.hashCode());
            System.out.println("Child NAME:"+NAME);
        }).start();
    }

    public static void main(String[] args) {
        TestCase testCase = new TestCase();
        System.out.println("Parent:"+testCase.hashCode());
        System.out.println("Parent NAME:"+testCase.NAME);
    }
}
