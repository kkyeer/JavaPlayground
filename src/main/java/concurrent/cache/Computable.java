package concurrent.cache;

/**
 * @Author: kkyeer
 * @Description: 可以通过调用compute方法来进行计算
 * @Date:Created in 16:20 2019/5/18
 * @Modified By:
 */
public interface Computable<V,R> {

    /**
     * 计算
     * @param v 入参
     * @return 结果
     */
    R compute(V v);

}
