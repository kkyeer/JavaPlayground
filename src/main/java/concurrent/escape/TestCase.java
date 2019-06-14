package concurrent.escape;

/**
 * @Author: kkyeer
 * @Description: 测试逃逸
 * @Date:Created in 16:29 2019/4/22
 * @Modified By:
 */
public class TestCase {
    private final String NAME="TEST ESCAPE";
    // 构造器中的线程，某些变量尚未实例化完成，因此产生引用逃逸
    // https://www.linuxidc.com/wap.aspx?nid=153334
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
