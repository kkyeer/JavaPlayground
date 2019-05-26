package design.pattern.strategy;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 计算器基类
 * @Date:Created in 15:33 2019/5/26
 * @Modified By:
 */
abstract class BaseCalculator {
    /**
     * 加法算法
     */
    private AddArithmetic addArithmetic;
    /**
     * 品牌
     */
    protected String brand;
    /**
     * 价格
     */
    protected float price;

    /**
     * 必须有品牌和价格
     * @param brand 品牌
     * @param price 价格
     */
    BaseCalculator(String brand, float price) {
        this.brand = brand;
        this.price = price;
    }

    /**
     * 使用setter引入加法算法
     *
     * @param addArithmetic 加法算法
     */
    public void setAddArithmetic(AddArithmetic addArithmetic) {
        this.addArithmetic = addArithmetic;
    }

    /**
     * 使用内部的加法算法来计算
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 和
     * @throws Exception 算法为空或者加和过程出错
     */
    long add(long num1, long num2) throws Exception {
        if (this.addArithmetic == null) {
            throw new NullPointerException("加法算法为空，请定义");
        }
        return this.addArithmetic.add(num1, num2);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", brand + "[", "]")
                .add("price=" + price)
                .toString();
    }
}
