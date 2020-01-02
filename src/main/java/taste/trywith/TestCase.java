package taste.trywith;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: kkyeer
 * @Description: 测试trywith会不会吞掉异常
 * @Date:Created in 9:13 12-11
 * @Modified By:
 */
@Slf4j
public class TestCase {
    public static void main(String[] args) {
        try (TestStream testStream =new TestStream()) {
            throw new RuntimeException("读取过程日常");
        } catch (Exception e) {
            log.error("读取异常", e);
        }

    }
}
