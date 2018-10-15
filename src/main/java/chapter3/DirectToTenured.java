package chapter3;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:33 PM 9/21/18
 * @Modified By:
 */
public class DirectToTenured {
    private static final int _1MB = 1024 * 1024;
//  -verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:PretenureSizeThreshold=3145728
    public static void main(String[] args) {
        byte[] allo1;
        allo1 = new byte[_1MB * 4]; // bigger than 3MB direct to Tenured Gen
    }
}
