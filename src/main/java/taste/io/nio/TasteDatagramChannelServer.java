package taste.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: kkyeer
 * @Description: 试用DatagramChannel
 * @Date:Created in 18:20 10/3
 * @Modified By:
 */
class TasteDatagramChannelServer {
    private static final String heartbeatPacket = "Hello everyone~";

    private static void startServer() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress(12315));
        while (true) {
//                            Util.printBuffer(toWrite);
            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            SocketAddress remoteAddress = datagramChannel.receive(readBuffer);
            readBuffer.flip();
            System.out.print("Receive from client:");
            NioUtil.printBuffer(readBuffer);
            Timer timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                datagramChannel.send((ByteBuffer) ByteBuffer.wrap(heartbeatPacket.getBytes()).rewind(), remoteAddress);
                                System.out.println("Sent to remote:" + remoteAddress);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    1000,
                    1000
            );
        }

    }


    public static void main(String[] args) throws IOException {
        startServer();
    }
}
