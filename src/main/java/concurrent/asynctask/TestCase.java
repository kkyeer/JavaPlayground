package concurrent.asynctask;

import java.util.concurrent.ExecutionException;

/**
 * @Author: kkyeer
 * @Description: 测试用例
 * @Date:Created in 1:02 2019/5/17
 * @Modified By:
 */
public class TestCase {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebDataLoader loader = new WebDataLoader();
        System.out.println(loader.getResult());
    }
}
