package chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 11:01 PM 9/18/18
 * @Modified By:
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024*1024;

//    -Xmx20M -XX:MaxDirectMemorySize=10M
    public static void main(String[] args) throws IllegalAccessException {
        // run fail
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
