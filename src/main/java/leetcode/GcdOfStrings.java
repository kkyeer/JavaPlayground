package leetcode;

/**
 * @Author: kkyeer
 * @Description: 1071. 字符串的最大公因子
 * 对于字符串 S 和 T，只有在 S = T + ... + T（T 与自身连接 1 次或多次）时，我们才认定 “T 能除尽 S”。
 *
 * 返回最长字符串 X，要求满足 X 能除尽 str1 且 X 能除尽 str2。
 *
 *
 *
 * 示例 1：
 *
 * 输入：str1 = "ABCABC", str2 = "ABC"
 * 输出："ABC"
 *
 * 示例 2：
 *
 * 输入：str1 = "ABABAB", str2 = "ABAB"
 * 输出："AB"
 *
 * 示例 3：
 *
 * 输入：str1 = "LEET", str2 = "CODE"
 * 输出：""
 *
 *
 *
 * 提示：
 *
 *     1 <= str1.length <= 1000
 *     1 <= str2.length <= 1000
 *     str1[i] 和 str2[i] 为大写英文字母
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/greatest-common-divisor-of-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-12 09:38
 * @Modified By:
 */
public class GcdOfStrings {
    public static void main(String[] args) {
        String s1 = "ABCABC";
        String s2 = "ABC";
        System.out.println(gcdOfStrings(s1, s2));
        System.out.println(getGcd(28,3));
    }

    public static String gcdOfStrings(String str1, String str2) {
        int a,b;
        if ((a = str1.length()) == 0 || (b = str2.length()) == 0) {
            return "";
        }
        int gcd = getGcd(a, b);
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int maxMatch = 0;
        for (int i = gcd; i >= 1; ) {
            if (a % i != 0 || b % i != 0) {
                i--;
                continue;
            }
            boolean match = true;
            // match 需要 1. s1本身[0,i]字符串重复a/i遍，2. s2本身[0,i]字符串重复b/i遍，3. a[0,i]=b[0,i]
            for (int j = 0; j < i; j++) {
                if (s1[j] != s2[j]) {
                    match = false;
                    break;
                }
                for (int k = 0; k < a / i; k++) {
                    if (s1[j] != s1[j + k * i]) {
                        match = false;
                        break;
                    }
                }
                for (int k = 0; k < b / i; k++) {
                    if (s2[j] != s2[j + k * i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    maxMatch = Math.max(maxMatch, j + 1);
                }
            }
            if (match) {
                return str1.substring(0, i);
            }else {
                i = maxMatch;
            }
        }
        return "";
    }

    public static int getGcd(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int c;
        while ((c = max % min) != 0) {
            max = min;
            min = c;
        }
        return min;
    }
}