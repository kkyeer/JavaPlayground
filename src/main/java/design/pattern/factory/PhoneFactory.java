package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 手机工厂抽象接口
 * @Date:Created in 11:01 2019/6/8
 * @Modified By:
 */
interface PhoneFactory {

    /**
     * 对外暴露的获取手机的方法
     * @return 完成后的手机
     */
    Phone getPhone();
}
