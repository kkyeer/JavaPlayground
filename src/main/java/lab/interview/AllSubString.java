package lab.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 实现函数, 给定一个字符串数组, 求该数组的连续非空子集，分別打印出来各子集 ，举例数组为[abc]，输出[a],[b],[c],[ab],[bc],[abc]
 * @Date:Created in 下午6:34 2021/3/7
 * @Modified By:
 */
public class AllSubString {
    public void printAllSubString(String s) {
        for (int i = 0; i < s.length(); i++) {
            StringJoiner stringJoiner = new StringJoiner(",");
            for (int j = i; j < s.length(); j++) {
                stringJoiner.add(s.substring(i, j + 1));
            }
            System.out.println(stringJoiner.toString());
        }
    }

    private void dfs(String s, int i) {


    }


    public static void main(String[] args) {
        new AllSubString().printAllSubString("abcded");
    }
}
