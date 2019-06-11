package design.pattern.factory;

/**
 * @Author: kkyeer
 * @Description: 经销商接口
 * @Date:Created in 11:09 2019/6/8
 * @Modified By:
 */
interface PhoneSeller {
    /**
     * 进货
     *
     * @return 进到的手机
     */
    Phone getPhone();

    /**
     * 将指定的手机卖出去
     */
    void sellPhone();
}
