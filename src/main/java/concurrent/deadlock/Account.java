package concurrent.deadlock;

/**
 * @Author: kkyeer
 * @Description: 账户接口
 * @Date:Created in 17:21 2019/6/4
 * @Modified By:
 */
interface Account {
    void transfer(Account target, int amount);
}
