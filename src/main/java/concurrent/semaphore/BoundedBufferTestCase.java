package concurrent.semaphore;

/**
 * @Author: kkyeer
 * @Description: 测试一下有界阻塞式缓存
 * @Date:Created in 11:09 2019/6/9
 * @Modified By:
 */
class BoundedBufferTestCase {
    private static final int BUFFER_SIZE = 100;
    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer<String> boundedBuffer = new BoundedBuffer<>(BUFFER_SIZE);
        System.out.println("--------------before put");
        System.out.println("Empty?"+boundedBuffer.isEmpty());
        System.out.println("Full?"+boundedBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            boundedBuffer.put("" + i);
        }
        System.out.println("--------------after put");
        System.out.println("Empty?"+boundedBuffer.isEmpty());
        System.out.println("Full?"+boundedBuffer.isFull());
        for (int i = 0; i < BUFFER_SIZE; i++) {
            boundedBuffer.take();
        }
        System.out.println("---------------after take");
        System.out.println("Empty?"+boundedBuffer.isEmpty());
        System.out.println("Full?"+boundedBuffer.isFull());
        
    }
}
