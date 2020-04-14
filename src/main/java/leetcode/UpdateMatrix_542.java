package leetcode;

import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: 542. 01 矩阵
 * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
 *
 * 两个相邻元素间的距离为 1 。
 *
 * 示例 1:
 * 输入:
 *
 * 0 0 0
 * 0 1 0
 * 0 0 0
 *
 * 输出:
 *
 * 0 0 0
 * 0 1 0
 * 0 0 0
 *
 * 示例 2:
 * 输入:
 *
 * 0 0 0
 * 0 1 0
 * 1 1 1
 *
 * 输出:
 *
 * 0 0 0
 * 0 1 0
 * 1 2 1
 *
 * 注意:
 *
 *     给定矩阵的元素个数不超过 10000。
 *     给定矩阵中至少有一个元素是 0。
 *     矩阵中的元素只在四个方向上相邻: 上、下、左、右。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/01-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Date:Created in  2020-4-15 01:48
 * @Modified By:
 */
public class UpdateMatrix_542 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]
                {
                        {0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,1,0,1,1,0,0,1,1,1,0,1,1,1,0}
                }
                ;
        int[][] result = new UpdateMatrix_542().updateMatrix(matrix);
        for (int[] line : result) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int[][] updateMatrix(int[][] matrix) {
        int[][] result;
        if (matrix.length == 0) {
            result = new int[0][];
            return result;
        }
        result = new int[matrix.length][];
//        左上->右下
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new int[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0)  {
                    int min = matrix.length + matrix[i].length;
                    if (i > 0) {
                        min = Math.min(min, result[i - 1][j]);
                    }
                    if (j > 0) {
                        min = Math.min(min, result[i][j - 1]);
                    }
                    result[i][j] = min + 1;
                }
            }
        }
//        右下->左上
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[i].length - 1; j >= 0; j--) {
                if (matrix[i][j] != 0) {
                    if (i == matrix.length - 1 && j == matrix[i].length - 1) {
                        continue;
                    }
                    int min = Integer.MAX_VALUE;
                    if (i < matrix.length - 1) {
                        min = Math.min(min, result[i+1][j]);
                    }
                    if (j < matrix[i].length - 1) {
                        min = Math.min(min, result[i][j + 1]);
                    }
                    result[i][j] = Math.min(min+1,result[i][j]);
                }
            }
        }
        return result;
    }
}