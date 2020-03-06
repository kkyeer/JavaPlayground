package leetcode;

import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: leetcode1103 分糖果
 * 排排坐，分糖果。
 * <p>
 * 我们买了一些糖果 candies，打算把它们分给排好队的 n = num_people 个小朋友。
 * <p>
 * 给第一个小朋友 1 颗糖果，第二个小朋友 2 颗，依此类推，直到给最后一个小朋友 n 颗糖果。
 * <p>
 * 然后，我们再回到队伍的起点，给第一个小朋友 n + 1 颗糖果，第二个小朋友 n + 2 颗，依此类推，直到给最后一个小朋友 2 * n 颗糖果。
 * <p>
 * 重复上述过程（每次都比上一次多给出一颗糖果，当到达队伍终点后再次从队伍起点开始），直到我们分完所有的糖果。注意，就算我们手中的剩下糖果数不够（不比前一次发出的糖果多），这些糖果也会全部发给当前的小朋友。
 * <p>
 * 返回一个长度为 num_people、元素之和为 candies 的数组，以表示糖果的最终分发情况（即 ans[i] 表示第 i 个小朋友分到的糖果数）。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：candies = 7, num_people = 4
 * 输出：[1,2,3,1]
 * 解释：
 * 第一次，ans[0] += 1，数组变为 [1,0,0,0]。
 * 第二次，ans[1] += 2，数组变为 [1,2,0,0]。
 * 第三次，ans[2] += 3，数组变为 [1,2,3,0]。
 * 第四次，ans[3] += 1（因为此时只剩下 1 颗糖果），最终数组变为 [1,2,3,1]。
 * <p>
 * 示例 2：
 * <p>
 * 输入：candies = 10, num_people = 3
 * 输出：[5,2,3]
 * 解释：
 * 第一次，ans[0] += 1，数组变为 [1,0,0]。
 * 第二次，ans[1] += 2，数组变为 [1,2,0]。
 * 第三次，ans[2] += 3，数组变为 [1,2,3]。
 * 第四次，ans[0] += 4，最终数组变为 [5,2,3]。
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= candies <= 10^9
 * 1 <= num_people <= 1000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/distribute-candies-to-people
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-5 13:17
 * @Modified By:
 */

// 可以使用数学方法进行化简
class DistributeCandies_1103 {
    public static void main(String[] args) {
        int candies = 1000000000;
        int num_people = 1000;
        int[] result = distributeCandiesApplyingFormula(candies, num_people);
        System.out.println(Arrays.toString(result));
    }

    private static int[] distributeCandies(int candies, int num_people) {
        int[] result = new int[num_people];
        int n = 0;
        int index = 0;
        while (candies > 0) {
            candies -= ++n;
            result[index] = result[index] + n;
            index = (index + 1) % num_people;
        }
        if (candies < 0) {
            int lastIndex = index == 0 ? num_people - 1 : index - 1;
            result[lastIndex] = result[lastIndex] + candies;
        }
        return result;
    }

    private static int[] distributeCandiesApplyingFormula(int candies, int num_people) {
        // 分配几轮
        int rounds = (int) ((-1.0 + Math.sqrt(1.0 + 8.0 * candies) ) / 2);
        // 判断是否分完
        int remainCount = candies - rounds * (rounds + 1) / 2;
        boolean isRemain = false;
        if (remainCount != 0) {
            rounds++;
            isRemain = true;
        }
        // 行数
        int row = rounds / num_people + (rounds % num_people == 0 ? 0 : 1);
        // 最后列
        int lastColumnIndex = rounds % num_people == 0 ? num_people - 1 : rounds % num_people - 1;
        int[] result = new int[num_people];
        int lastSecondRowStart = (row - 2) * num_people;
        int lastRowStart = (row - 1) * num_people;
        for (int i = 0; i < num_people; i++) {
            if (i < lastColumnIndex) {
                result[i] = (i * 2 + 2 + lastRowStart) * row/ 2 ;
            } else if (i == lastColumnIndex) {
                if (isRemain) {
                    result[i] = (i * 2 + 2 + lastSecondRowStart)* (row - 1) / 2  + remainCount;
                }else {
                    result[i] = (i * 2 + 2 + lastRowStart) * row/ 2 ;
                }

            } else {
                result[i] = (i * 2 + 2 + lastSecondRowStart) * (row - 1)/ 2 ;
            }
        }
        return result;
    }
}
