package effectivejava3.lambdas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * @Author: kkyeer
 * @Description: 根据Effective Java的内容，模拟一个cache基类，在构造方法中增加一个BiPredicate以决定是否要删除最老的key
 * @Date:Created in 11:02 2018/10/28
 * @Modified By:
 */
public class MapCacheUnsafe<K,V> extends LinkedHashMap<K,V> {
    private BiPredicate<Map<K,V>,Map.Entry<K, V>> eldestRemovalPredicater;

//    由于构造器方法不能被继承，因此，新建时构造器理论上应把所有需要用到的构造器全部写出并增加predicate入参，此处为简便，仅写一种构造方法
    /**
     * Constructs an empty insertion-ordered <tt>LinkedHashMap</tt> instance
     * with the specified initial capacity and a default load factor (0.75).
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public MapCacheUnsafe(int initialCapacity, BiPredicate<Map<K, V>, Map.Entry<K, V>> eldestRemovalPredicater) {
        super(initialCapacity);
        this.eldestRemovalPredicater = eldestRemovalPredicater;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean flag = eldestRemovalPredicater.test(this, eldest);
        if (flag) {
            this.remove(eldest.getKey());
        }
        return flag;
    }
}
