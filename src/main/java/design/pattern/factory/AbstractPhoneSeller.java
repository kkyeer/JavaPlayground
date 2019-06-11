package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 经销商抽象类
 * @Date:Created in 10:53 2019/6/8
 * @Modified By:
 */
abstract class AbstractPhoneSeller implements PhoneSeller{
    private PhoneFactory phoneFactory;

    AbstractPhoneSeller(PhoneFactory phoneFactory) {
        this.phoneFactory = phoneFactory;
    }

    public void setPhoneFactory(PhoneFactory phoneFactory) {
        this.phoneFactory = phoneFactory;
    }

    @Override
    public final Phone getPhone() {
        return this.phoneFactory.getPhone();
    }
}
