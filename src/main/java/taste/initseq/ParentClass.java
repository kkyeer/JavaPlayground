package taste.initseq;

/**
 * @Author: kkyeer
 * @Description: 父类
 * @Date:Created in 15:01 2019/8/6
 * @Modified By:
 */
class ParentClass {
    private static PrintablePojo staticPojoInitInline = new PrintablePojo("父类类变量初始化");
    private PrintablePojo instancePojoInitInline = new PrintablePojo("父类实例变量初始化");

    static {
        System.out.println("父类静态代码块");
    }

    ParentClass(){
        System.out.println("父类空构造器");
    }

    ParentClass(String info) {
        System.out.println("父类带参构造器");
    }
}
