package lab.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

/**
 * @Author: kkyeer
 * @Description: 测试文件Lock-写程序
 * @Date:Created in 22:29 2019/9/4
 * @Modified By:
 */
class Writer {
    public static void main(String[] args) throws IOException, InterruptedException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(TasteChannel.class.getResource("/nio_source.txt").getFile(), "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        FileLock lock = fileChannel.lock(0, fileChannel.size(), false);
        System.out.println("Start write:" + new Date());
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello world!!!!!".getBytes());
        fileChannel.write(byteBuffer);
        Thread.sleep(10000);
        lock.release();
        System.out.println("Write Complete:"+new Date());
    }
}
