package concurrent.cas.stack;

/**
 * @Author: kkyeer
 * @Description: 栈的接口
 * @Date:Created in 10:43 2019/7/20
 * @Modified By:
 */
interface Stack<T>{
    /**
     * 向栈里增加一个元素
     *
     * @param value 值
     */
    void push(T value);

    /**
     * 从栈中弹出一个元素
     * @return 返回栈顶元素
     */
    T pop();
}
