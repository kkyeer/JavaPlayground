package leetcode;

/**
 * @Author: kkyeer
 * @Description: 面试题01.06 字符串压缩
 * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串。你可以假设字符串中只包含大小写英文字母（a至z）。
 *
 * 示例1:
 *
 *  输入："aabcccccaaa"
 *  输出："a2b1c5a3"
 *
 * 示例2:
 *
 *  输入："abbccd"
 *  输出："abbccd"
 *  解释："abbccd"压缩后为"a1b2c2d1"，比原字符串长度更长。
 *
 * 提示：
 *
 *     字符串长度在[0, 50000]范围内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/compress-string-lcci
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-3-16 09:50
 * @Modified By:
 */
public class CompressString_M0106 {
    public static void main(String[] args) {
        System.out.println(new CompressString_M0106().compressString("bb"));;
    }

    private String compressString(String S) {
        char[] charArray = S.toCharArray();
        if (charArray.length <= 1) {
            return S;
        }
        char c = charArray[0];
        int cLength = 1;

        int originalLength = S.length();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c);
        int currentLength = 1;
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] != c) {
                stringBuilder.append(cLength);
                c = charArray[i];
                stringBuilder.append(c);
                currentLength+=2;
                cLength = 1;
                if (currentLength >= originalLength-1) {
                    return S;
                }
            }else {
                cLength++;
            }
        }
        stringBuilder.append(cLength);
        if (stringBuilder.length() >= S.length()) {
            return S;
        }
        return stringBuilder.toString();
    }
}