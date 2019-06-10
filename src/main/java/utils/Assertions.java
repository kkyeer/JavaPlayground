package utils;

/**
 * @Author: kkyeer
 * @Description: 断言工具类
 * @Date:Created in 20:22 2019/6/10
 * @Modified By:
 */
public class Assertions {
    /**
     * 断言是否为真
     * @param source 条件表达式
     */
    public static void assertTrue(boolean source) {
        if (!source) {
            throw new AssertionError();
        }
    }


}
