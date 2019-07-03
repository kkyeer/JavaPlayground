package concurrent.boundedbuffer;

import java.util.concurrent.BrokenBarrierException;

import static concurrent.boundedbuffer.BlockingBufferTestCase.*;

/**
 * @Author: kkyeer
 * @Description: 使用Object的方法实现有界缓存
 * @Date:Created in 17:31 2019/7/3
 * @Modified By:
 */
class CustomBlockingBufferWithConditionQueue<E> implements BlockingBuffer<E> {
    private final E[] array;
    final int boundSize;
    private int currentSize;
    private int takePosition,putPosition;

    public CustomBlockingBufferWithConditionQueue(int boundSize) {
        this.array = (E[]) new Object[boundSize];
        takePosition = 0;
        putPosition = 0;
        currentSize = 0;
        this.boundSize = boundSize;
    }

    @Override
    public synchronized void put(E ele) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        array[putPosition++] = ele;
        if (putPosition == boundSize) {
            putPosition = 0;
        }
        currentSize++;
        notifyAll();
    }

    @Override
    public synchronized E take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        E retEle = array[takePosition++];
        if (takePosition == boundSize) {
            takePosition = 0;
        }
        currentSize--;
        notifyAll();
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
        BlockingBuffer<String> blockingBuffer = new CustomBlockingBufferWithConditionQueue<>(BUFFER_SIZE);
        testBlockPutWhenFull(blockingBuffer);
        blockingBuffer = new CustomBlockingBufferWithConditionQueue<>(BUFFER_SIZE);
        testBlockTakeWhenEmpty(blockingBuffer);
        blockingBuffer = new CustomBlockingBufferWithConditionQueue<>(BUFFER_SIZE);
        testFullAndEmptyConcurrently(blockingBuffer);
        BlockingBuffer<Integer> integerBlockingBuffer = new CustomBlockingBufferWithConditionQueue<>(BUFFER_SIZE);
        testConcurrentCorrectness(integerBlockingBuffer);
    }
}
