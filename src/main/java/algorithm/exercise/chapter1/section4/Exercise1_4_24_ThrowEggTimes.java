package algorithm.exercise.chapter1.section4;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntBiFunction;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 下午10:41 2022/5/3
 * @Modified By:
 */
public class Exercise1_4_24_ThrowEggTimes {
    public static void main(String[] args) throws Exception {
        //    arr: int数组，第F个数字为1，前面为0

        // System.out.println(estimateFixMaxFloor(100000, 10000, Exercise1_4_24_ThrowEggTimes::highCost));
        System.out.println(estimateFixF(100000, 1024,100000, Exercise1_4_24_ThrowEggTimes::lowCost));
        // int[] arr = fillIn(10000, 10000);
        // System.out.println(lowCost(arr, new AtomicInteger(0)));
    }

    public static float estimateFixMaxFloor(int times, int maxFloorN, ToIntBiFunction<int[],AtomicInteger> func) throws Exception {
        int totalBroken = 0;
        for (int j = 0; j < times; j++) {
            AtomicInteger broken = new AtomicInteger(0);
            int f = new Random().nextInt(maxFloorN);
            System.out.println("size:" + maxFloorN + ",f:" + f);
            int[] arr = fillIn(maxFloorN, f);
            int result = func.applyAsInt(arr, broken);
            System.out.println(result);
            System.out.println("broken:" + broken.get());
            if (result != f) {
                throw new Exception("Wrong Answer");
            }
            totalBroken += broken.get();
        }
        System.out.println("times:" + times);
        System.out.println("avgBroken:" + totalBroken * 1.0 / times);
        return totalBroken * 1.0f / times;
    }

    public static float estimateFixF(int times, int f,int maxFloorN, ToIntBiFunction<int[],AtomicInteger> func) throws Exception {
        int totalBroken = 0;
        for (int j = 0; j < times; j++) {
            AtomicInteger broken = new AtomicInteger(0);
            int size = new Random().nextInt(maxFloorN) + f + 1;
            System.out.println("size:" + size + ",f:" + f);
            int[] arr = fillIn(size, f);
            int result = func.applyAsInt(arr, broken);
            System.out.println(result);
            System.out.println("broken:" + broken.get());
            if (result != f) {
                throw new Exception("Wrong Answer");
            }
            totalBroken += broken.get();
        }
        System.out.println("times:" + times);
        System.out.println("avgBroken:" + totalBroken * 1.0 / times);
        return totalBroken * 1.0f / times;
    }

    public static int[] fillIn(int maxFloorN,int f){
        int[] arr = new int[maxFloorN];
        for (int i = 0; i < f; i++) {
            arr[i] = 0;
        }
        for (int i = f; i < maxFloorN; i++) {
            arr[i] = 1;
        }
        return arr;
    }

    public static int highCost(int[] arr, AtomicInteger broken) {
        int low = 0, high = arr.length - 1, mid;
        do {
            mid = (low + high) / 2;
            if (arr[mid] == 0) {
                if (arr[mid + 1] == 1) {
                    broken.getAndIncrement();
                    return mid + 1;
                } else {
                    low = mid + 2;
                }
            } else {
                broken.getAndIncrement();
                if (mid == 0 || arr[mid - 1] == 0) {
                    return mid;
                } else {
                    broken.getAndIncrement();
                    high = mid - 1;
                }
            }
        } while (low <= high);
        return -1;
    }

    public static int lowCost(int[] arr, AtomicInteger broken) {
        int start = 0, end = 1;
        if (arr[0] == 1) {
            return 0;
        }
        // step1: find end
        while (end != arr.length-1  && arr[end] != 1 ) {
            end = Math.min(arr.length-1, end * 2);
        }
        start = end / 2;
        int[] arr2 = new int[end - start + 1];
        System.arraycopy(arr, start, arr2, 0, arr2.length);
        broken.getAndIncrement();
        return highCost(arr2, broken) + start;
    }
}
