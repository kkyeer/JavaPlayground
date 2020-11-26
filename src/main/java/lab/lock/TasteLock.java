package lab.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TasteLock {
    public static void main(String[] args) {
        new TasteLock().tasteReentrantRWL();
    }

    private void tasteReentrantRWL(){
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        new Thread(new LockThread(reentrantReadWriteLock,1)).start();
        new Thread(new LockThread(reentrantReadWriteLock,2)).start();
        new Thread(new LockThread(reentrantReadWriteLock,3)).start();
    }

    class LockThread implements Runnable{
        private ReadWriteLock lock;
        private int index;

        LockThread(ReadWriteLock lock, int index) {
            this.lock = lock;
            this.index = index;
        }

        @Override
        public void run() {
            this.lock.writeLock().lock();
            int times = 10;
            while ((--times)>=0) {
                System.out.println("Reading:" + this.index);
            }
            System.out.println("End reading:" + this.index);
            this.lock.writeLock().unlock();
        }
    }
}

