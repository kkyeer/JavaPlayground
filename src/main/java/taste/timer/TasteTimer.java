package taste.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: kkyeer
 * @Description: 试用Timer类
 * @Date:Created in 19:16 12-10
 * @Modified By:
 */
public class TasteTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask =
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("timer triggered");
                    }
                };
        timer.schedule(timerTask,
                1000
        );
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Not time out,timer canceled");
            timer.cancel();
            timer = null;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("timer triggered");
                }
            },1000);
        }
    }
}
