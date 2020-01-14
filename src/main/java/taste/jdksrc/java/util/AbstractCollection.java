package taste.jdksrc.java.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: AbstractCollection的实验代码
 * @Date:Created in  2020-1-14 14:47
 * @Modified By:
 */
public class AbstractCollection {
    public static void main(String[] args) throws InterruptedException {
        Collection<Integer> collection = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            collection.add(i);
        }

        // 传入数组大小足够的情况，运行正常
        Integer[] canFitArray = new Integer[10];
        collection.toArray(canFitArray);
        printArray(canFitArray);

        // 传入数组大小不够的情况，运行错误
        Integer[] canNotFitArray = new Integer[2];
        collection.toArray(canNotFitArray);
        printArray(canNotFitArray);
    }

    private static void printArray(Integer[] array) {
        StringJoiner printedString = new StringJoiner(",");
        for (Integer integer : array) {
            printedString.add(integer == null ? "null" : "" + integer);
        }
        System.out.println(printedString);
    }
}
