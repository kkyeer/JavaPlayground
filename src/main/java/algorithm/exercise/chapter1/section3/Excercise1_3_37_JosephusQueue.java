package algorithm.exercise.chapter1.section3;

public class Excercise1_3_37_JosephusQueue<E> {
    private class LinkedNode<T> {
        public T data;
        public LinkedNode<T> next;
    }

    private Integer size = 0;

    private LinkedNode<E> last = null;

    public boolean isEmpty() {
        return size == 0;
    }

    public Excercise1_3_37_JosephusQueue<E> enqueue(E data) {
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

    public void forward(Integer step) {
        if (isEmpty()) {
            return;
        }
        for (int i = 0; i < step; i++) {
            last = last.next;
        }
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

    public static void main(String[] args) {
        Excercise1_3_37_JosephusQueue<Integer> queue = new Excercise1_3_37_JosephusQueue<>();
        int m = 7;
        int n = 2;
        for (int i = 0; i < m; i++) {
            queue.enqueue(i);
        }
        queue.forward(1);
        do {
            System.out.println(queue.dequeue());
            queue.forward(n - 1);
        } while (!queue.isEmpty());
    }
}
