package taste.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 服务器端使用BIO实现, 对于每个建立连接的客户端，发送["MSG0", "MSG1", "MSG2"]，服务器端没有超时机制
 * @Date:Created in 10:00 2019/6/2
 * @Modified By:
 */
class SocketServerWithBIO implements SocketServer {
    @Override
    public void start() {
        System.out.println("Server start ......");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            ExecutorService ioThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>());
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
