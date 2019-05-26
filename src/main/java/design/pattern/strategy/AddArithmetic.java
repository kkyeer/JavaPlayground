package design.pattern.strategy;

/**
 * @Author: kkyeer
 * @Description: 加法的算法
 * @Date:Created in 15:39 2019/5/26
 * @Modified By:
 */
public interface AddArithmetic {
    /**
     * 长整数加法
     * @param num1 数字1
     * @param num2 数字2
     * @return 两者之和
     * @throws Exception 当和过大时
     */
    long add(long num1, long num2) throws Exception;
}
