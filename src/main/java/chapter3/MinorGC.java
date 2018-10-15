package chapter3;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:56 PM 9/20/18
 * @Modified By:
 */
public class MinorGC {
    private static final int _1MB = 1024 * 1024;
// -verbose:gc -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC
    public static void main(String[] args) {
        byte[] allo1,allo2,allo3,allo4;
        allo1 = new byte[_1MB * 2];
        allo2 = new byte[_1MB * 2];
        allo3 = new byte[_1MB * 2];
        allo4 = new byte[_1MB * 4]; // eden space 1/9 * 10M , not enough ,minor gc needed
    }
}
