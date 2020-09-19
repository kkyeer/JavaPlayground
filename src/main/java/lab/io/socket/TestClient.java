package lab.io.socket;

import lab.io.nio.NioUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;

/**
 * @Author: kkyeer
 * @Description: 测试服务器性能的SocketClient
 * @Date:Created in 20:37 2019/8/31
 * @Modified By:
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(12315));
        Random random = new Random();
        String content = "index"+random.nextInt(100);
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
        socketChannel.write(byteBuffer);
        System.out.println("SENT:" + content);
        byteBuffer.clear();
        while (socketChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            NioUtil.printBuffer(byteBuffer);
            byteBuffer.clear();
        }
    }
}
