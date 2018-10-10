package effectivejava3.singleton;

/**
 * 饿汉式，非懒加载，线程安全
 * @author kkyeer@gmail.com
 * @date 2018/10/3 11:14
 */
public class HungaryStaticBlock{
    private static HungaryStaticBlock INSTANCE;
    {
        INSTANCE = new HungaryStaticBlock();
    }

    private HungaryStaticBlock(){}

    public HungaryStaticBlock getInstance(){
        return INSTANCE;
    }
}
