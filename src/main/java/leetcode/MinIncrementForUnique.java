package leetcode;

import java.util.*;

/**
 * @author kkyeer
 * @description: 945. 使数组唯一的最小增量
 * 给定整数数组 A，每次 move 操作将会选择任意 A[i]，并将其递增 1。
 *
 * 返回使 A 中的每个值都是唯一的最少操作次数。
 *
 * 示例 1:
 *
 * 输入：[1,2,2]
 * 输出：1
 * 解释：经过一次 move 操作，数组将变为 [1, 2, 3]。
 *
 * 示例 2:
 *
 * 输入：[3,2,1,2,1,7]
 * 输出：6
 * 解释：经过 6 次 move 操作，数组将变为 [3, 4, 1, 2, 5, 7]。
 * 可以看出 5 次或 5 次以下的 move 操作是不能让数组的每个值唯一的。
 *
 * 提示：
 *
 *     0 <= A.length <= 40000
 *     0 <= A[i] < 40000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-increment-to-make-array-unique
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date:Created in 21:11 3-22
 * @modified By:
 */
public class MinIncrementForUnique {
    public static void main(String[] args) {
        int[] arr = new int[]{0,2,2};
        System.out.println(new MinIncrementForUnique().minIncrementForUnique(arr));
    }

    private int minIncrementForUnique(int[] arr) {
        if (arr.length <= 1) {
            return 0;
        }
        Arrays.sort(arr);
        Map<Integer, Integer> times = new HashMap<>(arr.length);
        for (int i : arr) {
            times.compute(i, (key, oriValue) -> oriValue == null ? 1 : ++oriValue);
        }
        Set<Integer> existedNumbers = times.keySet();
        List<Integer> numbers = new ArrayList<>(existedNumbers);
        Collections.sort(numbers);
        Integer index = null;
        int max = numbers.get(numbers.size() - 1);
        int moves = 0;
        for (int i = 0; i < numbers.size(); i++) {
            int currentNumberCount = times.get(numbers.get(i));
            if (index == null || index < numbers.get(i)) {
                index = numbers.get(i);
            }
            if (currentNumberCount == 1) {
                continue;
            } else {
                for (int j = 0; j < currentNumberCount - 1; j++) {
                    while (existedNumbers.contains(++index) && index <= max) {
                    }
                    moves += index - numbers.get(i);
                }
            }
        }
        return moves;
    }
}
