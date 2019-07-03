package concurrent.boundedbuffer;

/**
 * @Author: kkyeer
 * @Description: 大小有限制的缓存，阻塞式IO
 * @Date:Created in 11:09 2019/6/9
 * @Modified By:
 */
interface BlockingBuffer<E> {;

    void put(E ele) throws InterruptedException;

    E take()  throws InterruptedException;

    boolean isFull();

    boolean isEmpty();

    int availableSpace();

    int availableItemCount();
}
