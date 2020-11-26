package lab.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
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
class TasteDatagramChannelClient {
    private static final String hello = "Hello server~";

    private static void simuClients() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.connect(new InetSocketAddress("localhost", 12315));
        datagramChannel.configureBlocking(false);
        datagramChannel.write((ByteBuffer) ByteBuffer.wrap(hello.getBytes()).rewind());
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                            byteBuffer.clear();
                            datagramChannel.read(byteBuffer);
                            byteBuffer.flip();
                            System.out.print(Thread.currentThread().getName()+" received: ");
                            NioUtil.printBuffer(byteBuffer);
                        } catch (IOException e) {
                            System.out.println("exception");
                            e.printStackTrace();
                        }
                    }
                },
                1000,
                1000
        );
    }




    public static void main(String[] args) throws IOException {
        simuClients();
    }
}
