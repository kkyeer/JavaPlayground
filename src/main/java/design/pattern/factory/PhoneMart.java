package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 手机大卖场
 * @Date:Created in 11:06 2019/6/8
 * @Modified By:
 */
class PhoneMart extends AbstractPhoneSeller {
    PhoneMart(PhoneFactory phoneFactory) {
        super(phoneFactory);
    }

    public void sellPhone() {
        Phone phone = this.getPhone();
        int originalPrice = phone.getPrice();
        int afterDiscount = (int) (originalPrice * 0.8);
        System.out.println("selling " + phone + ",price " + afterDiscount);
    }
}
