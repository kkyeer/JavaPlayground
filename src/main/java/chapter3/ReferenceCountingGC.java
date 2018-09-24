package chapter3;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 10:20 PM 9/19/18
 * @Modified By:
 */
public class ReferenceCountingGC {
    public Object ref;
    private byte[] someProp = new byte[2*1024 * 1024];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        // create cross reference
        objA.ref = objB;
        objB.ref = objA;
        // trigger gc
        System.gc();
    }
}
