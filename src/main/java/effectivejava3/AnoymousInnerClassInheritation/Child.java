package effectivejava3.AnoymousInnerClassInheritation;

/**
 * @author kkyeer@gmail.com
 * @date 2018/10/1 17:41
 */
public class Child extends Parent {
    @Override
    void test() {
        this.printer.print("this is in child class");
    }

    public static void main(String[] args) {
        new Child().test();
    }
}
