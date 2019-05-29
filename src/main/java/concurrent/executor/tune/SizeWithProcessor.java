package concurrent.executor.tune;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 使用当前的处理器信息来定义线程池大小
 * @Date:Created in 17:11 2019/5/28
 * @Modified By:
 */
class SizeWithProcessor {
    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors:" + processors);
        ExecutorService service = Executors.newFixedThreadPool(processors+1);
        service.submit(() -> {
            System.out.println("task");
        });
    }
}
