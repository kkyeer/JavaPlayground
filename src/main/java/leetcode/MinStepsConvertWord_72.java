package leetcode;

import java.util.Arrays;

/**
 * @author kkyeer
 * @description: 72. 编辑距离
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：word1 = "horse", word2 = "ros"
 * 输出：3
 * 解释：
 * horse -> rorse (将 'h' 替换为 'r')
 * rorse -> rose (删除 'r')
 * rose -> ros (删除 'e')
 * <p>
 * 示例 2：
 * <p>
 * 输入：word1 = "intention", word2 = "execution"
 * 输出：5
 * 解释：
 * intention -> inention (删除 't')
 * inention -> enention (将 'i' 替换为 'e')
 * enention -> exention (将 'n' 替换为 'x')
 * exention -> exection (将 'n' 替换为 'c')
 * exection -> execution (插入 'u')
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/edit-distance
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date:Created in 20:38 4-6
 * @modified By:
 */
public class MinStepsConvertWord_72 {
    public static void main(String[] args) {
        System.out.println(new MinStepsConvertWord_72().minDistance("intention", "execution"));
    }


    private char[] arr1;
    private char[] arr2;
    private int[][] cache;

    public int minDistance(String word1, String word2) {
        /* 二维动态规划！
        假设dp[i][j] = word1的前i个字母转成word2的前j个字母的距离
        若word1[i]==word2[j]
        增: dp[i-1][j-1] = dp[i][j]
        否则，对于dp[i][j-1]到dp[i][j],考虑最后一步增删改三种操作的选择
        增:dp[i][j-1]+1 = dp[i][j]
        删:dp[i-1][j]+1 = dp[i][j]    abcd,abc
        改:dp[i-1][j-1]+1 = dp[i][j]
        即dp[i][j] = Min(dp[i][j-1],dp[i-1][j],dp[i-1][j-1])+1
        */
        arr1 = word1.toCharArray();
        arr2 = word2.toCharArray();
        cache = new int[arr1.length][];
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new int[arr2.length];
            Arrays.fill(cache[i], -1);
        }
        return dp(arr1.length - 1, arr2.length - 1);
    }
    int result;
    public int dp(int i, int j) {
        if (i == -1) {
            return j + 1;
        }
        if (j == -1) {
            return i + 1;
        }
        if (arr1[i] == arr2[j]) {
            return dp(i - 1, j - 1);
        } else {
            if (cache[i][j] != -1) {
                return cache[i][j];
            }
            result = Math.min(Math.min(dp(i, j - 1), dp(i - 1, j)), dp(i - 1, j - 1)) + 1;
            cache[i][j] = result;
            return result;

        }
    }

}
