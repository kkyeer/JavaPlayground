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

    /**
     * 断言是否为真，并在为假时抛出对应错误
     * @param source 布尔型值
     * @param errMsg 错误提示信息
     */
    public static void assertTrue(boolean source, String errMsg) {
        if (!source) {
            throw new AssertionError(errMsg);
        }
    }

    public static void equal(int require, int actual) {
        if (require != actual) {
            throw new AssertionError("require:" + require + " , actual:" + actual);
        }
    }


}
