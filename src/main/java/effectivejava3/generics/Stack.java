package effectivejava3.generics;


import java.io.UncheckedIOException;
import java.util.*;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 20:13 2018/10/18
 * @Modified By:
 */
public class Stack<T> {
    private T[] array;
    private static final int INITIALIZE_CAPACITY = 16;
    private int index = 0;
    @SuppressWarnings("unchecked")
    public Stack() {
//        this.array = new T[INITIALIZE_CAPACITY];
        this.array = (T[]) new Object[INITIALIZE_CAPACITY];
    }

    public void push(T obj) {
        ensureCapacity();
        array[index++] = obj;
    }

    public T pop() {
        if (index != 0) {
            T obj = array[--index];
            array[index] = null;
            return obj;
        }
        throw new EmptyStackException();
    }

    private void ensureCapacity() {
        if (index == array.length) {
            array = Arrays.copyOf(array, 2 * index+1);
        }
    }

    public boolean isEmpty(){
        return index==0;
    }

    public void pushAll(Iterable<? extends T> src) {
//        need wild type to ensure subtype push work
        for (T t : src) {
            this.push(t);
        }
    }

    public void popAll(Collection<? super T> target){
//        need wild type to ensure super type push work
        while (!isEmpty()) {
            target.add(pop());
        }
    }

    public static void main(String[] args) {
        Stack<String> argStatck = new Stack<>();
        for (String arg : args) {
            argStatck.push(arg);
        }
        while (!argStatck.isEmpty()) {
            System.out.println(argStatck.pop());
        }

//      test push all
        Stack<Number> numberStack = new Stack<>();
        List<Integer> integers = Arrays.asList(1, 3, 5);
        numberStack.pushAll(integers);

//        TEST POP ALL
        List<Object> list2 = new ArrayList<>(3);
        numberStack.popAll(list2);

    }
}
