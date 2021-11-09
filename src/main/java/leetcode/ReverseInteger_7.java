package leetcode;

/*
 * @lc app=leetcode.cn id=7 lang=java
 *
 * [7] 整数反转
 */

// @lc code=start
class ReverseInteger_7 {
    public int reverse(int x) {
        int result = 0;
        int temp = 0;
        while (x != 0) {
            temp = x % 10;
            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) {
                return 0;
            }
            result = result * 10 + temp;
            x = x / 10;
        }
        return Long.valueOf(result).intValue();
    }

    public static void main(String[] args) {
        System.out.println(new ReverseInteger_7().reverse(1534236469));
    }
}
// @lc code=end
