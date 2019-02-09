package taste.lock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kk
 * @desc 有界缓存
 */
public class BoundedBuffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Object[] buffer = new Object[100];
    private int count,takeptr,putptr;

    private void put(T item) {
        lock.lock();
        try {
            while (count == buffer.length) {
                notFull.await();
            }
            buffer[putptr] = item;
            if(++putptr==buffer.length){
                putptr=0;
            }
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private T take(){
        lock.lock();
        try {
            while (count <= 0) {
                notEmpty.await();
            }
            T obj = (T) buffer[takeptr];
            if(++takeptr==buffer.length) takeptr=0;
            --count;
            notFull.signal();
            return obj;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public static void main(String[] args) {
        BoundedBuffer<String> boundedBuffer = new BoundedBuffer<>();
        boundedBuffer.put("Hello");
        System.out.println(boundedBuffer.take());
//        System.out.println(boundedBuffer.take());
        new Thread(() -> {
            System.out.println("New Thread start");
            System.out.println(boundedBuffer.take());
            System.out.println("New Thread Read");
        }).start();
        Scanner scanner =new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            boundedBuffer.put(next);
            if ("eol".equals(next)) {
                break;
            }
        }
    }
}
