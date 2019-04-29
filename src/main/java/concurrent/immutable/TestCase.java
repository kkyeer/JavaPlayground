package concurrent.immutable;

import effectivejava3.generics.Stack;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 14:08 2019/4/29
 * @Modified By:
 */
class TestCase {
    private static final int RANDOM_SIZE = 3;
    public static void main(String[] args) {
//        Calculator calculator = new NoneSafeCachedCalculator();
        Calculator calculator = new SafeCachedCalculator();
        Random random = new Random();
        for (int i = 1; i < 10; i++) {
            if(i>RANDOM_SIZE){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            BigInteger input = new BigInteger("1" + random.nextInt(RANDOM_SIZE));
            new Thread(()->{
                BigInteger[] result = calculator.powers(input);
                if (!result[0].equals(input) || !result[1].equals(input.multiply(input))) {
                    System.out.println("----WRONG----Input["+(input)+"]:"+result[0]+"-"+result[1]+"-"+result[2]);
                }else {
                    System.out.println("Input:"+(input)+":"+result[0]+"-"+result[1]+"-"+result[2]);
                }
            }).start();
        }
    }
}