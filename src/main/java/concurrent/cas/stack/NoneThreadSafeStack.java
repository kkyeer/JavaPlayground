package concurrent.cas.stack;

/**
 * @Author: kkyeer
 * @Description: 单线程的stack
 * @Date:Created in 16:15 2019/7/12
 * @Modified By:
 */
class NoneThreadSafeStack<T> implements Stack<T>{
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
    public void push(T value) {
        top = new Node<>(value, top);
    }

    /**
     * 从栈中弹出一个元素
     *
     * @return 返回栈顶元素
     */
    @Override
    public T pop() {
        T retValue = null;
        if (top != null) {
            retValue = top.value;
            top = top.next;
        }
        return retValue;
    }

    public static void main(String[] args) {
        TestCase.test(NoneThreadSafeStack.class);
    }
}
