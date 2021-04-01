//给定一个直方图(也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量?直方图的宽度为 1。 
//
// 
//
// 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的直方图，在这种情况下，可以接 6 个单位的水（蓝色部分表示水）。 感谢 Marco
//s 贡献此图。 
//
// 示例: 
//
// 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
//输出: 6 
// Related Topics 栈 数组 双指针 
// 👍 56 👎 0


package leetcode.editor.cn;

import utils.Assertions;

public class VolumeOfHistogramLcci{
    public static void main(String[] args){
        Solution solution = new VolumeOfHistogramLcci().new Solution();
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        Assertions.equal(6, solution.trap(height));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int trap(int[] height) {
        int[] right = new int[height.length];
        int max = 0;
        for (int i = height.length - 1; i >= 0; i--) {
            right[i] = max;
            max = Math.max(max, height[i]);
        }
        int sum = 0 ;
        int diff;
        max = 0;
        for (int i = 0; i < height.length; i++) {
            diff = Math.min(max, right[i]) -height[i];
            if (diff > 0) {
                sum+=diff;
            }
            max = Math.max(max, height[i]);
        }
        return sum;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}