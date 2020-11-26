package funny.bugs;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 9:05 PM 2020/10/4
 * @Modified By:
 */
import java.util.concurrent.*;

public class ForkJoinPoolTest {
    static boolean triggerIssue = true;

    static ForkJoinPool fjp;

    static void hotSleep(int ms) {
        long until = System.nanoTime() + ms*1000_000;
        while (System.nanoTime() < until) { }
    }

    public static void main(String[] args) {
        int parallelism = (args.length > 0) ? Integer.parseInt(args[0]) : Runtime.getRuntime().availableProcessors();
        fjp = new ForkJoinPool(parallelism);

        for (int i = 0; i < 1000; i += 1) {
            if (i % 25 == 0)
                System.out.println("iteration " + i);

            hotSleep(25);

            long begin = System.nanoTime();

            if (!triggerIssue)
                fjp.execute(() -> hotSleep(205)); // this doesn't trigger the issue

            T t = new T(50);
            fjp.execute(t);
            t.join();

            long duration = (System.nanoTime() - begin) / 1000_000;
            if (duration > 200)
                System.out.println("  " + i + ": suspicious duration " + duration);
        }
    }

    static class T extends RecursiveAction {
        private static T[] noTs = {};

        int n;

        public T(int n) {
            this.n = n;
        }

        public void compute() {
            T[] subtasks = (n > 2) ? new T[] { new T(n / 2), new T(n / 2) } : noTs;

            for (T t : subtasks)
                t.fork();
            if (triggerIssue && n == 50)
                fjp.execute(() -> hotSleep(205));

            hotSleep(2);

            for (T t : subtasks)
                t.join();
        }
    }
}
