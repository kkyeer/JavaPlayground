package taste.jdk;

import concurrent.annotations.NotThreadSafe;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 试一试StringJoiner
 * @Date:Created in 22:19 2019/5/27
 * @Modified By:
 */
@NotThreadSafe
class TasteStringJoiner {
    public static void main(String[] args) {
        StringJoiner stringJoiner = new StringJoiner(",","[","]");
        stringJoiner.add("a");
        stringJoiner.add("b");
        stringJoiner.add("c");
        System.out.println(stringJoiner.toString());
    }
}
