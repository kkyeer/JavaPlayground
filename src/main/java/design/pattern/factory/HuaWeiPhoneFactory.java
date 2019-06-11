package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 华为手机工厂
 * @Date:Created in 10:50 2019/6/8
 * @Modified By:
 */
class HuaWeiPhoneFactory extends AbstractPhoneFactory{

    /**
     * 子类通过复写这个方法来胜场手机
     *
     * @return 一部生产测试好的手机
     */
    @Override
    protected Phone getNewPhone() {
        return new Phone("HuaWei",9999);
    }
}
