package taste.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kkyeer
 * @Description: 服务器端，基于NIO多路复用,对于每个建立连接的客户端，发送["MSG0", "MSG1", "MSG2"]
 * @Date:Created in 20.25 2019/8/31
 * @Modified By:
 */
class SocketServerWithNIO implements SocketServer{
    @Override
    public void start() {
        System.out.println("Server start ......");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            ExecutorService ioThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>());
            ioThreadPool.submit(
                    () -> {
                        try {
                            Selector selector = Selector.open();
                            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
                            serverSocketChannel.configureBlocking(false);
                            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                            while (true) {
                                selector.select();
                                Set<SelectionKey> selectionKeySet = selector.keys();
                                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                                while (iterator.hasNext()) {
                                    SelectionKey selectionKey = iterator.next();
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    for (String serverMessage : SERVER_MESSAGES) {

                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Got a connection from client");
                PrintWriter echoWriter = new PrintWriter(socket.getOutputStream());
                // 读线程
                ioThreadPool.submit(
                        () -> {
                            try {
                                // 对于客户端发送过来的信息，发送已读回执
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                String buf;
                                while ((buf = bufferedReader.readLine()) != null) {
                                    System.out.println("received:[" + buf + "]");
                                    echoWriter.println("Server received from Client:[" + buf + "]");
                                    echoWriter.flush();
                                }
                            } catch (IOException e) {
//                                e.printStackTrace();
                            } finally {
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                // 写线程
                ioThreadPool.submit(
                        () -> {
                            // 发送信息：["消息1","消息2","消息3"]，间隔1秒
                            for (String serverMessage : SERVER_MESSAGES) {
                                System.out.println("Pushing message:" + serverMessage);
                                echoWriter.println(serverMessage);
                                echoWriter.flush();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
        }

    }
}
