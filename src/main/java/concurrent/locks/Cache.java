package concurrent.locks;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: kkyeer
 * @Description: 测试读写锁
 * @Date:Created in 15:29 2019/7/2
 * @Modified By:
 */
class Cache implements Map<String,Object>{
    private final Map<String,Object> cache = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    public int size() {
        readLock.lock();
        try {
            return cache.size();
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        boolean isEmpty;
        lock.readLock().lock();
        isEmpty = cache.isEmpty();
        lock.readLock().unlock();
        return isEmpty;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean isEmpty;
        lock.readLock().lock();
        isEmpty = cache.containsKey(key);
        lock.readLock().unlock();
        return isEmpty;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        readLock.lock();
        try {
            return cache.get(key);
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public Object put(String key, Object value) {
        writeLock.lock();
        try {
            return cache.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }

    @Override
    public Object remove(Object key) {
        Object retValue;
        lock.writeLock().lock();
        retValue = cache.remove(key);
        lock.writeLock().unlock();
        return retValue;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        lock.writeLock().lock();
        cache.putAll(m);
        lock.writeLock().unlock();
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        cache.clear();
        lock.writeLock().unlock();
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
