package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 11:14 2019/6/8
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) {
        PhoneFactory phoneFactory = new HuaWeiPhoneFactory();
        PhoneSeller phoneSeller = new PhoneMart(phoneFactory);
        phoneSeller.sellPhone();
    }
}
