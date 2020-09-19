package lab.io.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: kkyeer
 * @Description: 服务器端，基于NIO多路复用,对于每个建立连接的客户端，发送["MSG0", "MSG1", "MSG2"]
 * @Date:Created in 20.25 2019/8/31
 * @Modified By:
 */
class SocketServerWithNIO implements SocketServer{
    private static final ExecutorService backgroundService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    @Override
    public void start() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int count = selector.select();
                if (count == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel originalChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = originalChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ + SelectionKey.OP_WRITE);
                    }
                    if (selectionKey.isReadable()&&selectionKey.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        read(socketChannel);
                        write(socketChannel);
                        socketChannel.close();
                        selectionKey.cancel();
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new SocketServerWithNIO().start();
    }
}
