package algorithm.exercise.chapter1.section3;

import java.util.Objects;
import java.util.Scanner;

public class Exercise1_3_40_MoveToFront_LRU<T> {
    private int size = 0;

    public UnidirectionalLinkedNode<T> last;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return this.size;
    }

    public void enqueue(T data) {
        if (isEmpty()) {
            init(data);
        } else {
            if(!lru(data)){
                UnidirectionalLinkedNode<T> insertNode = new UnidirectionalLinkedNode<>();
                insertNode.data = data;
                insertNode.next = last.next;
                last = insertNode;
                size++;
            };
        }
    }

    private boolean lru(T data) {
        UnidirectionalLinkedNode<T> current = last;
        UnidirectionalLinkedNode<T> prev = last;
        for (int i = 0; i < size; i++) {
            if(Objects.equals(current.data, data)){
                prev.next = current.next;
                last.next = current;
                last = current;
                return true;
            }
        }
        return false;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = last.next.data;
        if (size == 1) {
            last = null;
        } else {
            last.next = last.next.next;
        }
        size--;
        return data;
    }

    private void init(T data) {
        if (isEmpty()) {
            last = new UnidirectionalLinkedNode<>();
            last.data = data;
            last.next = null;
            size++;
        }
    }

    public static void main(String[] args) {
        Exercise1_3_40_MoveToFront_LRU<String> lruCache = new Exercise1_3_40_MoveToFront_LRU<>();
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(!"/".equals(line)){
            lruCache.enqueue(line);
            line = scanner.nextLine();
        }
        scanner.close();
        for (int i = 0; i < lruCache.size; i++) {
            System.out.println(lruCache.dequeue());
        }
    }
}
