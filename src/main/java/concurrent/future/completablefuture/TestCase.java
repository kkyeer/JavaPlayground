package concurrent.future.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: kkyeer
 * @Description: 测试CompletableFuture
 * @Date:Created in 17:03 2019/7/2
 * @Modified By:
 */
class TestCase {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Network result";
                }
        ).thenApply(
                String::toUpperCase
        ).thenApply(
                String::length
        );
        System.out.println(completableFuture.get());
    }
}
