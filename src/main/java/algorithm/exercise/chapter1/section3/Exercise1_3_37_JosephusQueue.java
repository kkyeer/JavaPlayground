package algorithm.exercise.chapter1.section3;

public class Exercise1_3_37_JosephusQueue<E> {

    private Integer size = 0;

    private UnidirectionalLinkedNode<E> last = null;

    public boolean isEmpty() {
        return size == 0;
    }

    public Exercise1_3_37_JosephusQueue<E> enqueue(E data) {
        UnidirectionalLinkedNode<E> temp = new UnidirectionalLinkedNode<>();
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
        Exercise1_3_37_JosephusQueue<Integer> queue = new Exercise1_3_37_JosephusQueue<>();
        int m = 7;
        int n = 4;
        for (int i = 0; i < m; i++) {
            queue.enqueue(i);
        }
        while (!queue.isEmpty()) {
            queue.forward(n - 1);
            System.out.println(queue.dequeue());
        }
    }
}
