package concurrent.serialgenerator;

/**
 * @Author: kkyeer
 * @Description: 序列生成器
 * @Date:Created in 14:19 2019/4/15
 * @Modified By:
 */
class UnsafeSerialGenerator implements SerialGenerator {
    private int index=0;

    @Override
    public int getNext(){
        index = index+1;
        return index;
    }
}
