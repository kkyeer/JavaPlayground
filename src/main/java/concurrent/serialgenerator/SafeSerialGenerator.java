package concurrent.serialgenerator;

/**
 * @Author: kkyeer
 * @Description: 序列生成器
 * @Date:Created in 14:19 2019/4/15
 * @Modified By:
 */
class SafeSerialGenerator implements SerialGenerator {
    private int index=0;
    @Override
    public synchronized int getNext(){
        index = index+1;
        return index;
    }
}
