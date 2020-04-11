package leetcode;

import java.util.StringJoiner;

/**
 * @Author: kkyeer
 * @Description: 面试题13. 机器人的运动范围
 * 上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
 *
 *
 *
 * 示例 1：
 *
 * 输入：m = 2, n = 3, k = 1
 * 输出：3
 *
 * 示例 1：
 *
 * 输入：m = 3, n = 1, k = 0
 * 输出：1
 *
 * 提示：
 *
 *     1 <= n,m <= 100
 *     0 <= k <= 20
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-4-8 09:50
 * @Modified By:
 */
class MovingCount_M13 {
    private static void fun(int m,int n,int k) {
        char[][] matrix = new char[m][];
        for (int i = 0; i < m; i++) {
            matrix[i] = new char[n];
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (int j = 0; j < n; j++) {
                if ((i % 10 + i / 10 + j % 10 + j / 10) > k) {
                    matrix[i][j] = '1';
                } else {
                    matrix[i][j] = '.';
                }
                stringJoiner.add(matrix[i][j]+"");
            }

            System.out.println(stringJoiner.toString());
        }
    }
    public static void main(String[] args) {
//        int m=1,n = 2, k = 1;//2
//        int m=2,n = 3, k = 1;//3
//        int m=3,n = 2, k = 17;//6
//        int m=5,n = 4, k = 0;//1
//        int m=16,n = 8, k = 4;//15
//        int m=26,n = 19, k = 4;//15
//        int m = 38, n = 15, k = 9;//135
        int m = 36, n = 11, k = 15;//362

        fun(m,n,k);
        System.out.println(new MovingCount_M13().movingCount(m,n,k));
    }

    public int m,n;
    public int movingCount(int m, int n, int k) {
        this.m = m;
        this.n = n;
        // 每个m*n的方格可以划为x个10*10的方格，计算横向和纵向每个方格是否能同行
        int iBlocks = m / 10 + (m % 10 == 0 ? 0 : 1);
        int jBlocks = n / 10 + (n % 10 == 0 ? 0 : 1);
        int[][] passFlag = new int[iBlocks][];
        for (int i = 0; i < iBlocks; i++) {
            passFlag[i] = new int[jBlocks];
        }
        int result = 0;
        for (int i = 0; i < iBlocks ; i++) {
            for (int j = 0; j < jBlocks ; j++) {
                if ((i > 0 && passFlag[i - 1][j] > 0) || (j > 0 && passFlag[i][j - 1] > 0)) {
                    passFlag[i][j] = 1;
                    break;
                }
                int area = area(i , j , k);
                if (area < 0) {
                    result += -area;
                    passFlag[i][j] = 1;
                    break;
                }else {
                    result += area;
                }
            }
        }

        return result;
    }

    public int area(int i, int j, int k) {
        int maxI = Math.min(10 * (i + 1) - 1, m - 1);
        int maxJ = Math.min(10 * (j + 1) - 1, n - 1);
        boolean passRU = i + j + maxJ % 10 <= k;
        int iLength = maxI - 10 * i + 1;
        int jLength = maxJ - 10 * j + 1;
        boolean passLD = i + j + maxI % 10 <= k;
        int passLength = k - i - j + 1;
        if (passLD && passRU) {
            // 右下三角
            int ix = k + 1 - i - j - maxJ % 10;
            ix = ix > 9 || ix > maxI % 10 ? 0 : iLength - ix;
            int jx = k + 1 - i - j - maxI % 10;
            jx = jx > 9 || jx > maxJ % 10 ? 0 : jLength - jx;
            int bottom = Math.max(ix, jx);
            int height = Math.min(ix, jx);
            return iLength * jLength - (2 * bottom - height + 1) * jx / 2;
        } else if(!passLD && !passRU){
            // 左上三角
            return -passLength * (passLength + 1) / 2;
        }else if (!passLD && passRU) {
            // 左下不能通过，右上能通过，最后一列
            return -(passLength + Math.max(0, passLength - jLength) + 1) * jLength / 2;
        } else {
            return -(passLength + Math.max(0, passLength - iLength) + 1) * iLength / 2;
        }
    }
}