package leetcode;

/**
 * @Author: kkyeer
 * @Description:
 * 994. 腐烂的橘子
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 *
 *     值 0 代表空单元格；
 *     值 1 代表新鲜橘子；
 *     值 2 代表腐烂的橘子。
 *
 *     每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 *
 *     返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 *
 *
 *
 *     示例 1：
 *
 *     输入：[[2,1,1],[1,1,0],[0,1,1]]
 *     输出：4
 *
 *     示例 2：
 *
 *     输入：[[2,1,1],[0,1,1],[1,0,1]]
 *     输出：-1
 *     解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 *
 *     示例 3：
 *
 *     输入：[[0,2]]
 *     输出：0
 *     解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 *
 *     来源：力扣（LeetCode）
 *     链接：https://leetcode-cn.com/problems/rotting-oranges
 *     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-4 10:31
 * @Modified By:
 */
public class OrangesRotting994 {
    public static void main(String[] args) {
        int[][] input = new int[][]{{0,1,1,2},{0,1,1,0},{1,1,2,0}};
        System.out.println(orangesRotting(input));
    }

    private static int orangesRotting(int[][] grid) {
        // 通过标记数组来标记腐烂所需时间
        int[][] marks = new int[grid.length][];
        for (int i = 0; i < marks.length; i++) {
            int[] line = grid[i];
            marks[i] = new int[line.length];
        }
        for (int i = 0; i < grid.length; i++) {
            int[] line = grid[i];
            for (int j = 0; j < line.length; j++) {
                if (grid[i][j] == 2) {
                    marks[i][j] = -1;
                    spreadRotting(i - 1, j, marks, grid, 1, 0);
                    spreadRotting(i + 1, j, marks, grid, 1, 1);
                    spreadRotting(i, j - 1, marks, grid, 1, 2);
                    spreadRotting(i, j + 1, marks, grid, 1, 3);
                }
            }
        }
        int max=0;
        for (int i = 0; i < marks.length; i++) {
            int[] line = marks[i];
            for (int j = 0; j < line.length; j++) {
                if (grid[i][j] == 1 && marks[i][j] == 0) {
                    return -1;
                } else {
                    max = Math.max(marks[i][j], max);
                }
            }
        }
        return max;
    }

    // 腐败扩散

    /**
     *
     * @param i
     * @param j
     * @param marks
     * @param age
     * @param spreadDirection 扩散方向 0:up,1:down,2:left,3:right
     */
    private static void spreadRotting(int i, int j, int[][] marks,int[][] input, int age,int spreadDirection) {
        if (i < 0 || i >= marks.length||j < 0) {
            return;
        }else {
            int[] line = marks[i];
            if (j >= line.length) {
                return;
            }
        }
        // 如果当前位置有橘子且，前面没感染过或者前面感染的代数大于当前代数
        if (input[i][j] == 1 && (marks[i][j] == 0 || marks[i][j] > age)) {
            marks[i][j] = age;
            // up
            if (spreadDirection != 1 ) {
                spreadRotting(i - 1, j, marks, input, age + 1, 0);
            }
            // down
            if (spreadDirection != 0 ) {
                spreadRotting(i + 1, j, marks, input, age + 1, 1);
            }
            // left
            if (spreadDirection != 3 ) {
                spreadRotting(i, j - 1, marks, input, age + 1, 2);
            }
            // right
            if (spreadDirection != 2 ) {
                spreadRotting(i, j + 1, marks, input, age + 1, 3);
            }
        }

    }
}
