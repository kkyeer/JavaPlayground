//给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第
//一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。 
//
// 示例 1: 
//
// 
//输入: [1,2,1]
//输出: [2,-1,2]
//解释: 第一个 1 的下一个更大的数是 2；
//数字 2 找不到下一个更大的数； 
//第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
// 
//
// 注意: 输入数组的长度不会超过 10000。 
// Related Topics 栈 
// 👍 271 👎 0


package leetcode.editor.cn;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class NextGreaterElementIi503{
    public static void main(String[] args){
        Solution solution = new NextGreaterElementIi503().new Solution();
        int[] result = solution.nextGreaterElements(new int[]{1,2,1});
        System.out.println(result);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        //        单调栈->栈里存单调递减的元素的 下标，如果下一个元素>栈顶，则pop栈直到继续符合单调递减
        int l = nums.length;
        int[] result = new int[l];
        if (l == 0) {
            return result;
        }
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        Integer max = null, maxIndex = 0;
        int index;
        // 第一遍遍历，找到最大的Index
        for (int i = 0; i < l; i++) {
            index = i % l;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[index]) {
                result[stack.pop()] = nums[index];
            }
            stack.push(index);
            if (max == null) {
                max = nums[index];
            } else if (nums[index] > max) {
                max = nums[index];
                maxIndex = index;
            }
        }
//        第二遍遍历，只需要遍历到maxIndex之前
        for (int i = 0; i <= maxIndex; i++) {
            index = i % l;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[index]) {
                result[stack.pop()] = nums[index];
            }
            stack.push(index);
        }
        return result;
    }

    /**
     * 单调栈
     * @param nums
     * @return
     */
    public int[] monotonicDecreasing(int[] nums){
//        单调栈->栈里存单调递减的元素的 下标，如果下一个元素>栈顶，则pop栈直到继续符合单调递减
        int l = nums.length;
        int[] result = new int[l];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        int index;
        for (int i = 0; i < 2 * l - 1; i++) {
            index = i % l;
            while (!stack.isEmpty() && nums[stack.peek()] < nums[index]) {
                result[stack.pop()] = nums[index];
            }
            stack.push(index);
        }
        return result;
    }

    public int[] myAnswer(int[] nums){

        int[] result = new int[nums.length];
        if (nums.length == 0) {
            return result;
        }
        int max = nums[0], maxIndex = 0, l = nums.length;
        for (int i = 1; i < l; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }
        result[maxIndex] = -1;
        int index;
        int num;
        int index2;
        for (int i = 1; i <= l - 1; i++) {
            index = (maxIndex + i) % l;
            num = nums[index];
            if (num == max) {
                result[index] = -1;
            }else {
                boolean found = false;
                for (int j = 1; j <= l - 1 - i; j++) {
                    index2 = (index + j) % l;
                    if (nums[index2] > num) {
                        result[index] = nums[index2];
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result[index] = max;
                }
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}