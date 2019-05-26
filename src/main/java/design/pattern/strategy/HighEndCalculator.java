package design.pattern.strategy;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 高端计算器，支持刻字
 * @Date:Created in 16:02 2019/5/26
 * @Modified By:
 */
public class HighEndCalculator extends BaseCalculator {
    /**
     * 刻字
     */
    private String customLabel;


    /**
     * 必须有品牌和价格
     *
     * @param brand 品牌
     * @param price 价格
     */
    HighEndCalculator(String brand, float price) {
        super(brand, price);
    }

    /**
     * 构造器
     * @param brand 品牌
     * @param price 价格
     * @param customLabel 刻字
     */
    public HighEndCalculator(String brand, float price, String customLabel) {
        super(brand, price);
        this.customLabel = customLabel;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", brand + "[", "]")
                .add("customLabel='" + customLabel + "'")
                .add("price=" + price)
                .toString();
    }
}
