package concurrent.concurrentrw;

import concurrent.annotations.NotThreadSafe;

/**
 * @Author: kkyeer
 * @Description: 并发读写
 * @Date:Created in 17:53 2019/4/21
 * @Modified By:
 */
@NotThreadSafe
public class NoVisibility {
    private static boolean ready;
    private static String input;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(input);
        }
    }

    public static void main(String[] args) {
        // todo 有没有更好的办法展现错误现象？
        new ReaderThread().start();
        input = "42";
        ready = true;
    }
}
