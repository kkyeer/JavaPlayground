package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 22. 括号生成
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 *
 *
 * 示例：
 *
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-4-9 19:53
 * @Modified By:
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        List<String> result = new GenerateParenthesis().generateParenthesis(5);
//        for (String s : result) {
//            System.out.println(s);
//        }
    }

    boolean[] pairs;
    int n;
    private int[] factorial;
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0) {
            return result;
        } else if (n == 1) {
            result.add("()");
            return result;
        } else if (n == 2) {
            result.add("()()");
            result.add("(())");
            return result;
        }
        this.n = n;
        pairs = new boolean[2 * n];
        reset();
        factorial = new int[n];
        StringBuilder stringBuilder = new StringBuilder("");
        int[] pointers ;
        for (int i = 1; i <= n - 1; i++) {
            reset();
            pointers = new int[n - 1];
            // left 0~n-1,有i个1
            // right n~2*n-1 n-1-i个1
            for (int j = 0; j < i; j++) {
                pointers[j] = j + 1;
                pairs[j + 1] = true;
            }
            for (int j = 0; j < n - i - 1; j++) {
                pointers[i + j] = n + j;
                pairs[n + j] = true;
            }

            int left = i - 1;
            int right = n - 2;
            int leftTimes = c(n - 1, i);
            do {
                if (left < 0 || right >= n - 1) {
                    continue;
                }
                int rightTimes = c(n - 1, n - 1 - i);
                do {
                    int sum = 0;
                    boolean ok = true;
                    for (int l = 0; l < 2 * n; l++) {
                        sum = sum + (pairs[l] ? 1 : -1);
                        if (sum < 0) {
                            ok = false;
                            break;
                        }
                        stringBuilder.append(pairs[l] ? '(' : ')');
                    }
                    if (ok) {
                        System.out.println(stringBuilder.toString());
                        result.add(stringBuilder.toString());
                    }
                    stringBuilder = new StringBuilder();
                    if (pointers[right] == 2 * n - 2 || pairs[pointers[right] + 1]) {
                        right--;
                    }
                    if (right < i) {
                        break;
                    }
                    if (pointers[right] >= n) {
                        pairs[pointers[right]] = false;
                        pointers[right] += 1;
                        pairs[pointers[right]] = true;
                    }
                } while (--rightTimes > 0);
                right = n - 2;
                for (int j = n; j < 2 * n; j++) {
                    pairs[j] = false;
                }
                for (int j = 0; j < n - i - 1; j++) {
                    pointers[i + j] = n + j;
                    pairs[n + j] = true;
                }
                if (pointers[left] == n - 1 || pairs[pointers[left] + 1]) {
                    left--;
                }
                if (left < 0) {
                    break;
                }
                if (pointers[left] >= 1) {
                    pairs[pointers[left]] = false;
                    pointers[left] += 1;
                    pairs[pointers[left]] = true;
                }
            } while (--leftTimes > 0);
        }
        return result;
    }

    private void reset() {
        Arrays.fill(pairs,false);
        pairs[0] = true;
        pairs[2*n-1] = false;
    }

    public int c(int n, int r) {
        return fact(n) / fact(r) / fact(n - r);
    }

    public int fact(int num) {
        if (num <= 1) {
            return 1;
        }
        if (factorial[num - 1] != 0) {
            return factorial[num - 1];
        }

        return num * fact(num - 1);
    }
}