package leetcode;


import java.util.*;

/**
 * @author kkyeer
 * @description: 56. 合并区间
 * 给出一个区间的集合，请合并所有重叠的区间。
 *
 * 示例 1:
 *
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 *
 * 示例 2:
 *
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-intervals
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date:Created in 20:12 4-16
 * @modified By: kkyeer
 */

public class Merge_56 {
    public static void main(String[] args) {
        int[][] source = new int[][]
                {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
////        int[][] source = new int[][]
////                {{1, 4}, {0, 0}};
//        int[][] source = new int[][]
//                {{1, 4}, {4, 5}};
        int[][] result = new Merge_56().merge(source);
        for (int[] line : result) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int[][] merge(int[][] intervals) {
        List<int[]> result = new ArrayList<>();
        for (int[] interval : intervals) {
            int position = 0;
            int min = interval[0];
            int max = interval[1];
            Set<int[]> removeIndexSet = new HashSet<>();
            for (int i = 0; i < result.size(); i++) {
                int[] existInterval = result.get(i);
                if (existInterval[0] > max) {
                    break;
                }
                if (existInterval[1] < min) {
                    position++;
                    continue;
                }
                position++;
                removeIndexSet.add(existInterval);
                min = Math.min(min, existInterval[0]);
                if (max >= existInterval[0]) {
                    removeIndexSet.add(existInterval);
                    max = Math.max(max, existInterval[1]);
                }
            }
            result.add(position, new int[]{min, max});
            result.removeAll(removeIndexSet);
        }
        return result.toArray(new int[0][]);
    }

}
