package jvmdeepunderstanding.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 9:52 PM 9/17/18
 * @Modified By:
 */
public class RuntimeConstantPoolOOM {
//    -XX:PermSize=10M -XX:MaxPermSize=10M
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
