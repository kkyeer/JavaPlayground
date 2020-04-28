package leetcode;

/**
 * @Author: kkyeer
 * @Description: 面试题56 - I. 数组中数字出现的次数
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [4,1,4,6]
 * 输出：[1,6] 或 [6,1]
 *
 * 示例 2：
 *
 * 输入：nums = [1,2,10,4,1,4,3,3]
 * 输出：[2,10] 或 [10,2]
 *
 *
 *
 * 限制：
 *
 *     2 <= nums <= 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Date:Created in  2020-4-28 14:06
 * @Modified By:
 */
public class TwoNumber_M56_1 {
    public static void main(String[] args) {
        System.out.println();
    }

    public int[] singleNumbers(int[] nums) {
        int[] result = new int[2];
        int temp = 0;
        for (int num : nums) {
            temp ^= num;
        }
        // 此时temp的值为a^b
        result[0] = temp;
        result[1] = temp;
        // 这个值为temp二进制化后，取最后面的一个1并且后面拼上对应的0后的值，做法为i&-i，具体原因可以看补码运算
        int bitIndex = Integer.lowestOneBit(temp);
        for (int num : nums) {
            if ((num & bitIndex) == 0) {
                result[0] ^= num;
            } else {
                result[1] ^= num;
            }
        }
        return result;
    }
}