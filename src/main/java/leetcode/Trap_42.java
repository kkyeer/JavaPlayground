package leetcode;

/**
 * @author kkyeer
 * @description: 42. 接雨水
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
 *
 * 示例:
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/trapping-rain-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @date:Created in 20:59 4-4
 * @modified By:
 */
public class Trap_42 {
    public static void main(String[] args) {
        int[] heights = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(new Trap_42().trap(heights));
    }

    public int trap(int[] heights) {
        if (heights.length < 3) {
            return 0;
        }
        int result = 0;
        int[] left = new int[heights.length];
        int[] right = new int[heights.length];
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] > max) {
                max = heights[i];
            }
            left[i] = max;
        }
        max = 0;
        for (int i = heights.length - 1; i >= 0; i--) {
            if (heights[i] > max) {
                max = heights[i];
            }
            right[i] = max;
        }
        for (int i = 0; i < heights.length; i++) {
            result += Math.max(Math.min(left[i], right[i]) - heights[i], 0);
        }
        return result;
    }
}
