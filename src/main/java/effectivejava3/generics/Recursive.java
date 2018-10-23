package effectivejava3.generics;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:54 2018/10/18
 * @Modified By:
 */
public class Recursive {
    /**
     * any type E that can be compared to itself
     * @author kkyeer
     * @date 2018/10/18 21:57
     * @param collection in collection
     * @param <E> recursive parameter type
     * @return E max element
     */
    public static <E extends Comparable<E>> E max(Collection<E> collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Iterator<E> iterator = collection.iterator();
        E current = iterator.next();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (next.compareTo(current)>0) {
                current = next;
            }
        }
        return current;
    }

    private static class BadExample implements Comparable{
        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 3, 5);
        Integer max = max(integers);
        Object max2 = max(integers);

        BadExample a1 = new BadExample();
        BadExample a2 = new BadExample();
        List<BadExample> badExamples = Arrays.asList(a1, a2);
        BadExample result = max(badExamples);
    }
}
