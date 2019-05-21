package concurrent.jdbcpool;

import java.util.concurrent.Semaphore;

/**
 * @Author: kkyeer
 * @Description: 使用Semaphore来模拟一个简单的线程池，假设使用固定大小的线程池
 * @Date:Created in 19:11 2019/5/17
 * @Modified By:
 */
class SimuJDBCPool {
    /**
     * semaphore
     */
    private Semaphore semaphore;


    public SimuJDBCPool(int maxSize) {
        this.semaphore = new Semaphore(maxSize);
        for (int i = 0; i < maxSize; i++) {
//            this.idle.add(i);
        }
    }

    public String getConnection() throws Exception {
        try {
            this.semaphore.acquire();
            synchronized (this){

            }
            return "这是新链接";
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void releaseConnection() {
        this.semaphore.release();
    }


}
