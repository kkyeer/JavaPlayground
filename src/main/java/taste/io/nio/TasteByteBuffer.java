package taste.io.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

/**
 * @Author: kkyeer
 * @Description: 试用ByteBuffer
 * @Date:Created in 11:03 2019/9/3
 * @Modified By:
 */
class TasteByteBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 写
        fillBuffer(buffer);
        // 读
        buffer.flip();
        printBuffer(buffer);
        // 清除
        buffer.clear();

        // compact
        fillBuffer(buffer);
        buffer.position(2).limit(5);
        buffer.compact();
        System.out.print("after compact:");
        buffer.flip();
        printBuffer(buffer);

        // mark
        buffer.clear();
        fillBuffer(buffer);
        buffer.position(2).limit(5);
        buffer.mark();
        System.out.print("before reset:");
        printBuffer(buffer);
//        buffer.reset();
        System.out.print("without reset:");
        printBuffer(buffer);
        System.out.print("reset:");
        buffer.reset();
        printBuffer(buffer);

        char[] chars = "Hello world".toCharArray();
        CharBuffer charBuffer = CharBuffer.wrap(chars, 2, 3);
        System.out.println("offset:" + charBuffer.arrayOffset());

        CharBuffer copy = charBuffer.duplicate();
        copy.clear();
        System.out.println("original:" + charBuffer.position() + "-" + charBuffer.limit());
        System.out.println("copy:" + copy.position() + "-" + copy.limit());

        CharBuffer readOnlyCopy = charBuffer.asReadOnlyBuffer();
        try {
            readOnlyCopy.put("c");
        } catch (ReadOnlyBufferException e) {
            System.out.println("Caught read-only exception");
        }

        CharBuffer slicedBuffer = charBuffer.slice();
        System.out.println("Sliced:" + slicedBuffer.position() + "-" + slicedBuffer.limit());
    }

    /**
     * 写入Buffer:"Hello"
     * @param buffer target buffer
     */
    private static void fillBuffer(ByteBuffer buffer) {
        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
    }

    /**
     * 打印Buffer内容，从position到limit
     * @param buffer target buffer
     */
    private static void printBuffer(ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            System.out.print((char)buffer.get());
        }
        System.out.println();
    }
}
