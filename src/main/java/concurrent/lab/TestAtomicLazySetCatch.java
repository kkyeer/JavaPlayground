package concurrent.lab;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试Atomic方法使用LazySet的速度是否大于Set
 * @Date:Created in 5:22 PM 2020/12/21
 * @Modified By:
 */
public class TestAtomicLazySetCatch {
    AtomicInteger counter= new AtomicInteger(0);
    private static final int ARRAY_LENGTH = 1000 * 100 * 100;
    int[] array = new int[ARRAY_LENGTH];
    public final CountDownLatch END_LATCH = new CountDownLatch(2);

    public Thread producer(){
        return new Thread(
                ()->{;
                    for (int i = 0; i < ARRAY_LENGTH; i++) {
                        counter.lazySet(i);
//                        counter.set(i);
                        array[i] = i;
                    }
                    END_LATCH.countDown();
                }
        );
    }

    public Thread consumer(){
        return new Thread(
                ()->{
                    int consumerIndex = 0;
                    int producerIndex;
                    int end = ARRAY_LENGTH -1;
                    while ((producerIndex = counter.get()) < end) {
                        if (consumerIndex < producerIndex) {
                            if (array[consumerIndex] != consumerIndex) {
                                throw new RuntimeException();
                            }
                            consumerIndex++;
                        }
                    }
                    END_LATCH.countDown();
                }
        );
    }

    public static void main(String[] args) throws InterruptedException {
        long timeSum = 0;
        for (int i = 0; i < 100; i++) {
            long start  = System.nanoTime();
            TestAtomicLazySetCatch test = new TestAtomicLazySetCatch();
            test.producer().start();
            test.consumer().start();
            test.END_LATCH.await();
            long duration = System.nanoTime() - start;
            timeSum += duration;
            System.out.println("Time = " + duration);
        }
        System.out.println("Time Average:" + (timeSum / 100));

//        791829
//        2384902
//        67559729
//        253913151
    }
}
