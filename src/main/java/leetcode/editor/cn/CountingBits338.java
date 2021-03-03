/*
给定一个非负整数 num。对于 0 ≤ i ≤ num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。 

 示例 1: 

 输入: 2
输出: [0,1,1] 

 示例 2: 

 输入: 5
输出: [0,1,1,2,1,2] 

 进阶: 


 给出时间复杂度为O(n*sizeof(integer))的解答非常容易。但你可以在线性时间O(n)内用一趟扫描做到吗？ 
 要求算法的空间复杂度为O(n)。 
 你能进一步完善解法吗？要求在C++或任何其他语言中不使用任何内置函数（如 C++ 中的 __builtin_popcount）来执行此操作。 

 Related Topics 位运算 动态规划 
 👍 591 👎 0
*/

package leetcode.editor.cn;

import utils.Assertions;

public class CountingBits338{
    public static void main(String[] args){
        Solution solution = new CountingBits338().new Solution();
        int[] result = solution.countBits(5);
        Assertions.equal(result[2], 1);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
        /**
         * 动态规划
         * F(0)=0
         * F(1)=1 + F(1-1)
         * F(2)= 1+ F(2-2)
         * F(3)= 1 + F(3-2)
         * F(4)= 1+ F(4-4)
         * @param num
         * @return
         */
    public int[] countBits(int num) {
        int[] result = new int[num + 1];
        int stage = 1;
        for (int i = 0; i <= num; ) {
            for (int j = 0; j < stage && i++ < num; j++) {
                result[i] = 1 + result[i - stage];
            }
            stage *= 2;
        }
        return result;
    }

        /**
         * 调用原生方法
         * @param num
         * @return
         */
    public int[] countBits2(int num) {
        int[] result = new int[num+1];
        for(int i = 0;i<=num;i++){
            result[i] = Integer.bitCount(i);
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}