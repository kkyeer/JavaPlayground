package leetcode;

/**
 * @Author: kkyeer
 * @Description: 151. 翻转字符串里的单词
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 *
 *
 *
 * 示例 1：
 *
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 *
 * 示例 2：
 *
 * 输入: "  hello world!  "
 * 输出: "world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 *
 * 示例 3：
 *
 * 输入: "a good   example"
 * 输出: "example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *
 *
 *
 * 说明：
 *
 *     无空格字符构成一个单词。
 *     输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 *     如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-words-in-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-4-10 09:59
 * @Modified By:
 */
public class ReverseWords_151 {
    public static void main(String[] args) {
        String words = "";
        System.out.println(new ReverseWords_151().reverseWords(words));
    }

    public String reverseWords(String s) {
        char[] arr = s.toCharArray();
        StringBuilder resultBuilder = new StringBuilder();
        int i = arr.length - 1;
        while (i >= 0 && arr[i] == ' ') {
            i--;
        }
        StringBuilder wordBuilder = new StringBuilder();
        while (i >= 0) {
            if (arr[i] != ' ') {
                wordBuilder.append(arr[i--]);
            }else {
                resultBuilder.append(wordBuilder.reverse().toString());
                wordBuilder.setLength(0);
                resultBuilder.append(' ');
                do {
                    i--;
                } while (i >= 0 && arr[i] == ' ');
            }
        }
        if (wordBuilder.length() != 0) {
            resultBuilder.append(wordBuilder.reverse().toString());
        }
        if (resultBuilder.length() > 0 && resultBuilder.charAt(resultBuilder.length() - 1) == ' ') {
            resultBuilder.deleteCharAt(resultBuilder.length() - 1);
        }
        return resultBuilder.toString();
    }
}