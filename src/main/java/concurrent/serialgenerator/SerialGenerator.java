package concurrent.serialgenerator;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 16:19 2019/4/15
 * @Modified By:
 */
interface SerialGenerator {
    /**
     * 获取下一个序列号
     * @return 下一个序列号
     */
    int getNext();
}
