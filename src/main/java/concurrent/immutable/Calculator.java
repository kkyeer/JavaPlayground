package concurrent.immutable;

import java.math.BigInteger;

/**
 * @Author: kkyeer
 * @Description: 简单计算器接口
 * @Date:Created in 14:14 2019/4/29
 * @Modified By:
 */
interface Calculator {
    /**
     * 计算输入的整数次方
     * @param input 输入一个整数，长度不限
     * @return 输入整数的整数次方，输出数组中索引为i的位置存放input的(i+1)次方
     */
    BigInteger[] powers(BigInteger input);
}