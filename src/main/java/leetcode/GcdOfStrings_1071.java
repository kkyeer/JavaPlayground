package leetcode;

/**
 * @Author: kkyeer
 * @Description: 1071. 字符串的最大公因子
 * 对于字符串 S 和 T，只有在 S = T + ... + T（T 与自身连接 1 次或多次）时，我们才认定 “T 能除尽 S”。
 * <p>
 * 返回最长字符串 X，要求满足 X 能除尽 str1 且 X 能除尽 str2。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：str1 = "ABCABC", str2 = "ABC"
 * 输出："ABC"
 * <p>
 * 示例 2：
 * <p>
 * 输入：str1 = "ABABAB", str2 = "ABAB"
 * 输出："AB"
 * <p>
 * 示例 3：
 * <p>
 * 输入：str1 = "LEET", str2 = "CODE"
 * 输出：""
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= str1.length <= 1000
 * 1 <= str2.length <= 1000
 * str1[i] 和 str2[i] 为大写英文字母
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/greatest-common-divisor-of-strings
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-12 09:38
 * @Modified By:
 */
public class GcdOfStrings_1071 {
    public static void main(String[] args) {
        String s1 = "ABCABC";
        String s2 = "ABC";
        System.out.println(gcdOfStrings2(s1, s2));
        System.out.println(getGcd(28, 3));
    }
/*      数学上有如下定理：
        如果 str1 和 str2 拼接后等于 str2和 str1 拼接起来的字符串（注意拼接顺序不同），
        那么一定存在符合条件的字符串 X，且X的长度为str1长度和str2长度的最大公约数
        证明分两部分，即充分性与必要性
        必要性证明：即需要证明如果存在字符串X，长度为str1长度和str2长度的最大公约数，且符合条件，则str1+str2=str2+str1;
        此时str1可以分成m个X，str2可以分成n个X，则str1+str2和str2+str1均为(m+n)个X，得证
        充分性证明：需要证明如果str1+str2=str2+str1，则一定存在长度为最大公约数的字符串X
        证明：假设str1,str2长度最大公约数为m
        则str1可以均分为(str1.length/m)个字符串，设str1.length/m = an，则有
        str1 = Xa1+Xa2+....+Xan，其中Xa1,Xa2到Xan均为长度为m的字符串
        同样设str2.length/m = bn,则有
        str2 = Xb1+Xb2+....+Xbn
        str1+str2= Xa1+Xa2+Xa3...+Xan+Xb1+Xb2+...+Xbn
        str2+str1= Xb1+Xb2+...+Xbn+Xa1+Xa2+...+Xan
        由于str1+str2=str2+str1,不失一般性，假设an>=bn
        角标图为
        a1 a2 a3 .... a(bn) a(bn+1) ...    an    b1 b2 b3 ... bn-2 bn-1 bn
        b1 b2 b3 ....   bn     a1       a(an-bn) a3 a4 a5 ... an-2 an-1 an
        上下同一个角标对应的字符串全部相等
        则有
        bn = an = a(an-bn)
        对于任何一个an-bn,都有一个对应的b(an-bn),传导下去则可得结论【这里可以想象为上下无限反射】
 */

    public static String gcdOfStrings2(String str1, String str2) {
        if (str1 == null || str2 == null || !(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        return str1.substring(0, getGcd(str1.length(), str2.length()));
    }

    public static String gcdOfStrings(String str1, String str2) {
        int a, b;
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
            } else {
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