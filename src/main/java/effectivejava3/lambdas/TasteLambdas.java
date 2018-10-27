package effectivejava3.lambdas;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Supplier;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 21:54 2018/10/26
 * @Modified By:
 */
public class TasteLambdas {
    public static void main(String[] args) {
        // Sort中使用Lambda的各种姿势
        List<String> words = Arrays.asList("123513425", "askldfj", "oyui");
        Collections.sort(words,(s1,s2)->{return s1.length()-s2.length();});
        Collections.sort(words,(s1,s2)->Integer.compare(s1.length(),s2.length()));
        Collections.sort(words, Comparator.comparingInt(String::length));
        words.sort(Comparator.comparingInt(String::length));
        System.out.println(words);
        System.out.println("-----------Sort end------------");

//        map.merge方法
        Map<String, Integer> keyCounter = new HashMap<>(2);
        keyCounter.put("abc", 1);
        keyCounter.merge("abc", 1, (oldValue, value) -> oldValue + value);
        keyCounter.merge("def",1,Integer::sum);
        System.out.println(keyCounter);
        System.out.println("-----------map merge end -------");

        variousMethodReferenceTypes();
    }

    /**
     * 各种各样的Lambda方法引用类型
     * @author kkyeer
     * @date 2018/10/26 23:22
     */
    private static void variousMethodReferenceTypes() {
        //      类构造器引用类型
        List<String> stringsCopy = copy(Arrays.asList("123513425", "askldfj", "oyui"), ArrayList::new);
        System.out.println(stringsCopy);
    }

    /**
     * 拷贝一个Collection到新的Collection
     * @author kkyeer
     * @date 2018/10/26 23:23
     * @param ori 原
     * @param supplier 构造器Lambda表达式
     * @param <T> Collection内部泛型
     * @param <E> Collection泛型
     * @return 拷贝的Collection
     */
    private static  <T,E extends Collection<T>> E copy(E ori, Supplier<E> supplier) {
        E newInstance = supplier.get();
        newInstance.addAll(ori);
        return newInstance;
    }
}
