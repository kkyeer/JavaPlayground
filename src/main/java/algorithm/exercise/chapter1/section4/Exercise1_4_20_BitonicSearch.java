package algorithm.exercise.chapter1.section4;

import java.security.InvalidParameterException;

/**
 * @Author: kkyeer
 * @Description: Bitonic Search
 * @Date:Created in 下午11:20 2022/4/30
 * @Modified By:
 */
public class Exercise1_4_20_BitonicSearch {
    public static void main(String[] args) throws Exception {
        int[] arr = new int[]{10,9,7,3,1};
        System.out.println(check(arr, 0, arr.length - 1, 7, false));

        int[] arr2 = new int[]{1,2,3,6,4,0};
        System.out.println(existInBitonicArray(7, arr2));
    }

    public static boolean existInBitonicArray(int target,int[] arr) throws Exception {
        // 第一步，找到转折的点 m，有a[m-1]<a[m]&&a[m]>a[m-1]
        int turnover = findTurnOver(arr);
        System.out.println(turnover);

        return check(arr, 0, turnover, target, true)
                || check(arr, turnover, arr.length - 1, target, false);
    }

    private static int findTurnOver(int[] arr) throws Exception {
        if (arr.length < 3) {
            throw new Exception("wrong input");
        }
        int start = 0, end = arr.length - 1;
        int mid;
        do{
            mid = (start+end)/2;
            if ((arr[mid - 1] < arr[mid]) && (arr[mid] > arr[mid + 1])) {
                return mid;
            }else {
                if (arr[mid -1]<arr[mid]){
                    start = mid +1;
                }else {
                    end = mid -1;
                }
            }
        }while (start<=end);
        return 0;
    }

    public static boolean check(int[] arr, int start, int end, int target, boolean ascend) {
        if ((ascend && arr[end] < start) || (!ascend && arr[start] < target)) {
            return false;
        }
        int mid;
        do {
            mid = (start + end) / 2;
            if (arr[mid] == target) {
                return true;
            } else {
                if ((ascend && arr[mid] > target) || (!ascend && arr[mid] < target)) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
        } while (start <= end);
        return false;
    }
}
