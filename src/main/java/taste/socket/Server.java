package taste.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: kkyeer
 * @Description: 服务器端
 * @Date:Created in 10:00 2019/6/2
 * @Modified By:
 */
class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("prepare to start......");
        ServerSocket serverSocket = new ServerSocket(12315);
        ExecutorService socketThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>());
        while (true) {
            Socket socket = serverSocket.accept();
            socketThreadPool.execute(
                    ()->{
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter echoWriter = new PrintWriter(socket.getOutputStream());
                            String buf;
                            System.out.println("receiving......");
                            while ((buf = bufferedReader.readLine()) != null) {
                                echoWriter.println("received:[" + buf + "]");
                                echoWriter.flush();
                            }
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

    }
}
