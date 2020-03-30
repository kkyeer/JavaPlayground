package leetcode;

/**
 * @Author: kkyeer
 * @Description: 面试题62. 圆圈中最后剩下的数字
 * 0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。
 *
 * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
 *
 *
 *
 * 示例 1：
 *
 * 输入: n = 5, m = 3
 * 输出: 3
 *
 * 示例 2：
 *
 * 输入: n = 10, m = 17
 * 输出: 2
 *
 *
 *
 * 限制：
 *
 *     1 <= n <= 10^5
 *     1 <= m <= 10^6
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-30 15:53
 * @Modified By:
 */
public class LastRemaining_M62 {
    public static void main(String[] args) {
        System.out.println(new LastRemaining_M62().lastRemaining(5,3));
    }

    /**
     *
        定义F(n,m)为n个数，按规则每次取出m剩下的数
        定理1，对于同样的n个数，假设第一次从0开始数，每次取出m，剩下x，则如果第一次从i开始数，每次同样取出m，应该剩下(x+i)%n
        设：F(n-1,m)=x，考虑F(n,m)和F(n-1,m)的关系
        有n个数，从0到n-1放到数组里，第一次数m，取出的是(m-1)%n，此时剩下(n-1)个数，且下一次从((m-1)%n+1)%n开始数

        已知从0开始数的时候，F(n-1,m)=x,则从((m-1)%n+1)%n开始数，根据定理1，最终剩下的数应该为(((m-1)%n+1)%n+x)%n=F(n,m)
        这里为什么模n，因为实际上是一个重新映射的过程，即【把((m-1)%n+1)%n作为0以后，环上的其他数字应该放在哪？
        对于0,1,2,3,4,5,0,1,2,3这种，如果取走3，将4作为0则,4,5,0,1,2,4,5
        定理一：两个正整数a，b的和，模另外一个数c，就等于它俩分别模c，模完之后加起来再模。

        (a+b)%c=((a%c)+(b%c))%c

        定理二：一个正整数a，模c，模一遍和模两遍是一样的。

        a%c=(a%c)%c

        化简得(m+x)%n=F(n,m)
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        int x = 0;
        for (int i = 2; i <= n; i++) {
            x = (m + x) % i;
        }
        return x;
    }
}