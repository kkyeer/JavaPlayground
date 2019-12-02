package concurrent.interupt;

/**
 * @Author: kkyeer
 * @Description: 测试下Catch Interupted Exception是否会阻止线程结束
 * @Date:Created in 11:18 11-8
 * @Modified By:
 */
public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread(
                ()->{
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            System.out.println("alive");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.interrupted();
                    }
                }
        );
        try {
            thread.start();
            Thread.sleep(5000);
            thread.interrupt();
        }catch (InterruptedException ex){

        }
    }
}
