//给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。 
//
// 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0]
//输出：[[],[0]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10 
// -10 <= nums[i] <= 10 
// nums 中的所有元素 互不相同 
// 
// Related Topics 位运算 数组 回溯算法 
// 👍 1100 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;

public class Subsets78{
    public static void main(String[] args){
        Solution solution = new Subsets78().new Solution();
        List<List<Integer>> result = solution.subsets(new int[]{1, 2, 3});
        System.out.println(result);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
//        位图
        int length = nums.length;
        int exp = 1 << length;
        List<List<Integer>> result = new ArrayList<>();
        int j,index;
        for (int i = 0; i < exp; i++) {
            List<Integer> current = new ArrayList<>();
            j = i;
            index =0;
            while (j > 0) {
                if ((j & 1) == 1) {
                    current.add(nums[index]);
                }
                j = j >> 1;
                index++;
            }
            result.add(current);
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}