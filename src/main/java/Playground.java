import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.URL;

/**
 * @Author: kkyeer
 * @Description: 代码操场随便玩
 * @Date:Created in 21:00 2018/12/18
 * @Modified By:
 */
public class Playground {

    public static void main(String[] args) throws IOException {


    }

    static void netBasic() throws IOException{
        Inet4Address inet4Address = (Inet4Address) Inet4Address.getLocalHost();
        System.out.println(inet4Address.getHostAddress());
        URL baiduUrl = new URL("http://www.baidu.com");
        System.out.println(baiduUrl.getProtocol());
        InputStream inputStream = baiduUrl.openStream();
    }
}
