package lab.hocon;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @Author: kkyeer
 * @Description: 试用HOCON
 * @Date:Created in 14:40 12-2
 * @Modified By:
 */
class Demo {
    public static void main(String[] args) {
        Config hoConfig = ConfigFactory.load("hocon.conf");
        System.out.println(hoConfig.getBoolean("a.enable"));
        System.out.println(hoConfig.getString("a.c"));
    }
}
