package algorithm.exercise.chapter1.section3;

import java.util.Objects;
import java.util.Scanner;

public class Exercise1_3_40_MoveToFront_LRU<T> {
    private int size = 0;

    public UnidirectionalLinkedNode<T> first;

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
                insertNode.next = first;
                first = insertNode;
                size++;
            };
        }
    }

    private boolean lru(T data) {
        UnidirectionalLinkedNode<T> current = first;
        UnidirectionalLinkedNode<T> prev = null;
        for (int i = 0; i < size; i++) {
            if(Objects.equals(current.data, data)){
                if (current == first) {
                    return true;
                }
                prev.next = current.next;
                current.next = first;
                first = current;
                return true;
            }else{
                prev = current;
                current = current.next;
            }
        }
        return false;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = first.data;
        first = first.next;
        size--;
        return data;
    }

    private void init(T data) {
        if (isEmpty()) {
            first = new UnidirectionalLinkedNode<>();
            first.data = data;
            first.next = null;
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
        while (!lruCache.isEmpty()){
            System.out.println(lruCache.dequeue());
        }
    }
}
