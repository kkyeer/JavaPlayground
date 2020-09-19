package lab.io.socket;

import lab.io.nio.NioUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @Author: kkyeer
 * @Description: Socket服务器定义
 * @Date:Created in 20:16 2019/8/31
 * @Modified By:
 */
interface SocketServer {
    String[] SERVER_MESSAGES = new String[]{"MSG0", "MSG1", "MSG2"};
    int PORT = 12315;

    /**
     * 服务器启动方法
     */
    void start();

    /**
     * 从Channel读取数据
     * @param channel 源channel
     */
    default void read(ReadableByteChannel channel){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        try {
            while (channel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                NioUtil.printBuffer(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向channel写入响应
     * @param channel
     */
    default void write(WritableByteChannel channel) {
        ByteBuffer byteBuffer;
        for (String serverMessage : SERVER_MESSAGES) {
            byteBuffer = ByteBuffer.wrap(serverMessage.getBytes());
            try {
                channel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("SENT");
    }
}
