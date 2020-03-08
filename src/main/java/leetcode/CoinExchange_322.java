package leetcode;

import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: 零钱兑换
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 *
 * 示例 1:
 *
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 * 示例 2:
 *
 * 输入: coins = [2], amount = 3
 * 输出: -1
 *
 * 说明:
 * 你可以认为每种硬币的数量是无限的。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/coin-change
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 14:50 3-8
 * @Modified By:
 */
class CoinExchange_322 {
    public static void main(String[] args) {
        int[] coins = new int[]{186,419,83,408};
        int amount = 6249;
        System.out.println(coinChange(coins, amount));
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount < 1) {
            return 0;
        }
        Arrays.sort(coins);
        return calc(new int[amount+1], coins, amount);
    }

    public static int calc(int[] existedSolution, int[] coins, int amount) {
        // 假设F(Amount)为金额Amount的最小硬币数，对于每一个金额Amount,兑换的最小硬币数为F(Amount)=F(Amount-C)+1,其中C为解的硬
        // 币集合的最后一个值,遍历每个硬币，取得使F(Amount-C)最小的值，+1即为答案
        int min = Integer.MAX_VALUE;
        if (existedSolution[amount] != 0) {
            return existedSolution[amount];
        }
        for (int i = coins.length - 1; i >= 0; i--) {
            int remain = amount - coins[i];
            if (remain > 0) {
                int remainCoinChange = calc(existedSolution, coins, remain);
                if (remainCoinChange != -1 && remainCoinChange < min) {
                    min = remainCoinChange + 1;
                }
            } else if (remain == 0) {
                existedSolution[amount] = 1;
                return 1;
            }
        }
        int result = min == Integer.MAX_VALUE ? -1 : min;
        existedSolution[amount] = result;
        return result;
    }
}
