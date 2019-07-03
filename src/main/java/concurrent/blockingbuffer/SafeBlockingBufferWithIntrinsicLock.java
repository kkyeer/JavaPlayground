package concurrent.blockingbuffer;

import java.util.concurrent.Semaphore;

/**
 * @Author: kkyeer
 * @Description: 大小有限制的缓存，阻塞式IO
 * @Date:Created in 11:09 2019/6/9
 * @Modified By:
 */
class SafeBlockingBufferWithIntrinsicLock<E> implements BlockingBuffer<E> {
    private E[] dataArray;
    private int takePosition,putPosition;
    private final Semaphore availableItems,availableSpace;

    SafeBlockingBufferWithIntrinsicLock(int size) {
        dataArray = (E[]) new Object[size];
        availableItems = new Semaphore(0);
        availableSpace = new Semaphore(size);
    }

    @Override
    public void put(E ele) throws InterruptedException {
        availableSpace.acquire();
        synchronized (this){
            dataArray[putPosition] = ele;
            if (++putPosition == dataArray.length) {
                putPosition = 0;
            }
        }
        availableItems.release();
    }

    @Override
    public E take() throws InterruptedException {
        availableItems.acquire();
        E elem;
        synchronized (this){
            elem = dataArray[takePosition];
            dataArray[takePosition] = null;
            if (++takePosition == dataArray.length) {
                takePosition = 0;
            }

        }
        availableSpace.release();
        return elem;
    }

    @Override
    public boolean isFull(){
        return availableSpace.availablePermits() == 0;
    }

    @Override
    public boolean isEmpty(){
        return availableItems.availablePermits() == 0;
    }

    @Override
    public int availableSpace(){
        return availableSpace.availablePermits();
    }

    @Override
    public int availableItemCount(){
        return availableSpace.availablePermits();
    }
}
