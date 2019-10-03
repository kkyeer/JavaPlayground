package taste.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * @Author: kkyeer
 * @Description: 客户端
 * @Date:Created in 10:00 2019/6/2
 * @Modified By:
 */
class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 12315);
        new Thread(
                ()->{
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String buf;
                        while ((buf = bufferedReader.readLine()) != null) {
                            System.out.println("Client received from server:" + buf);
                            System.out.flush();
                        }
                    } catch (SocketException e) {
                        System.out.println("socket 状态错误:"+e.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
        new Thread(
                ()->{
                    try {
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                        Scanner scanner = new Scanner(System.in);
                        String nextUserInput;
                        while (!(nextUserInput = scanner.next()).equals("done")) {
                            printWriter.println(nextUserInput);
                            printWriter.flush();
                        }
                        scanner.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
}
