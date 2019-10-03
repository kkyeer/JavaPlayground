package taste.io.nio;

import java.nio.ByteBuffer;

/**
 * @Author: kkyeer
 * @Description: 辅助工具类
 * @Date:Created in 18:49 10/3
 * @Modified By:
 */
class Util {
    /**
     * 打印Buffer内容，从position到limit
     * @param buffer target buffer
     */
    static void printBuffer(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print((char)buffer.get());
        }
        System.out.println();
    }
}
