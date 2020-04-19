package leetcode;

import utils.Assertions;

/**
 * @author kkyeer
 * @description: 466. 统计重复个数
 * 由 n 个连接的字符串 s 组成字符串 S，记作 S = [s,n]。例如，["abc",3]=“abcabcabc”。
 *
 * 如果我们可以从 s2 中删除某些字符使其变为 s1，则称字符串 s1 可以从字符串 s2 获得。例如，根据定义，"abc" 可以从 “abdbec” 获得，但不能从 “acbbe” 获得。
 *
 * 现在给你两个非空字符串 s1 和 s2（每个最多 100 个字符长）和两个整数 0 ≤ n1 ≤ 106 和 1 ≤ n2 ≤ 106。现在考虑字符串 S1 和 S2，其中 S1=[s1,n1] 、S2=[s2,n2] 。
 *
 * 请你找出一个可以满足使[S2,M] 从 S1 获得的最大整数 M 。
 *
 *
 *
 * 示例：
 *
 * 输入：
 * s1 ="acb",n1 = 4
 * s2 ="ab",n2 = 2
 *
 * 返回：
 * 2
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-the-repetitions
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date:Created in 17:26 4-19
 * @modified By:
 */
public class MaxRepetitions_466 {
    public static void main(String[] args) {
        String s1 = "acb";
        int n1 = 4;
        String s2 = "ab";
        int n2 = 2;
        Assertions.equal(2, new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));


        s1 = "acb";
        n1 = 1;
        s2 = "acb";
        n2 = 1;
        Assertions.equal(1, new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));

        s1 = "aaa";
        n1 = 3;
        s2 = "aa";
        n2 = 1;
        Assertions.equal(4, new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));

        s1 = "baba";
        n1 = 11;
        s2 = "baab";
        n2 = 1;
        Assertions.equal(7, new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));

        s1 = "bacaba";
        n1 = 3;
        s2 = "abacab";
        n2 = 1;
        Assertions.equal(2 , new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));

        s1 = "ecbafedcba";
        n1 = 4;
        s2 = "abcdef";
        n2 = 1;
        Assertions.equal(1 , new MaxRepetitions_466().getMaxRepetitions(s1, n1, s2, n2));
    }

    /**
     * 考虑对于p个s2，最少需要q个s1才能获取到，p和q存在关系q=a*p+b，则n1个s1最多可以被(n1-b)/a个S2获取，后者除以n2得结果
     * 如何求a和b，事实上，这是个一次函数，只需要确定两个点，即可求解a和b，即知道(x1,y1)和(x2，y2)即可求解
     * 问题转化为求解(x1,y1)和(x2,y2),只需要每次循环从s2中扣除前面的若干字符即可
     * @param s1
     * @param n1
     * @param s2
     * @param n2
     * @return
     */
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        if (n1 == 0 || n2 == 0) {
            return 0;
        }
        char[] s1CharArray = s1.toCharArray();
        char[] s2CharArray = s2.toCharArray();
        int[] x = new int[2];
        int[] y = new int[2];
        int matchIndex = 0;
        int s2RepeatTimes = 0;
        int s1RepeatTimes = 0;
        for (int i = 0; i < 2; i++) {
            // s1重复次数
            for (; ; ) {
                s1RepeatTimes++;
                if (s1RepeatTimes > n1) {
                    return s2RepeatTimes / n2;
                }
                boolean found = false;
                for (int k = 0; k < s1CharArray.length; k++) {
                    if (s2CharArray[matchIndex] == s1CharArray[k]) {
                        found = true;
                        matchIndex++;
                        if (matchIndex == s2CharArray.length) {
                            s2RepeatTimes++;
                            matchIndex = 0;
                        }
                    }
                }
                if (!found) {
                    return 0;
                }
                if (matchIndex == 0) {
                    x[i] = s2RepeatTimes;
                    y[i] = s1RepeatTimes;
                    matchIndex = 0;
                    break;
                }

            }
        }
        double a = (y[1] - y[0]) * 1.0 / (x[1] - x[0]);
        double b = y[0] - a * x[0];
        return ((int) ((n1 - b) / a)) / n2;
    }
//        int repeatTimes = 1;
//        // 求s2的最小重复子串
//        for (int i = 1; i <= s2CharArray.length; i++) {
//            if (s2CharArray.length % i == 0) {
//                boolean repeat = true;
//                for (int j = 0; j < i; j++) {
//                    for (int k = 0; k < s2CharArray.length / i; k++) {
//                        if (s2CharArray[j + k * (s2CharArray.length / i)] != s2CharArray[j]) {
//                            repeat = false;
//                            break;
//                        }
//                    }
//                    if (repeat = false) {
//                        break;
//                    }
//                }
//                if (repeat) {
//                    repeatTimes = s2CharArray.length / i;
//                    s2CharArray = Arrays.copyOf(s2CharArray, i);
//                }
//            }
//        }


}
