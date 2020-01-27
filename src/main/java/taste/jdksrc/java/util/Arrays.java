package taste.jdksrc.java.util;

import utils.Utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: kkyeer
 * @Description: Arrays工具类使用
 * @Date:Created in  2020-1-19 15:18
 * @Modified By:
 */
class Arrays {
    public static void main(String[] args) throws Exception {
//        parallelPrefix();

        int testArrSize = 1000;
        int[] testArr = new int[testArrSize];
        Random random = new Random();
        for (int i = 0; i < testArr.length; i++) {
            testArr[i] = random.nextInt(10000);
        }
        Utils.printArray(testArr);
        testArr = fillIn("5073,3627,4026,3113,7235,4859,6866,4311,5941,1192");
        quickSort(testArr, 0, testArr.length-1);
        validateSort(testArr);
        Utils.printArray(testArr);
        System.out.println(INVOKE_COUNT.get());
    }

    private static int[] fillIn(String s) {
        String[] ints = s.split(",");
        int[] result = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = Integer.parseInt(ints[i]);
        }
        return result;
    }

    /**
     * 测试目标数组是否有序
     * @param testArr 待测数组
     */
    private static void validateSort(int[] testArr) throws Exception {
        for (int i = 0; i < testArr.length - 2; i++) {
            if (testArr[i + 1] < testArr[i]) {
                Utils.printArray(testArr);
                throw new Exception("数组非从小到大有序");
            }
        }
    }

    private static final AtomicInteger INVOKE_COUNT = new AtomicInteger(0);

    /**
     * 快排
     * @param srcArray 待排序数组
     */
    private static void quickSort(int[] srcArray,int left,int right) {
//        INVOKE_COUNT.incrementAndGet();
        if (left >= right || right >= srcArray.length || left < 0) {
            return;
        } else if (left == right - 1) {
            if (srcArray[left] > srcArray[right]) {
                swap(srcArray, left, right);
            }
            return;
        }
        int pivot = srcArray[left], i = left, j = right;
        while (i < j) {
            // 左侧，排序完以后，i前面的都<=pivot
            for (int k = i; k < j; i = ++k) {
                if (srcArray[k] > pivot) {
                    swap(srcArray, k, j);
                    j--;
                    break;
                }
            }
            // 右侧，排序完以后，j后面的都>pivot
            for (int k = j; k > i; j = --k) {
                if (srcArray[k] < pivot) {
                    swap(srcArray, i, k);
                    i++;
                    break;
                }
            }
        }
        if (srcArray[i] > pivot) {
            swap(srcArray, left, i - 1);
            quickSort(srcArray, left, i - 2);
            quickSort(srcArray, i, right);
        }else {
            swap(srcArray,left,i);
            quickSort(srcArray, left, i - 1);
            quickSort(srcArray, i + 1, right);
        }
    }

    private static void swap(int[] srcArray, int pos1, int pos2) {
        if (pos1 == pos2) {
            return;
        }
        srcArray[pos1] = srcArray[pos1] ^ srcArray[pos2];
        srcArray[pos2] = srcArray[pos1] ^ srcArray[pos2];
        srcArray[pos1] = srcArray[pos1] ^ srcArray[pos2];
    }

    /**
     * 测试Arrays工具类的parallelPrefix方法，在op为重度操作时，通过多线程并发降低执行时间
     * @throws Exception 抛出所有异常
     */
    private static void parallelPrefix() throws Exception{
        int[] oriArray = new int[1000];
        for (int i = 0; i < oriArray.length; i++) {
            oriArray[i] = i;
        }
        int times = 5;
        long parallelSum = 0;
        long sequentialSum = 0;
        for (int i = 0; i < times; i++) {
            int[] testArray = new int[oriArray.length];
            System.arraycopy(oriArray, 0, testArray, 0, oriArray.length);
            int result;
            long start = System.nanoTime();
            java.util.Arrays.parallelPrefix(testArray,
                    (v1,v2)->{
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return v1+v2;
                    }
            );
            long duration = (System.nanoTime()-start);
            result = testArray[testArray.length-1];
            parallelSum += duration;
            System.out.println("并发" + i + ":" + duration);
            System.arraycopy(oriArray, 0, testArray, 0, oriArray.length);
            start = System.nanoTime();
            for (int j = 1; j < testArray.length; j++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                testArray[j] = testArray[j ] + testArray[j - 1];
            }
            duration = (System.nanoTime()-start);
            sequentialSum += duration;
            System.out.println("串行" + i + ":" + duration);
            if (result != testArray[testArray.length - 1]) {
                throw new Exception("错误");
            }
        }
        System.out.println("并发平均:" + parallelSum / times);
        System.out.println("串行平均:" + sequentialSum / times);
    }

}
