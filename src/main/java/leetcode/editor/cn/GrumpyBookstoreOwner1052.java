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

import java.util.Arrays;
import java.util.List;

public class GrumpyBookstoreOwner1052{
    public static void main(String[] args){
        Solution solution = new GrumpyBookstoreOwner1052().new Solution();
        List<Integer> customerInput = Arrays.asList(1,0,1,2,1,1,7,5);
        int[] customers = new int[customerInput.size()];
        for (int i = 0; i < customerInput.size(); i++) {
            customers[i] = customerInput.get(i);
        }
        List<Integer> grumpyInput = Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1);
        int[] grumpy = new int[grumpyInput.size()];
        for (int i = 0; i < grumpy.length; i++) {
            grumpy[i] = grumpyInput.get(i);
        }
        System.out.println(solution.maxSatisfied(customers, grumpy, 3));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int maxSupressed = 0;
        int sum =0;
        int length = customers.length;
        for (int i = 0; i < customers.length; i++) {
            if (i <= length - X) {
                int currentSuppressed = 0;
                for (int j = 0; j < X; j++) {
                    if (grumpy[i + j] == 1) {
                        currentSuppressed += customers[i + j];
                    }
                }
                maxSupressed = Math.max(currentSuppressed, maxSupressed);
            }
            if (grumpy[i] == 0) {
                sum += customers[i];
            }
        }
        return sum + maxSupressed;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}