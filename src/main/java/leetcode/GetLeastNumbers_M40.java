package leetcode;

import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: 面试题40. 最小的k个数
 * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 *
 * 示例 1：
 *
 * 输入：arr = [3,2,1], k = 2
 * 输出：[1,2] 或者 [2,1]
 *
 * 示例 2：
 *
 * 输入：arr = [0,1,2,1], k = 1
 * 输出：[0]
 *
 * 限制：
 *
 *     0 <= k <= arr.length <= 10000
 *     0 <= arr[i] <= 10000
 * @Date:Created in  2020-3-20 10:16
 * @Modified By:
 */
public class GetLeastNumbers_M40 {
    public static void main(String[] args) {
        int[] arr = new int[]{0,0,1,2,4,2,2,3,1,4};
        System.out.println(Arrays.toString(new GetLeastNumbers_M40().getLeastNumbers(arr, 8)));
    }
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        return Arrays.copyOf(arr,k);
    }
}