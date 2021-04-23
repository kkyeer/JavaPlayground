//给定一个二维矩阵，计算其子矩形范围内元素的总和，该子矩阵的左上角为 (row1, col1) ，右下角为 (row2, col2) 。 
//
// 
//上图子矩阵左上角 (row1, col1) = (2, 1) ，右下角(row2, col2) = (4, 3)，该子矩形内元素的总和为 8。 
//
// 
//
// 示例： 
//
// 
//给定 matrix = [
//  [3, 0, 1, 4, 2],
//  [5, 6, 3, 2, 1],
//  [1, 2, 0, 1, 5],
//  [4, 1, 0, 1, 7],
//  [1, 0, 3, 0, 5]
//]
//
//sumRegion(2, 1, 4, 3) -> 8
//sumRegion(1, 1, 2, 2) -> 11
//sumRegion(1, 2, 2, 4) -> 12
// 
//
// 
//
// 提示： 
//
// 
// 你可以假设矩阵不可变。 
// 会多次调用 sumRegion 方法。 
// 你可以假设 row1 ≤ row2 且 col1 ≤ col2 。 
// 
// Related Topics 动态规划 
// 👍 178 👎 0


package leetcode.editor.cn;

import utils.Assertions;

public class RangeSumQuery2dImmutable304{
    public static void main(String[] args){
        int[][] matrix = new int[][]{
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };
        NumMatrix solution = new RangeSumQuery2dImmutable304().new NumMatrix(matrix);
        Assertions.equal(8, solution.sumRegion(2, 1, 4, 3));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class NumMatrix {
        private int[][] sums;

        public NumMatrix(int[][] matrix) {
            if(matrix.length==0){
                sums = new int[0][];
                return;
            }
            int lineLength = matrix[0].length;
            sums = new int[matrix.length][lineLength];
            int lineSum;
            for (int i = 0; i < matrix.length; i++) {
                lineSum = 0;
                for (int j = 0; j < lineLength; j++) {
                    sums[i][j] = (i > 0 ? sums[i - 1][j] : 0) + lineSum + matrix[i][j];
                    lineSum += matrix[i][j];
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sums[row2][col2] - (col1 > 0 ? sums[row2][col1 - 1] : 0) - (row1 > 0 ? sums[row1 - 1][col2] : 0) + (row1 > 0 && col1 > 0 ? sums[row1 - 1][col1 - 1] : 0);
        }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
//leetcode submit region end(Prohibit modification and deletion)

}