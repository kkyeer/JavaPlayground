package taste.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: kkyeer
 * @Description: 试用ServerSocketChannel
 * @Date:Created in 22:40 9/29
 * @Modified By:
 */
public class TasteServerSocketChannel {
    private static final String GREETING = "Hello buddy!\r\n";
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(12315));
        serverSocketChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                
                System.out.println("no connection");
                Thread.sleep(2000);
            }else {
                System.out.println("Got connection");
                buffer.rewind();
                socketChannel.write(buffer);
            }
        }
    }
}
