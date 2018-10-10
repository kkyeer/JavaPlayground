package effectivejava3.singleton;

/**
 * 饿汉式，缺点是无论是否需要都会实例化，没有懒加载，优点是简洁明了且线程安全
 * @author kkyeer@gmail.com
 * @date 2018/10/3 10:13
 */
public class HungaryBasic {
    private static final HungaryBasic instance = new HungaryBasic();
    private String name = "HungaryBasic";
    private HungaryBasic() {

    }

    public static HungaryBasic getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }


    public static void main(String[] args) {
        System.out.println(HungaryBasic.getInstance().getName());
    }


}
