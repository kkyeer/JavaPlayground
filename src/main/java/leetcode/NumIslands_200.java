package leetcode;

import utils.Assertions;

import java.util.Arrays;

/**
 * @Author: kkyeer
 * @Description: 200. 岛屿数量
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 *
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 * 示例 1:
 *
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 *
 * 示例 2:
 *
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 输出: 3
 * 解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 下午2:05 20-4-20
 * @Modified By:
 */
public class NumIslands_200 {
    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        Assertions.equal(1, new NumIslands_200().numIslands(grid));
    }
    int[][] result;
    char[][] grid;

    public int numIslands(char[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        this.grid = grid;
        this.result = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            result[i] = new int[grid[i].length];
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    result[i][j] = -1;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1' && result[i][j] < 0) {
                    count++;
                    mark(i, j);
                }
            }
        }
        return count;
    }

    int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public void mark(int i, int j) {
        result[i][j] = 1;
        for (int k = 0; k < 4; k++) {
            int nextI = i + directions[k][0];
            int nextJ = j + directions[k][1];
            if (nextI >= 0 && nextI < grid.length && nextJ >= 0 && nextJ < grid[i].length) {
                if (result[nextI][nextJ] < 0) {
                    mark(nextI, nextJ);
                }
            }
        }        
    }
}
