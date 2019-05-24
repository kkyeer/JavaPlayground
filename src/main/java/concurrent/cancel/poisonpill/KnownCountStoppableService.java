package concurrent.cancel.poisonpill;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 14:26 2019/5/24
 * @Modified By:
 */
class KnownCountStoppableService {
    private static final int PRODUCER_COUNT = 3;
    public static void main(String[] args) throws InterruptedException {
        KnownCountStoppableService knownCountStoppableService = new KnownCountStoppableService();
        knownCountStoppableService.start();
        Thread.sleep(5000);
        knownCountStoppableService.stop();
    }

    private static final String POISON = "--POISON--";
    private final BlockingDeque<String> stringBlockingDeque = new LinkedBlockingDeque<>(10);
    private final List<Producer> producerList = new ArrayList<>(PRODUCER_COUNT);
    private final Consumer consumer = new Consumer();

    void start(){
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Producer producer = new Producer();
            producer.start();
            producerList.add(producer);
        }
        consumer.start();
    }

    void stop(){
        producerList.forEach(
                producer -> producer.interrupt()
        );
    }


    class Consumer extends Thread{
        @Override
        public void run() {
            int POISON_COUNT = 0;
            while (true) {
                try {
                    String content = stringBlockingDeque.take();
                    if (content == POISON) {
                        System.out.println("encounter poison:"+POISON_COUNT);
                        if (++POISON_COUNT == PRODUCER_COUNT) {
                            break;
                        }
                    } else {
                        System.out.println("Got:"+content);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("consumer ended");
        }
    }
    class Producer extends Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    stringBlockingDeque.put("a");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
            try {
                stringBlockingDeque.put(POISON);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("producer ended");
        }
    }
}
