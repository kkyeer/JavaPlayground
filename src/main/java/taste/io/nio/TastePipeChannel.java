package taste.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Author: kkyeer
 * @Description: 试用PipeChannel
 * @Date:Created in 20:40 10/3
 * @Modified By:
 */
class TastePipeChannel {
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        pipe.sink().write(ByteBuffer.wrap("My world".getBytes()));
        ByteBuffer dest = ByteBuffer.allocate(10);
        pipe.source().read(dest);
        dest.rewind();
        Util.printBuffer(dest);
    }
}
