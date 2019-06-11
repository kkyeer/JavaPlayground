package design.pattern.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: kkyeer
 * @Description: 抽象的工厂类
 * @Date:Created in 9:59 2019/6/8
 * @Modified By:
 */
abstract class AbstractPhoneFactory implements PhoneFactory{
    /**
     * 子类通过复写这个方法来生产手机
     * @return 一部生产测试好的手机
     */
    protected abstract Phone getNewPhone();

    /**
     * 测试手机
     * @param phone 待测试手机
     * @return 测试完成的手机
     */
    private Phone test(Phone phone){
        System.out.println("Testing " + phone);
        // 出序列号
        phone.setSerial(phone.getBrand() + new SimpleDateFormat("yyyyMMdd").format(new Date()));
        return phone;
    }

    /**
     * 包装手机
     * @param phone 待包装手机
     * @return 包装好的手机
     */
    private Phone wrap(Phone phone) {
        System.out.println("Wrapping " + phone);
        return phone;
    }

    /**
     * 对外暴露的获取手机的方法
     * @return 完成后的手机
     */
    public Phone getPhone(){
        Phone crtPhone = getNewPhone();
        crtPhone = test(crtPhone);
        crtPhone = wrap(crtPhone);
        return crtPhone;
    }
}
