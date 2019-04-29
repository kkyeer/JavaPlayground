package concurrent.immutable;

import concurrent.annotations.NotThreadSafe;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: kkyeer
 * @Description: 线程不安全的带缓存计算器
 * @Date:Created in 13:56 2019/4/29
 * @Modified By:
 */
@NotThreadSafe
class NoneSafeCachedCalculator implements Calculator{
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> powers = new AtomicReference<>();

    public BigInteger[] powers(BigInteger input){
        if (lastNumber.get()!=null&&lastNumber.get().equals(input)) {
            return powers.get();
        }else {
            BigInteger[] currentPowers = new BigInteger[3];
            currentPowers[0] = input;
            currentPowers[1] = input.multiply(input);
            currentPowers[2] = currentPowers[1].multiply(input);
            powers.set(currentPowers);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastNumber.set(input);
            return currentPowers;
        }
    }
}