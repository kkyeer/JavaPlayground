package taste.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

/**
 * @Author: kkyeer
 * @Description: 测试文件Lock-读程序
 * @Date:Created in 22:29 2019/9/4
 * @Modified By:
 */
class Reader {
    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(TasteChannel.class.getResource("/nio_source.txt").getFile(), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock(0, fileChannel.size(), true);
        System.out.println("Start Read:" + new Date());
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        fileChannel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.print((char) byteBuffer.get());
        }
        System.out.println();
        Thread.sleep(10000);
        lock.release();
        System.out.println("Read Complete:" + new Date());
    }
}
