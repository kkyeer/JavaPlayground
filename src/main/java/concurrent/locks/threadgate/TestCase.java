package concurrent.locks.threadgate;

import utils.Assertions;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 测试类
 * @Date:Created in 10:42 2019/7/5
 * @Modified By:
 */
class TestCase {
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);
    private static final AtomicInteger count = new AtomicInteger(0);
    static void test(ThreadGate gate){
        try {
            testNormalFunction(gate);
//            testRapidOpenAndClose(gate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdownNow();
        }
    }

    private static void testRapidOpenAndClose(ThreadGate gate)  throws InterruptedException{
        go(gate);
        int blockedCount = gate.openDoor();
        gate.closeDoor();
        try {
            barrier.await(1,TimeUnit.SECONDS);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            // ignore
        }
        Assertions.assertTrue(count.get() == blockedCount);
        System.out.println("TEST PASSED");
    }

    /**
     * 测试开与关是否正常
     * @param gate 待测试对象
     * @throws InterruptedException 异常
     * @throws BrokenBarrierException 异常
     */
    private static void testNormalFunction(ThreadGate gate) throws InterruptedException, BrokenBarrierException  {
        go(gate);
        gate.openDoor();
        barrier.await();
        Assertions.assertTrue(count.get() == THREAD_COUNT);
        gate.closeDoor();
        count.set(0);
        go(gate);
        gate.openDoor();
        barrier.await();
        Assertions.assertTrue(count.get() == THREAD_COUNT);
        System.out.println("TEST PASSED");
    }

    private static void go(ThreadGate gate){
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(
                    ()->{
                        try {
                            gate.waitDoor();
                            count.getAndIncrement();
                            barrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            // ignore
                        }
                    }
            );
        }
    }
}
