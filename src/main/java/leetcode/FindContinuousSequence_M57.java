package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 面试题57 - II. 和为s的连续正数序列
 *输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 *
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 *
 *
 *
 * 示例 1：
 *
 * 输入：target = 9
 * 输出：[[2,3,4],[4,5]]
 *
 * 示例 2：
 *
 * 输入：target = 15
 * 输出：[[1,2,3,4,5],[4,5,6],[7,8]]
 *
 *
 *
 * 限制：
 *
 *     1 <= target <= 10^5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 21:56 3-6
 * @Modified By:
 */
class FindContinuousSequence_M57 {
    public static void main(String[] args) {
        int[][] seqs = findContinuousSequence(98160);
        for (int[] seq : seqs) {
            System.out.println(Arrays.toString(seq));
        }
    }

    public static int[][] findContinuousSequence(int target) {
        List<int[]> results = new ArrayList<>();
        int mid;
        for (int n = 1;(mid= 2 * target - n * n + n) > 0; n++) {
            int s = mid / 2 / n;
            if (2 * n * s == mid) {
                if (n > 1) {
                    int[] seq = new int[n];
                    for (int j = 0; j < n; j++) {
                        seq[j] = s + j;
                    }
                    results.add(0,seq);
                }
            }
        }
        return results.toArray(new int[results.size()][]);
    }

    public static int[][] findContinuousSequenceOriginal(int target) {
        List<int[]> results = new ArrayList<>();
        for (int i = 1; i <= target / 2; i++) {
            int m = 2 * i - 1;
            long square = (long) m * m + (long) 8 * target;
            int root = (int) Math.sqrt(square);
            int n;
            if ((long) root * root == square && (root - m) % 2 == 0) {
                n = (root - m) / 2;
                if (n > 1) {
                    int[] seq = new int[n];
                    for (int j = 0; j < n; j++) {
                        seq[j] = i + j;
                    }
                    results.add(seq);
                }
            }
        }
        return results.toArray(new int[results.size()][]);
    }

}
