package leetcode;

import utils.Assertions;

import java.util.HashMap;
import java.util.Map;

import static utils.Assertions.equal;

/**
 * @author kkyeer
 * @description: 887. 鸡蛋掉落
 * 你将获得 K 个鸡蛋，并可以使用一栋从 1 到 N  共有 N 层楼的建筑。
 *
 * 每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。
 *
 * 你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F 的楼层落下的鸡蛋都会碎，从 F 楼层或比它低的楼层落下的鸡蛋都不会破。
 *
 * 每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层 X 扔下（满足 1 <= X <= N）。
 *
 * 你的目标是确切地知道 F 的值是多少。
 *
 * 无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？
 *
 *
 *
 * 示例 1：
 *
 * 输入：K = 1, N = 2
 * 输出：2
 * 解释：
 * 鸡蛋从 1 楼掉落。如果它碎了，我们肯定知道 F = 0 。
 * 否则，鸡蛋从 2 楼掉落。如果它碎了，我们肯定知道 F = 1 。
 * 如果它没碎，那么我们肯定知道 F = 2 。
 * 因此，在最坏的情况下我们需要移动 2 次以确定 F 是多少。
 *
 * 示例 2：
 *
 * 输入：K = 2, N = 6
 * 输出：3
 *
 * 示例 3：
 *
 * 输入：K = 3, N = 14
 * 输出：4
 *
 *
 *
 * 提示：
 *
 *     1 <= K <= 100
 *     1 <= N <= 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/super-egg-drop
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date:Created in 19:11 4-11
 * @modified By:
 */
class SuperEggDrop_887 {
    public static void main(String[] args) {
        Assertions.equal(3,new SuperEggDrop_887().superEggDrop(2, 4) );
        Assertions.equal(3, new SuperEggDrop_887().superEggDrop(2, 6));
        equal(4,new SuperEggDrop_887().superEggDrop(3, 14) );
        Assertions.equal(1,new SuperEggDrop_887().superEggDrop(2, 1) );
        equal(new SuperEggDrop_887().superEggDrop(2, 9) , 4);
        equal(8,new SuperEggDrop_887().superEggDrop(3, 80) );
        equal(18,new SuperEggDrop_887().superEggDrop(4, 4000) );
//        Assertions.equal(18,new SuperEggDrop_887().superEggDrop(5, 10000) );
    }

    Map<Integer, Map<Integer, Integer>> cache = new HashMap<>();
    int count = 0;

    public int superEggDrop(int K, int N) {
        if (N == 0) {
            return 0;
        }
        if (K == 1 || N == 1) {
            return N;
        } else if (N <= 3) {
            return 2;
        } else  if (K >= N) {
            return superEggDrop(K-1, N);
        } else {
            Map<Integer, Integer> resultMap = cache.getOrDefault(K,new HashMap<>(K));

            if (resultMap.containsKey(N)) {
                return resultMap.get(N);
            }
            count++;
            int left = 1;
            int right = N;
            int r1 = 0;
            int r2 = 0;
            int lr1 = Integer.MAX_VALUE;
            int lr2 =  Integer.MAX_VALUE;
            int rr1 =  Integer.MAX_VALUE;
            int rr2 =  Integer.MAX_VALUE;
            while (left + 1 < right) {
                int i = (left+right)/2;
                r1 = superEggDrop(K - 1, i);
                r2 = superEggDrop(K, N - 1 - i);
                if (r1 > r2) {
                    right = i;
                    rr1 = r1;
                    rr2 = r2;
                } else if (r2==r1) {
                    left = right = i;
                    lr1 = rr1 = r1;
                    lr2 = rr2 = r2;
                } else {
                    left = i;
                    lr1 = r1;
                    lr2 = r2;
                }
            }
            int result = 1 + Math.min(Math.max(lr1,lr2),Math.max(rr1, rr2));
            resultMap.put(N, result);
            cache.put(K, resultMap);
            System.out.println(count);
            return result;
        }
    }

}
