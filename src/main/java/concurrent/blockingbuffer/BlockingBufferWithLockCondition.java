package concurrent.blockingbuffer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static concurrent.blockingbuffer.BlockingBufferTestCase.*;

/**
 * @Author: kkyeer
 * @Description: 使用Object的方法实现有界缓存
 * @Date:Created in 17:31 2019/7/3
 * @Modified By:
 */
class BlockingBufferWithLockCondition<E> implements BlockingBuffer<E> {
    private final E[] array;
    private final int boundSize;
    private int currentSize;
    private int takePosition,putPosition;
    private final Lock lock = new ReentrantLock();
    // 符合时表示非满
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    BlockingBufferWithLockCondition(int boundSize) {
        this.array = (E[]) new Object[boundSize];
        takePosition = 0;
        putPosition = 0;
        currentSize = 0;
        this.boundSize = boundSize;
    }

    @Override
    public void put(E ele) throws InterruptedException {
        lock.lock();
        while (isFull()) {
            notFull.await();
        }
        array[putPosition++] = ele;
        if (putPosition == boundSize) {
            putPosition = 0;
        }
        currentSize++;
        notEmpty.signalAll();
        lock.unlock();
    }

    @Override
    public E take() throws InterruptedException {
        lock.lock();
        while (isEmpty()) {
            notEmpty.await();
        }
        E retEle = array[takePosition++];
        if (takePosition == boundSize) {
            takePosition = 0;
        }
        currentSize--;
        notFull.signalAll();
        lock.unlock();
        return retEle;
    }

    @Override
    public boolean isFull() {
        return currentSize == boundSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public int availableSpace() {
        return boundSize - currentSize;
    }

    @Override
    public int availableItemCount() {
        return currentSize;
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        BlockingBuffer<String> blockingBuffer = new BlockingBufferWithLockCondition<>(BUFFER_SIZE);
        testBlockPutWhenFull(blockingBuffer);
        blockingBuffer = new BlockingBufferWithLockCondition<>(BUFFER_SIZE);
        testBlockTakeWhenEmpty(blockingBuffer);
        blockingBuffer = new BlockingBufferWithLockCondition<>(BUFFER_SIZE);
        testFullAndEmptyConcurrently(blockingBuffer);
        BlockingBuffer<Integer> integerBlockingBuffer = new BlockingBufferWithLockCondition<>(BUFFER_SIZE);
        testConcurrentCorrectness(integerBlockingBuffer);
    }
}
