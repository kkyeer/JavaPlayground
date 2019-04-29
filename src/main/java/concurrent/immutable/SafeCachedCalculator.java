package concurrent.immutable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: kkyeer
 * @Description: 线程安全的带缓存计算器
 * @Date:Created in 14:47 2019/4/29
 * @Modified By:
 */
public class SafeCachedCalculator implements Calculator {
    private volatile PowerCache powerCache = new PowerCache(null,null);
    /**
     * 计算输入的整数次方
     *
     * @param input 输入一个整数，长度不限
     * @return 输入整数的整数次方，输出数组中索引为i的位置存放input的(i+1)次方
     */
    @Override
    public BigInteger[] powers(BigInteger input) {
        BigInteger[] result = powerCache.getPowers(input);
        if (result==null) {
            BigInteger[] currentPowers = new BigInteger[3];
            currentPowers[0] = input;
            currentPowers[1] = input.multiply(input);
            currentPowers[2] = currentPowers[1].multiply(input);
            PowerCache cache = new PowerCache(input, currentPowers);
            this.powerCache= cache;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = currentPowers;
        }
        return result;
    }
}
class PowerCache {
    private final BigInteger number;
    private final BigInteger[] powers;

    PowerCache(BigInteger number, BigInteger[] powers) {
        this.number = number;
        this.powers = powers==null?null:Arrays.copyOf(powers,powers.length);
    }

    BigInteger[] getPowers(BigInteger number) {
        if(number!=null&&number.equals(this.number)){
            return powers;
        }
        return null;
    }
}