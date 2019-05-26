package design.pattern.strategy;

import java.math.BigDecimal;

/**
 * @Author: kkyeer
 * @Description: 生产计算器的公司，设计各种计算器
 * @Date:Created in 16:05 2019/5/26
 * @Modified By:
 */
public class CalculatorCompany {
    public static void main(String[] args){
        CheapCalculator casio = new CheapCalculator("Casio", 100, new BigDecimal("0.99"));
        System.out.println(casio);
        try {
            System.out.println(casio.add(2, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddArithmetic simpleAdd = new SimpleAddArithmetic();
        casio.setAddArithmetic(simpleAdd);
        try {
            System.out.println(casio.add(2, 3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
