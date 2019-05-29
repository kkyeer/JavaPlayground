package concurrent.semaphore;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @Author: kkyeer
 * @Description: 数量有上限的Set
 * @Date:Created in 20:22 2019/5/17
 * @Modified By:
 */
class BoundedHashSet<E>{
    private Set<E> set = new HashSet<>();

    private Semaphore semaphore;

    BoundedHashSet(int maxSize) {
        this.semaphore = new Semaphore(maxSize);
    }

    public void add(E obj){
        try {
            this.semaphore.acquire();
            this.set.add(obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void remove(E obj){
        synchronized (this){
            if (this.set.contains(obj)) {
                this.set.remove(obj);
                this.semaphore.release();
            }
        }
    }

    int getSize(){
        return this.set.size();
    }
}
