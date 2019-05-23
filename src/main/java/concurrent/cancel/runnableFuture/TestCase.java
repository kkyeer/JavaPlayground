package concurrent.cancel.runnableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kkyeer
 * @Description: 测试可取消任务的测试用例
 * @Date:Created in 11:01 2019/5/22
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) throws Exception {

        CancellingExecutor cancellingExecutor = new CancellingExecutor(4, 4, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        Queue<String> dishPlate = new LinkedBlockingDeque<>(8);
        List<RunnableFuture<String>> cancellableTaskList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            CancellableTask<String> cancellableTask = new WasherTask<String>() {
                @Override
                public String call() throws Exception {
                    for (int j = 0; j < 3; j++) {
                        dishPlate.add("DISH-" + j);
                        System.out.println(Thread.currentThread().getName()+" Added:"+j);
                        int sleepTime = new Random().nextInt(1000)+1000;
                        Thread.sleep(sleepTime);
                    }
                    return null;
                }
            };
            RunnableFuture<String> runnableFuture = cancellingExecutor.newTaskFor(cancellableTask);
            cancellableTaskList.add(runnableFuture);
        }
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            RunnableFuture<String> cancellableTask = cancellableTaskList.get(i);
            new Thread(cancellableTask).start();
            int sleepTime = random.nextInt(1000)+1000;
            new Thread(() -> {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("should cancel");
                cancellableTask.cancel(true);
            }).start();

        }
    }
}
