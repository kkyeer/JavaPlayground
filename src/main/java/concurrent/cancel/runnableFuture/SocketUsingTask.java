package concurrent.cancel.runnableFuture;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @Author: kkyeer
 * @Description: 可取消的工作的实现类
 * @Date:Created in 10:50 2019/5/22
 * @Modified By:
 */
abstract class SocketUsingTask<T> implements CancellableTask<T> {
    private Socket socket;

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 取消任务
     *
     * @return 取消请求是否发送成功
     */
    @Override
    public synchronized boolean cancel() {
        System.out.println("closing socket");
        try {
            socket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 新任务
     *
     * @return
     */
    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            public boolean cancel() {
                try {
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(true);
                }
            }
        };
    }
}
