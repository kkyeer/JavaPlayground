package concurrent.cas.stack;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: kkyeer
 * @Description: 使用CAS来实现一个栈stack
 * @Date:Created in 16:15 2019/7/12
 * @Modified By:
 */
class SafeStackUsingCAS<T> implements Stack<T>{
    class Node<T>{
        T value;
        Node next;

        Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private AtomicReference<Node<T>> top =new AtomicReference<>(null);

    /**
     * 向栈里增加一个元素
     *
     * @param value 值
     */
    @Override
    public void push(T value) {
        Node<T> old = top.get();
        Node<T> updateValue = new Node<>(value, old);
        // 如果CAS失败，就重新取最新的top对应的node，重新初始化
        while (!top.compareAndSet(old, updateValue)) {
            old = top.get();
            updateValue = new Node<>(value, old);
        }
    }

    /**
     * 从栈中弹出一个元素
     *
     * @return 返回栈顶元素
     */
    @Override
    public T pop() {
        // 1. 取当前的Node值
        // 2. 如果当前Node值为null，返回null
        // 3. 如果当前Node值不为null，则top通过CAS更新为top.next,如果CAS失败，则说明top有变化，则需要重新取top重新判断
        Node<T> current = top.get();
        if (current == null) {
            return null;
        }else {
            while (!top.compareAndSet(current, current.next)) {
                current = top.get();
                if (current == null) {
                    return null;
                }
            }
            return current.value;
        }
    }

    public static void main(String[] args) {
        TestCase.test(SafeStackUsingCAS.class);
        // 从性能测试数据来看，使用cas并没有极大提升性能，甚至有巨幅下降，原因猜测可能为测试代码中，并未涉及多次线程切换，但涉及多次CAS失败重试
    }

}
