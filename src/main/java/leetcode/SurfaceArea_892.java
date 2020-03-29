package leetcode;

/**
 * @Author: kkyeer
 * @Description: 892.三维形体的表面积
 * 在 N * N 的网格上，我们放置一些 1 * 1 * 1  的立方体。
 *
 * 每个值 v = grid[i][j] 表示 v 个正方体叠放在对应单元格 (i, j) 上。
 *
 * 请你返回最终形体的表面积。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[[2]]
 * 输出：10
 *
 * 示例 2：
 *
 * 输入：[[1,2],[3,4]]
 * 输出：34
 *
 * 示例 3：
 *
 * 输入：[[1,0],[0,2]]
 * 输出：16
 *
 * 示例 4：
 *
 * 输入：[[1,1,1],[1,0,1],[1,1,1]]
 * 输出：32
 *
 * 示例 5：
 *
 * 输入：[[2,2,2],[2,1,2],[2,2,2]]
 * 输出：46
 *
 *
 *
 * 提示：
 *
 *     1 <= N <= 50
 *     0 <= grid[i][j] <= 50
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/surface-area-of-3d-shapes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-25 09:20
 * @Modified By:
 */
public class SurfaceArea_892 {
    public static void main(String[] args) {
        int[][] arr  = new int[][]{{1,1,1},{1,0,1},{1,1,1}};
        System.out.println(new SurfaceArea_892().surfaceArea(arr));
    }

    public int surfaceArea(int[][] arr) {
        int num = 0;
        int ori = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                num = arr[i][j];
                ori = num == 0 ? 0 : 2 + 4 * num;
                sum += ori - blocked(arr, i, j);
            }
        }
        return sum;
    }

    public int blocked(int[][] arr, int i, int j) {
        int sum = 0;
        // up
        if (i - 1 >= 0) {
            sum += Math.min(arr[i - 1][j], arr[i][j]);
        }
        // right
        if (j + 1 < arr.length) {
            sum += Math.min(arr[i][j + 1], arr[i][j]);
        }
        // left
        if (j - 1 >= 0) {
            sum += Math.min(arr[i][j - 1], arr[i][j]);
        }
        // down
        if (i + 1 < arr.length) {
            sum += Math.min(arr[i + 1][j], arr[i][j]);
        }
        return sum;
    }
}