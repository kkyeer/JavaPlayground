package taste.io.socket;

/**
 * @Author: kkyeer
 * @Description: Socket服务器定义
 * @Date:Created in 20:16 2019/8/31
 * @Modified By:
 */
interface SocketServer {
    static final String[] SERVER_MESSAGES = new String[]{"MSG0", "MSG1", "MSG2"};
    static final int PORT = 12315;

    /**
     * 服务器启动方法
     */
    void start();
}
