package utils;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 工具类
 * @Date:Created in  2020-1-19 15:05
 * @Modified By:
 */
public class Utils {
    public static void printArray(Object[] arr) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Object o : arr) {
            stringJoiner.add(o.toString());
        }
        System.out.println(stringJoiner.toString());
    }

    public static void printArray(int[] arr) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Object o : arr) {
            stringJoiner.add(o.toString());
        }
        System.out.println(stringJoiner.toString());
    }
}
