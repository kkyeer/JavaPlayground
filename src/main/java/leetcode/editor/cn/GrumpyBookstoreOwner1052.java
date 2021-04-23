//今天，书店老板有一家店打算试营业 customers.length 分钟。每分钟都有一些顾客（customers[i]）会进入书店，所有这些顾客都会在那一分
//钟结束后离开。 
//
// 在某些时候，书店老板会生气。 如果书店老板在第 i 分钟生气，那么 grumpy[i] = 1，否则 grumpy[i] = 0。 当书店老板生气时，那一
//分钟的顾客就会不满意，不生气则他们是满意的。 
//
// 书店老板知道一个秘密技巧，能抑制自己的情绪，可以让自己连续 X 分钟不生气，但却只能使用一次。 
//
// 请你返回这一天营业下来，最多有多少客户能够感到满意的数量。 
// 
//
// 示例： 
//
// 输入：customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
//输出：16
//解释：
//书店老板在最后 3 分钟保持冷静。
//感到满意的最大客户数量 = 1 + 1 + 1 + 1 + 7 + 5 = 16.
// 
//
// 
//
// 提示： 
//
// 
// 1 <= X <= customers.length == grumpy.length <= 20000 
// 0 <= customers[i] <= 1000 
// 0 <= grumpy[i] <= 1 
// 
// Related Topics 数组 Sliding Window 
// 👍 110 👎 0


package leetcode.editor.cn;

import utils.Assertions;

import java.util.Arrays;
import java.util.List;

public class GrumpyBookstoreOwner1052{
    public static void main(String[] args){
        Solution solution = new GrumpyBookstoreOwner1052().new Solution();

        List<Integer> customerInput = Arrays.asList(1,0,1,2,1,1,7,5);
        List<Integer> grumpyInput = Arrays.asList(0,1,0,1,0,1,0,1);
        int X = 3;
        int expected = 16;
        int[] customers = new int[customerInput.size()];
        for (int i = 0; i < customerInput.size(); i++) {
            customers[i] = customerInput.get(i);
        }
        int[] grumpy = new int[grumpyInput.size()];
        for (int i = 0; i < grumpy.length; i++) {
            grumpy[i] = grumpyInput.get(i);
        }


        Assertions.assertTrue(expected == solution.maxSatisfied(customers, grumpy, X));

        customerInput = Arrays.asList(3);
        grumpyInput = Arrays.asList(1);
        X = 1;
        expected = 3;
        customers = new int[customerInput.size()];
        for (int i = 0; i < customerInput.size(); i++) {
            customers[i] = customerInput.get(i);
        }
        grumpy = new int[grumpyInput.size()];
        for (int i = 0; i < grumpy.length; i++) {
            grumpy[i] = grumpyInput.get(i);
        }

        Assertions.assertTrue(expected == solution.maxSatisfied(customers, grumpy, X));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int maxSuppressed = 0;
        int sum =0;
        int length = customers.length;
        int[] suppressedArray = new int[length];
        int currentSuppressed = 0;
//        滑动窗口
        for (int i = 0; i < length; i++) {
            if (grumpy[i] == 1) {
                suppressedArray[i] = customers[i];
            }else {
                sum += customers[i];
                suppressedArray[i] = 0;
            }
            if (i >= X) {
                currentSuppressed = currentSuppressed - suppressedArray[i - X] + suppressedArray[i];
            }else {
                currentSuppressed = currentSuppressed + suppressedArray[i];
            }
            maxSuppressed = Math.max(currentSuppressed, maxSuppressed);
        }
        return sum + maxSuppressed;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}