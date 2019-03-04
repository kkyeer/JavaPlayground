package taste.playground;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 23:02 2019/2/26
 * @Modified By:
 */
public class tasteJIT {
    private static void testNonBracked(){
        long startTime = System.nanoTime();
        int n = 0;
        for (int i = 0; i < 1000000000; i++) {
            n+=2*i*i;
        }
        System.out.println("Time:"+(System.nanoTime()-startTime)/1000+"ms");
    }

    private static void testWithBracked(){
        long startTime = System.nanoTime();
        int n = 0;
        for (int i = 0; i < 1000000000; i++) {
            n+=2*(i*i);
        }
        System.out.println("Time:"+(System.nanoTime()-startTime)/1000+"ms");
    }
}
