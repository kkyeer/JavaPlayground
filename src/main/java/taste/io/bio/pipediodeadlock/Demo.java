package taste.io.bio.pipediodeadlock;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: 演示PipedInputStream和PipedOutputStream在同一个线程下使用造成死锁的情况
 * @Date:Created in 14:35 12-2
 * @Modified By:
 */
class Demo {
    public static void main(String[] args) throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        outputStream.connect(inputStream);
        byte[] writeData = new byte[1000];
        byte[] readBuffer = new byte[20];
        Arrays.fill(writeData, (byte) 1);
        int i = 1;
        outputStream.write(writeData);
        System.out.println("FW OK");
        while (i < 100) {
            inputStream.read(readBuffer,0,readBuffer.length);
            System.out.println("FR OK");
            outputStream.write(writeData);
            System.out.println("RW OK");
        }
    }
}
