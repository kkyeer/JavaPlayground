package leetcode;

/**
 * @Author: kkyeer
 * @Description: 55. 跳跃游戏
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 判断你是否能够到达最后一个位置。
 *
 * 示例 1:
 *
 * 输入: [2,3,1,1,4]
 * 输出: true
 * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
 *
 * 示例 2:
 *
 * 输入: [3,2,1,0,4]
 * 输出: false
 * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Date:Created in 上午10:18 20-4-17
 * @Modified By:
 */
public class CanJump_55 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 0, 4};
        System.out.println(new CanJump_55().canJump(nums));
    }

    public boolean canJump(int[] nums) {
        // 问题转化为，是否存在这样一个点,符合以下特征:
        // 1. index<length-1
        // 2. 前面所有的点都无法越过它
        // 3. 这个点的跳跃距离为0
        boolean leap = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) {
                if (i == 0) {
                    leap = true;
                }
                boolean preCanJump = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (i - j < nums[j]) {
                        preCanJump = true;
                        break;
                    }
                }
                if (!preCanJump) {
                    leap = false;
                    break;
                }
            }
        }
        return leap;
    }
}
