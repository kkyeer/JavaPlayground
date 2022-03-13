package algorithm.exercise.chapter1.section3;

import java.util.Iterator;

public class Excercise1_3_29_CircularQueue<E> implements Iterable<E> {
    private class LinkedNode<T> {
        public T data;
        public LinkedNode<T> next;
    }

    private Integer size = 0;

    private LinkedNode<E> last = null;

    public boolean isEmpty() {
        return size == 0;
    }

    public Excercise1_3_29_CircularQueue<E> enqueue(E data) {
        LinkedNode<E> temp = new LinkedNode<>();
        temp.data = data;
        if (isEmpty()) {
            last = temp;
            last.next = last;
        } else {
            temp.next = last.next;
            last.next = temp;
            last = temp;
        }
        size++;
        return this;
    }

    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        E data = last.next.data;
        if (size == 1) {
            last = null;
        } else {
            last.next = last.next.next;
        }
        size--;
        return data;
    }

    private class CircularQueueIterator<T> implements Iterator<T> {
        private Excercise1_3_29_CircularQueue<T>.LinkedNode<T> current;
        private int times = 0;
        private final int size;

        public CircularQueueIterator(Excercise1_3_29_CircularQueue<T> queue) {
            this.size = queue.size;
            this.current = queue.isEmpty() ? null : queue.last.next;
        }

        @Override
        public boolean hasNext() {
            return current != null && times < size;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            times++;
            return data;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularQueueIterator<>(this);
    }

    public static void main(String[] args) {
        Excercise1_3_29_CircularQueue<Integer> queue = new Excercise1_3_29_CircularQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        // System.out.println(queue.dequeue());
        // System.out.println(queue.dequeue());
        // System.out.println(queue.dequeue());
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
