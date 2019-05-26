package design.pattern.strategy;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 廉价计算器，活动时会打折
 * @Date:Created in 15:49 2019/5/26
 * @Modified By:
 */
public class CheapCalculator extends BaseCalculator {
    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 必须有品牌和价格
     *
     * @param brand 品牌
     * @param price 价格
     */
    CheapCalculator(String brand, float price) {
        super(brand, price);
    }

    /**
     * 必须有品牌和价格
     *
     * @param brand 品牌
     * @param price 价格
     * @param discount 折扣
     */
    CheapCalculator(String brand, float price,BigDecimal discount) {
        super(brand, price);
        this.discount = discount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", brand + "[", "]")
                .add("discount=" + discount)
                .add("price=" + price)
                .toString();
    }
}
