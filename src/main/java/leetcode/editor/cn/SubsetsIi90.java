//给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。 
//
// 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。 
//
// 
// 
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,2]
//输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
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
// 
// 
// 
// Related Topics 数组 回溯算法 
// 👍 504 👎 0


package leetcode.editor.cn;

import java.util.*;

public class SubsetsIi90{
    public static void main(String[] args){
        Solution solution = new SubsetsIi90().new Solution();
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{1, 2,2});
        System.out.println(result);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Map<Integer, Integer> numberPosition = new HashMap<>();
        int position = 0;
        for (int num : nums) {
            numberPosition.putIfAbsent(num, position++);
        }
        Set<Integer> existCombination = new HashSet<>();
        int exp = 1 << nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < exp; i++) {
            List<Integer> current = new ArrayList<>();
            int j = i, index = 0, combinationCode = 0;
            while (j > 0) {
                if ((j & 1) == 1) {
                    int currentNumber = nums[index];
                    current.add(currentNumber);
//                    combination的第numberPosition.get(currentNumber)位为1
                    combinationCode = combinationCode | (1 << numberPosition.get(currentNumber));
                }
                j = j >> 1;
                index++;
            }
            if (existCombination.add(combinationCode)) {
                result.add(current);
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}