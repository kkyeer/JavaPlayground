package leetcode;

/**
 * @Author: kkyeer
 * @Description: 岛屿的最大面积
 * 给定一个包含了一些 0 和 1的非空二维数组 grid , 一个 岛屿 是由四个方向 (水平或垂直) 的 1 (代表土地) 构成的组合。你可以假设二维矩阵的四个边缘都被水包围着。
 *
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为0。)
 *
 * 示例 1:
 *
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *
 * 对于上面这个给定矩阵应返回 6。注意答案不应该是11，因为岛屿只能包含水平或垂直的四个方向的‘1’。
 *
 * 示例 2:
 *
 * [[0,0,0,0,0,0,0,0]]
 *
 * 对于上面这个给定的矩阵, 返回 0。
 *
 * 注意: 给定的矩阵grid 的长度和宽度都不超过 50。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-area-of-island
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @Date:Created in 21:55 3-15
 * @Modified By:
 */
public class MaxAreaOfIsland_695 {
    public static void main(String[] args) {
        int[][] grid = new int[][]{  {0,0,1,0,0,0,0,1,0,0,0,0,0},
                                     {0,0,0,0,0,0,0,1,1,1,0,0,0},
                                     {0,1,1,0,1,0,0,0,0,0,0,0,0},
                                     {0,1,0,0,1,1,0,0,1,0,1,0,0},
                                     {0,1,0,0,1,1,0,0,1,1,1,0,0},
                                     {0,0,0,0,0,0,0,0,0,0,1,0,0},
                                     {0,0,0,0,0,0,0,1,1,1,0,0,0},
                                     {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        System.out.println(new MaxAreaOfIsland_695().maxAreaOfIsland(grid));
    }

    private int[][] grid;
    private int area = 0;
//    possibleDirections
    int[][] pd = new int[][]{{-1, 0},  {1, 0},{0, -1}, {0, 1}};

    public int maxAreaOfIsland(int[][] grid) {
        this.grid = grid;
        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    // 左和上都探索过，向右和下探索，如果所有方向上的1都没有mark过，立即返回，否则继续探索直到遇到0
                    grid[i][j] = 0;
                    area = 1;
                    explore(1, 0, i, j);
                    explore(0, 1, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    public void explore(int directionX, int directionY, int row, int column) {
        for (int i = 0; i < pd.length; i ++) {
            int nextX = pd[i][0];
            int nextY = pd[i][1];
            if (nextX != -directionX || nextY != -directionY) {
                int nextRow = row + nextX;
                int nextColumn = column + nextY;
                if (nextRow >= 0 && nextRow < grid.length && nextColumn >= 0 && nextColumn < grid[row].length &&  grid[nextRow][nextColumn] != 0) {
                    area++;
                    grid[nextRow][nextColumn] = 0;
                    explore(nextX, nextY, nextRow, nextColumn);
                }
            }
        }
    }
}
