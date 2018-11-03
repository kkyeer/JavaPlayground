package effectivejava3.lambdas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 17:07 2018/10/28
 * @Modified By:
 */
public class MapCacheSafe<K,V> extends ConcurrentHashMap<K,V> {
    private Consumer<Map<K,V>> putAction;

    public MapCacheSafe(int initialCapacity, Consumer<Map<K, V>> putAction) {
        super(initialCapacity);
            this.putAction = putAction;
    }
    @Override
    public V put(K key, V value) {
        V oriValue = super.put(key, value);
        synchronized (this) {
            putAction.accept(this);
        }
        return oriValue;
    }
}
