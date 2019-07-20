package concurrent.cas.stack;

/**
 * @Author: kkyeer
 * @Description: 使用内部锁机制(synchronized关键字)来保证线程安全
 * @Date:Created in 16:15 2019/7/12
 * @Modified By:
 */
class SafeStackUsingIntrinsicLock<T> implements Stack<T>{
    class Node<T>{
        T value;
        Node next;

        Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> top = null;

    /**
     * 向栈里增加一个元素
     *
     * @param value 值
     */
    @Override
    public synchronized void push(T value) {
        top = new Node<>(value, top);
    }

    /**
     * 从栈中弹出一个元素
     *
     * @return 返回栈顶元素
     */
    @Override
    public synchronized T pop() {
        T retValue = null;
        if (top != null) {
            retValue = top.value;
            top = top.next;
        }
        return retValue;
    }

    public static void main(String[] args) {
        TestCase.test(SafeStackUsingIntrinsicLock.class);
    }
}
