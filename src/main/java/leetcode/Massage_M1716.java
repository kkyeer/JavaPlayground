package leetcode;

import java.util.Arrays;

/**
 * @author kkyeer
 * @description: 面试题 17.16. 按摩师
 * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
 *
 * 注意：本题相对原题稍作改动
 *
 *
 *
 * 示例 1：
 *
 * 输入： [1,2,3,1]
 * 输出： 4
 * 解释： 选择 1 号预约和 3 号预约，总时长 = 1 + 3 = 4。
 *
 * 示例 2：
 *
 * 输入： [2,7,9,3,1]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约和 5 号预约，总时长 = 2 + 9 + 1 = 12。
 *
 * 示例 3：
 *
 * 输入： [2,1,4,5,3,1,1,3]
 * 输出： 12
 * 解释： 选择 1 号预约、 3 号预约、 5 号预约和 8 号预约，总时长 = 2 + 4 + 3 + 3 = 12。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/the-masseuse-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date: Created in 21:09 3-24
 * @modified By:
 */
public class Massage_M1716 {
    public static void main(String[] args) {
        int[] arr = new int[]{2,1,1,2};
//        对于一个数组，假设F(I)是0-i项的解，则arr[I]和arr[I-1]一定有且只有一项，如果选择arr[I]，则F(I）=F(I-2)+arr[I]，
//        如果选择arr[I-1],则F(I)=F(I-3)+arr[I-1],可得递归方程
//        F(I) = Max(F(I-2)+arr[I],F(I-3)+arr[I-1])
        System.out.println(new Massage_M1716().massage(arr));
    }

    int[] store;

    public int massage(int[] arr) {
        store = new int[arr.length];
        Arrays.fill(store, -1);
        if (arr.length == 0) {
            return 0;
        }
        return calc(arr, arr.length - 1);
    }

    public int calc(int[] arr, int index) {
        if (store[index] != -1) {
            return store[index];
        }
        if (index < 0) {
            return 0;
        } else if (index == 0) {
            return arr[0];
        } else if (index == 1) {
            return Math.max(arr[0], arr[1]);
        } else if (index == 2) {
            return Math.max(arr[0] + arr[2], arr[1]);
        } else if (index == 3) {
            return Math.max(Math.max(arr[0] + arr[2], arr[1] + arr[3]), arr[0] + arr[3]);
        }
        int m2 = calc(arr, index - 2);
        store[index -2] = m2;
        int m3 = calc(arr, index - 3);
        store[index -3 ] = m3;
        return Math.max(m2 + arr[index], m3 + arr[index - 1]);
    }
}
