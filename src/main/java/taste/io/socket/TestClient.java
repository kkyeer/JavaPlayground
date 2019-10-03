package taste.io.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: kkyeer
 * @Description: 测试服务器性能的SocketClient
 * @Date:Created in 20:37 2019/8/31
 * @Modified By:
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(12315));
        ByteBuffer byteBuffer = ByteBuffer.wrap("hhhhhh  elllllooooooo".getBytes());
        byteBuffer.rewind();
        socketChannel.write(byteBuffer);
    }
}
