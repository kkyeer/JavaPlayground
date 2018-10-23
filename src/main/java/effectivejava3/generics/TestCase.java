package effectivejava3.generics;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 20:42 2018/10/15
 * @Modified By:
 */
public class TestCase {


    static void unsafeAdd(List list, Object object) {
        list.add(object);
    }

    static void testInstanceOf(Object object) {
        if (object instanceof Set) {
            Set set = (Set) object;
        }
    }

    public static <T> Set<T>  union(Set<? extends T> s1, Set<? extends T> s2) {
//        use extends to generify method
        Set<T> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    public static void swap(List<?> list, int index1, int index2) {
        swapHelper(list,index1,index2);
    }

    private static <E> void  swapHelper(List<E> list, int index1, int index2){
        list.set(index1, list.set(index2, list.get(index1)));
    }

    public static <E> void sequencePrint(E... args) {
        for (E arg : args) {
            System.out.println(args);
        }
    }

    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = Arrays.asList(1,3);
        Object[] objects = stringLists;
        objects[0] = intList; // Heap pollution
        String s = stringLists[0].get(0); // ClassCastException
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
            default: return null;
        }
    }

    // UNSAFE - Exposes a reference to its generic parameter array!
    static <T> T[] toArray(T... args) {
        return args;
    }


    public static void main(String[] args) {
//        List<? extends Object> list1 ;
//        List<String> list2 = new ArrayList<>();
//        list1 = list2;
//        List list3 = new ArrayList();
//        unsafeAdd(list3,"asdf");
//        Integer i1 = (Integer) list3.get(0);
//
//
//        new ArrayList<String>().toArray(new String[20]);
//        Set<Integer> set1 = new HashSet<>();
//        set1.add(1);
//        set1.add(2);
//        Set<String> set2 = new HashSet<>();
//        set2.add("dskfjal");
//        Set<Object> un = union(set1, set2);
//        System.out.println(un);

//        test pickTwo
        Integer[] r1 = pickTwo(1, 2, 3);
        r1[0] = 5;


    }

}
