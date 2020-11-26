package lab.io.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 试一下Channel
 * @Date:Created in 10:39 2019/9/4
 * @Modified By:
 */
class TasteChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
//        testFileChannel();
//        ReadableByteChannel inChannel = Channels.newChannel(System.in);
//        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
//        new Thread(
//                ()-> channelCopy1(inChannel, writableByteChannel)
//        ).start();
//        Thread.sleep(10000);
//        inChannel.close();

        scatter();
        gather();
    }

    static void  testFileChannel() throws IOException {
        File targetFile = new File(TasteChannel.class.getResource("/nio_source.txt").getFile());
        String toWriteContent = "Hello world!!!!\n";
        ByteBuffer buffer = ByteBuffer.wrap(toWriteContent.getBytes());
        System.out.println(buffer);

        FileInputStream fileInputStream = new FileInputStream(targetFile);
        FileChannel readOnlyFileChannel = fileInputStream.getChannel();
        try {
            readOnlyFileChannel.write(buffer);
        } catch (IOException e) {
//            TODO 这里为什么能捕获到非IOException类型？
//            if (e instanceof NonWritableChannelException) {
//                System.out.println("Channel 不可写");
//            }
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(TasteChannel.class.getResource("/nio_source.txt").getFile(), "rw");
        randomAccessFile.seek(randomAccessFile.length());
        FileChannel writableFileChannel = randomAccessFile.getChannel();
        try {
            writableFileChannel.write(buffer);
            writableFileChannel.close();
            System.out.println("RandomAccessFile 写入完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testOpen() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(12306));

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(12306));

        DatagramChannel datagramChannel = DatagramChannel.open();
        RandomAccessFile randomAccessFile = new RandomAccessFile("nio_source.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
    }

    /**
     * 将一个channel内部的内容拷贝到另外一个channel
     * @param srcChannel 源channel
     * @param destChannel 目标channel
     */
    static void channelCopy1(ReadableByteChannel srcChannel, WritableByteChannel destChannel) {
        ByteBuffer swapBuffer = ByteBuffer.allocateDirect(1024);
        try {
            while (srcChannel.isOpen() && srcChannel.read(swapBuffer) > -1) {
                swapBuffer.flip();
                destChannel.write(swapBuffer);
                swapBuffer.clear();
            }
        } catch (Exception e) {
            if (e instanceof AsynchronousCloseException) {
                System.out.println("Channel被异步关闭");
            }
        }
        System.out.println("Copy 完成");

    }

    /**
     * 从一个流中分散到多个buffer
     */
    private static void scatter() throws IOException {
        File sourceFile = new File(TasteChannel.class.getResource("/batch.txt").getFile());
        RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFile, "r");
        FileChannel sourceFileChannel = randomAccessFile.getChannel();
        ByteBuffer metaInfoByteBuffer = ByteBuffer.allocateDirect(10);
        ByteBuffer detailByteBuffer = ByteBuffer.allocateDirect(500);
        sourceFileChannel.read(new ByteBuffer[]{metaInfoByteBuffer, detailByteBuffer});
        System.out.println("--meta--" + metaInfoByteBuffer);
        metaInfoByteBuffer.flip();
        while (metaInfoByteBuffer.hasRemaining()) {
            System.out.print((char)metaInfoByteBuffer.get());
        }
        System.out.println("--detail--"+detailByteBuffer);
        detailByteBuffer.flip();
        while (detailByteBuffer.hasRemaining()) {
            System.out.print((char)detailByteBuffer.get());
        }
    }

    /**
     * 从多个ByteBuffer中汇聚到同一个流
     */
    private static void gather() throws IOException {
        File dest = new File(TasteChannel.class.getResource("/").getPath()+"out.txt");
        dest.createNewFile();
        FileChannel destFileChannel = new FileOutputStream(dest).getChannel();
        String[] adjs = "good,bad,nice,great".split(",");
        String[] nouns = "person,toy,book".split(",");
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ByteBuffer[] buffers = new ByteBuffer[]{ByteBuffer.wrap(adjs[random.nextInt(adjs.length)].getBytes()), ByteBuffer.wrap(" ".getBytes()), ByteBuffer.wrap(nouns[random.nextInt(nouns.length)].getBytes()), ByteBuffer.wrap("\n".getBytes())};
            destFileChannel.write(buffers);
        }
    }
}
