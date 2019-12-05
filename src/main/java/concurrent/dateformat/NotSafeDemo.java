package concurrent.dateformat;

import concurrent.ConcurrentTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: 演示SimpleDateFormat非线程安全
 * @Date:Created in 11:01 12-5
 * @Modified By:
 */
class NotSafeDemo {
    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("s");
        AtomicInteger second = new AtomicInteger(0);
        ConcurrentTest.test(
                ()-> {
                    int index = second.incrementAndGet();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.SECOND,index);
                    Date toFormat = calendar.getTime();
                    for (int i = 0; i < 10000; i++) {
                        String parsed = dateFormat.format(toFormat);
                        if (index != Integer.parseInt(parsed)) {
                            System.err.println(index + "-" + parsed);
                        }
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}
