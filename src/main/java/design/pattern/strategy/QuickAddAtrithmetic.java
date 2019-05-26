package design.pattern.strategy;

/**
 * @Author: kkyeer
 * @Description: 快速加法
 * @Date:Created in 15:48 2019/5/26
 * @Modified By:
 */
class QuickAddAtrithmetic implements AddArithmetic{
    /**
     * 长整数加法
     *
     * @param num1 数字1
     * @param num2 数字2
     * @return 两者之和
     * @throws Exception 当和过大时
     */
    @Override
    public long add(long num1, long num2) throws Exception {
        // 假装这里有快速算法
        return num1+num2;
    }
}
