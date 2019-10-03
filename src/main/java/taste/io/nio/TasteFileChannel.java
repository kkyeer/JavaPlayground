package taste.io.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: kkyeer
 * @Description: FileChannel相关基础
 * @Date:Created in 17:29 2019/9/4
 * @Modified By:
 */
class TasteFileChannel {
    public static void main(String[] args) throws IOException {
        File testReadFile = new File(TasteFileChannel.class.getResource("/nio_source.txt").getPath());
        testSyncPosition(testReadFile);
        File possiblyHoleFile = new File(TasteFileChannel.class.getResource("/").getPath() + "possibly_hole.txt");
        possiblyCreateFileHole(possiblyHoleFile);
    }

    /**
     * File Channel对象和底层的源，position是同步变化的
     * @param testFile 文件
     * @throws IOException 异常
     */
    private static void testSyncPosition(File testFile) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(testFile, "r");
        FileChannel readChannel = randomAccessFile.getChannel();
        System.out.println("Position:"+randomAccessFile.getFilePointer()+"-"+readChannel.position());
        randomAccessFile.seek(500);
        System.out.println("Position:"+randomAccessFile.getFilePointer()+"-"+readChannel.position());
        readChannel.position(1000);
        System.out.println("Position:"+randomAccessFile.getFilePointer()+"-"+readChannel.position());
    }

    /**
     * 可能会创造File Hole，与操作系统有关，现象是文件大小和占用大小完全不成比例，文件大小50MB，实际占用几byte
     *
     * @param destFile 目标文件
     * @throws IOException 发生异常时
     */
    private static void possiblyCreateFileHole(File destFile) throws IOException {
        FileChannel fileChannel = new FileOutputStream(destFile).getChannel();
        fileChannel.write(ByteBuffer.wrap("Begin".getBytes()));
        fileChannel.position(5000000);
        fileChannel.write(ByteBuffer.wrap("Middle".getBytes()));
        fileChannel.position(50000000);
        fileChannel.write(ByteBuffer.wrap("End".getBytes()));
        fileChannel.close();
    }
}
