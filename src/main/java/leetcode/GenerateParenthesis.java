package leetcode;

import java.util.ArrayList;
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
        List<String> result = new GenerateParenthesis().generateParenthesis(4);
        for (String s : result) {
            System.out.println(s);
        }
    }

    boolean[] pairs;
    int n;
    private int[] factorial;
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0) {
            return result;
        }
        this.n = n;
        pairs = new boolean[2 * n];
        factorial = new int[n];
        pairs[0] = true;
        pairs[2*n-1] = false;
        StringBuilder stringBuilder = new StringBuilder("");
        int[] pointers ;
        for (int i = 1; i < n - 1; i++) {
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
            for (int l = 0; l < 2 * n; l++) {
                stringBuilder.append(pairs[l] ? '(' : ')');
            }
            result.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            int left = i - 1;
            int right = n - i - 1;
            int leftTimes = c(n - 1, i);
            int rightTimes = c(n - 1, n - 1 - i);
            for (int j = 0; j < leftTimes; j++) {
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
                for (int k = 0; k < rightTimes; k++) {
                    if (pointers[right] == 2 * n - 1 || pairs[pointers[right] + 1]) {
                        right--;
                    }
                    if (right < n) {
                        break;
                    }
                    if (pointers[right] > n) {
                        pairs[pointers[right]] = false;
                        pointers[right] += 1;
                        pairs[pointers[right]] = true;
                    }
                    for (int l = 0; l < 2 * n - 1; l++) {
                        stringBuilder.append(pairs[l] ? '(' : ')');
                    }
                    result.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }
        }
        return result;
    }

    public int c(int n, int r) {
        return fact(n) / fact(r) / fact(n - r);
    }

    public int fact(int num) {
        if (factorial[num - 1] != 0) {
            return factorial[num - 1];
        }
        if (num == 1) {
            return 1;
        }
        return num * fact(num - 1);
    }
}