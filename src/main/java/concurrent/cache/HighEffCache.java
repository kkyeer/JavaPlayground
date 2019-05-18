package concurrent.cache;

import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 高性能本地缓存
 * @Date:Created in 16:20 2019/5/18
 * @Modified By:
 */
public class HighEffCache<V,R> implements Computable<V,R>{
    private ConcurrentHashMap<V, Future<R>> cache = new ConcurrentHashMap<>();
    private Computable<V,R> computable;

    public HighEffCache(Computable<V,R> computable){
        this.computable = computable;
    }


    /**
     * 计算
     *
     * @param v 入参
     * @return 结果
     */
    @Override
    public R compute(V v) {
        FutureTask<R> nextF= new FutureTask<>(
                () -> computable.compute(v)
        );
        Future<R> result = cache.putIfAbsent(v,nextF);
        if (result != null) {
//            System.out.println("cached:"+v);
            nextF = (FutureTask<R>) result;
        }else {
//            System.out.println("not cached:"+v);
        }

        try {
            nextF.run();
            return nextF.get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
