package design.pattern.strategy;

/**
 * @Author: kkyeer
 * @Description: 简单加法的实现
 * @Date:Created in 15:42 2019/5/26
 * @Modified By:
 */
class SimpleAddArithmetic implements AddArithmetic{

    /**
     * 长整数加法
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 两者之和
     * @throws NumberFormatException
     */
    @Override
    public long add(long num1, long num2) throws NumberFormatException {
        return num1+num2;
    }
}
