package lab.initseq;

/**
 * @Author: kkyeer
 * @Description: 子类
 * @Date:Created in 15:01 2019/8/6
 * @Modified By:
 */
public class ChildClass extends ParentClass {
    private static PrintablePojo staticPojoInitInline = new PrintablePojo("子类类变量初始化");
    private PrintablePojo instancePojoInitInline = new PrintablePojo("子类实例变量初始化");

    static {
        System.out.println("子类静态代码块");
    }

    ChildClass(){
        System.out.println("子类空构造器");
    }

    ChildClass(String info) {
        System.out.println("子类带参构造器：不显式调用任何构造器");
    }

    ChildClass(int param) {
        this();
        System.out.println("子类带参构造器：显式调用this()");
    }

    ChildClass(boolean param) {
        super("haha");
        System.out.println("子类带参构造器：显式调用super(String param)");
    }

    public static void main(String[] args) {
        System.out.println("----------调用子类空构造器的构造方法-------");
        new ChildClass();
        System.out.println("----------调用子类带参不显式调用构造器的构造方法-------");
        new ChildClass("a");
        System.out.println("----------调用子类带参显式调用this()的构造方法-------");
        new ChildClass(1);
        System.out.println("----------调用子类带参显式调用super(String)的构造方法-------");
        new ChildClass(true);
    }

}
