package knowledge.list2array;

import java.util.ArrayList;

/**
 * @Author: kkyeer
 * @Description: 演示两种不同的ArrayList转数组的方法
 * @Date:Created in 10:42 11-8
 * @Modified By:
 */
class Demo {
    public static void main(String[] args) {
        ArrayList<String> original = new ArrayList<>();
        original.add("aa");
        original.add("bb");
        String[] result = (String[]) original.toArray();
        result = original.toArray(new String[0]);
    }
}
