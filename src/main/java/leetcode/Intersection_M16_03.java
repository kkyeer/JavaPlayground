package leetcode;

import utils.Assertions;

/**
 * @author kkyeer
 * @description: 面试题 16.03. 交点
 * 给定两条线段（表示为起点start = {X1, Y1}和终点end = {X2, Y2}），如果它们有交点，请计算其交点，没有交点则返回空值。
 *
 * 要求浮点型误差不超过10^-6。若有多个交点（线段重叠）则返回 X 值最小的点，X 坐标相同则返回 Y 值最小的点。
 *
 *
 *
 * 示例 1：
 *
 * 输入：
 * line1 = {0, 0}, {1, 0}
 * line2 = {1, 1}, {0, -1}
 * 输出： {0.5, 0}
 *
 * 示例 2：
 *
 * 输入：
 * line1 = {0, 0}, {3, 3}
 * line2 = {1, 1}, {2, 2}
 * 输出： {1, 1}
 *
 * 示例 3：
 *
 * 输入：
 * line1 = {0, 0}, {1, 1}
 * line2 = {1, 0}, {2, 1}
 * 输出： {}，两条线段没有交点
 *
 *
 *
 * 提示：
 *
 *     坐标绝对值不会超过 2^7
 *     输入的坐标均是有效的二维坐标
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @date:Created in 00:58 4-12
 * @modified By:
 */
public class Intersection_M16_03 {
    public static void main(String[] args) {
        int[] start1 ;
        int[] end1 ;
        int[] start2 ;
        int[] end2;
        double[] result;

//        int[] start1 = new int[]{0, 0};
//        int[] end1 = new int[]{1, 0};
//        int[] start2 = new int[]{1, 1};
//        int[] end2 = new int[]{0, -1};
//        double[] result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
//        Assertions.equal(0.5, result[0]);
//        Assertions.equal(0, result[1]);


        start1 = new int[]{0, 0};
        end1 = new int[]{0, 1};
        start2 = new int[]{0, 2};
        end2 = new int[]{0, 3};
        result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
        Assertions.assertTrue(result.length==0);

        start1 = new int[]{0, 3};
        end1 = new int[]{0, 6};
        start2 = new int[]{0, 1};
        end2 = new int[]{0, 5};
        result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
        Assertions.equal(0, result[0]);
        Assertions.equal(3,result[1]);

//        int[] start1 = new int[]{0, 0};
//        int[] end1 = new int[]{1, 1};
//        int[] start2 = new int[]{2, 2};
//        int[] end2 = new int[]{3, 3};
//        double[] result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
//        Assertions.equal(0, result.length);

//        int[] start1 = new int[]{0, 0};
//        int[] end1 = new int[]{1, 1};
//        int[] start2 = new int[]{1, 0};
//        int[] end2 = new int[]{2, 1};
//        double[] result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
//        Assertions.equal(0, result.length);

        start1 = new int[]{1, 0};
        end1 = new int[]{1, 1};
        start2 = new int[]{-1, 0};
        end2 = new int[]{3, 2};
        result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
        Assertions.equal(2, result.length);
        Assertions.equal(1.0, result[0]);
        Assertions.equal(1.0, result[1]);

        start1 = new int[]{0, 0};
        end1 = new int[]{3, 3};
        start2 = new int[]{1, 1};
        end2 = new int[]{2, 2};
        result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
        Assertions.equal(2, result.length);
        Assertions.equal(1.0, result[0]);
        Assertions.equal(1.0, result[1]);

        start1 = new int[]{-25, 67};
        end1 = new int[]{-67, 24};
        start2 = new int[]{-52, 48};
        end2 = new int[]{-45, 43};
        result = new Intersection_M16_03().intersection(start1, end1, start2, end2);
        Assertions.equal(2, result.length);
        Assertions.equal(-47.02739726027397, result[0]);
        Assertions.equal(44.448140900195696, result[1]);
    }

    public double[] intersection(int[] start1, int[] end1, int[] start2, int[] end2) {
        double[] result = new double[2];
        // 有任意一个是单点的情况
        boolean dot1 = end1[1] == start1[1] && end1[0] == start1[0];
        boolean dot2 = end2[1] == start2[1] && end2[0] == start2[0];
        if (dot1 && dot2) {
            if (end1[1] == end2[1]) {
                result[0] = end1[0];
                result[1] = end1[0];
                return result;
            }else {
                result = new double[0];
                return result;
            }
        } else if (dot1) {
            return dotInSegment(start1, start2, end2);
        } else if (dot2) {
            return dotInSegment(start2, start1, end1);
        }
        
//        对两条线段做处理，保证start[0]<=end[0]
        
        // 计算k1,k2
        int[] k1 = calcK(start1, end1);
        int[] k2 = calcK(start2, end2);

        // 计算b1,b2
        int[] b1 = calcB(start1, end1);
        int[] b2 = calcB(start2, end2);
        // 斜率相等的情况
        if (k1[0] == k2[0] && k1[1] == k2[1]) {
            if (b2[0] != b1[0] || b2[1] != b1[1]) {
                return new double[0];
            }
            return getDotInSameLineint(start1, end1, start2, end2);
        }
        // 计算交点
        int[] x = new int[2];
        int[] y = new int[2];
        if (k1[0] == Integer.MAX_VALUE) {
            if (k2[0] == Integer.MAX_VALUE) {
                return getDotInSameLineint(start1, start2, end1, end2);
            }
            x[1] = 1;
            x[0] = start1[0];
            y = fractionAdd(fractionMultiInt(k2, x[0]), b2);
        }else if (k2[0] == Integer.MAX_VALUE){
            x[1] = 1;
            x[0] = start2[0];
            y = fractionAdd(fractionMultiInt(k1, x[0]), b1);
        }else {
            x = fractionDivide(fractionMinus(b2, b1), fractionMinus(k1, k2));
            y = fractionDivide(fractionMinus(fractionMultiply(k1, b2), fractionMultiply(k2, b1)), fractionMinus(k1, k2));

        }
        result[0] = x[0] * 1.0 / x[1];
        result[1] = y[0] * 1.0 / y[1];
        // 判断是否同时在两条线段上
        if (isDotInSegment(result, start1, end1) && isDotInSegment(result, start2, end2)) {
            return result;
        }else {
            return new double[0];
        }
    }

    public double[] getDotInSameLineint(int[] start1, int[] end1, int[] start2, int[] end2) {
        double[] result = new double[2];
        int[][] sorted1 = new int[2][];
        if (start1[0] <= end1[0]) {
            sorted1[0] = start1;
            sorted1[1] = end1;
        }else {
            sorted1[0] = end1;
            sorted1[1] = start1;
        }
        int[][] sorted2 = new int[2][];
        if (start2[0] <= end2[0]) {
            sorted2[0] = start2;
            sorted2[1] = end2;
        }else {
            sorted2[0] = end2;
            sorted2[1] = start2;
        }
        int[] y1range = new int[]{Math.min(start1[1], end1[1]), Math.max(start1[1], end1[1])};
        int[] y2range = new int[]{Math.min(start2[1], end2[1]), Math.max(start2[1], end2[1])};
        if ((sorted1[1][0] < sorted2[0][0] || sorted2[1][0] < sorted1[0][0]) || (y1range[1] < y2range[0] || y2range[1] < y1range[0])) {
            return new double[0];
        } else {
            if (sorted1[0][0] == sorted2[0][0]) {
                result[0] = sorted1[0][0];
                result[1] = Math.max(sorted1[0][1], sorted2[0][1]);
            } else if (sorted1[0][0] < sorted2[0][0]) {
                result[0] = sorted2[0][0];
                result[1] = sorted2[0][1];
            } else {
                result[0] = sorted1[0][0];
                result[1] = sorted1[0][1];
            }
            return result;
        }
    }
    
    public boolean isDotInSegment(double[] dot,int[] start, int[] end){
        int[] xRange = new int[]{Math.min(start[0], end[0]), Math.max(start[0], end[0])};
        int[] yRange = new int[]{Math.min(start[1], end[1]), Math.max(start[1], end[1])};
        return dot[0] >= xRange[0] && dot[0] <= xRange[1] && dot[1] >= yRange[0] && dot[1] <= yRange[1];
    }

    public double[] dotInSegment(int[] dot,int[] start, int[] end){
        double[] result = new double[0];
        int[] k = calcK(start, end);
        int[] b = calcB(start, end);
        if (k[0] == Integer.MAX_VALUE) {
            if (dot[0] == start[0]) {
                result = new double[2];
                result[0] = dot[0];
                result[1] = dot[1];
            }
        }else {
            int[] kx = fractionMultiInt(k, dot[0]);
            int[] calcY = fractionAdd(kx, b);
            if (calcY[1] == 1 && calcY[0] == dot[1]) {
                result = new double[2];
                result[0] = dot[0];
                result[1] = dot[1];
            }
        }
        return result;
    }

    public int[] fractionMultiInt(int[] num1, int num2) {
        int[] result = new int[2];
        result[0] = num1[0] * num2;
        result[1] = num1[1];
        divide(result);
        return result;
    }

    public int[] fractionMultiply(int[] fraction1, int[] fraction2) {
        int[] result = new int[2];
        result[0] = fraction1[0] * fraction2[0];
        result[1] = fraction1[1] * fraction2[1];
        divide(result);
        return result;
    }

    public int[] fractionAdd(int[] fraction1, int[] fraction2) {
        int[] result = new int[2];
        result[0] = fraction1[0] * fraction2[1] + fraction1[1] * fraction2[0];
        result[1] = fraction1[1] * fraction2[1];
        divide(result);
        return result;

    }

    public int[] fractionMinus(int[] fraction1, int[] fraction2) {
        int[] result = new int[2];
        result[0] = fraction1[0] * fraction2[1] - fraction1[1] * fraction2[0];
        result[1] = fraction1[1] * fraction2[1];
        divide(result);
        return result;

    }

    public int[] fractionDivide(int[] fraction1, int[] fraction2) {
        int[] result = new int[2];
        result[0] = fraction1[0] * fraction2[1] ;
        result[1] = fraction1[1] * fraction2[0];
        divide(result);
        return result;

    }

    public int[] calcK(int[] start, int[] end) {
        int[] result = new int[2];
        result[0] = start[1] - end[1];
        result[1] = start[0] - end[0];
        divide(result);
        return result;
    }

    public int[] calcB(int[] start, int[] end) {
        int[] result = new int[2];
        result[0] = (int) ((long) (start[1] * end[0]) - (long) (start[0] * end[1]));
        result[1] = end[0] - start[0];
        divide(result);
        return result;
    }

    public void divide(int[] nums) {
        int sign0 = sign(nums[0]);
        if (sign0 == 0) {
            nums[0] = 0;
            nums[1] = 1;
            return;
        }
        nums[0] = Math.abs(nums[0]);
        int sign1 = sign(nums[1]);
        if (sign1 == 0) {
            nums[0] = Integer.MAX_VALUE;
            nums[1] = 1;
            return;
        }
        if (sign0 == sign1) {
            sign0 = sign1 = 1;
        }
        nums[1] = Math.abs(nums[1]);
        int gcd = gcd(nums[0], nums[1]);
        nums[0] = nums[0] / gcd * sign0;
        nums[1] = nums[1] / gcd * sign1;
    }

    public int sign(int a) {
        return Integer.compare(a, 0);
    }

    public int gcd(int m, int n) {
        int big = Math.max(m, n);
        int little = Math.min(m, n);
        int remain;
        while ((remain=big%little) != 0) {
            big = little;
            little = remain;
        }
        return little;
    }
}
