package concurrent.cache;

import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 模拟一个很耗时间的操作
 * @Date:Created in 16:22 2019/5/18
 * @Modified By:
 */
public class ExpensiveComputation implements Computable<Integer, Integer> {

    /**
     * 计算
     *
     * @param s 入参
     * @return 结果
     */
    @Override
    public Integer compute(Integer s) {
        try {
            Thread.sleep(new Random().nextInt(3000)+3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return s+s;
    }
}
