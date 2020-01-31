package taste.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: kkyeer
 * @Description: 文本左右对齐
 * 给定一个单词数组和一个长度 maxWidth，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
 *
 * 你应该使用“贪心算法”来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
 *
 * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
 *
 * 文本的最后一行应为左对齐，且单词之间不插入额外的空格。
 *
 * 说明:
 *
 *     单词是指由非空格字符组成的字符序列。
 *     每个单词的长度大于 0，小于等于 maxWidth。
 *     输入单词数组 words 至少包含一个单词。
 *
 * 示例:
 *
 * 输入:
 * words = ["This", "is", "an", "example", "of", "text", "justification."]
 * maxWidth = 16
 * 输出:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 *
 * 示例 2:
 *
 * 输入:
 * words = ["What","must","be","acknowledgment","shall","be"]
 * maxWidth = 16
 * 输出:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * 解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
 *      因为最后一行应为左对齐，而不是左右两端对齐。
 *      第二行同样为左对齐，这是因为这行只包含一个单词。
 *
 * 示例 3:
 *
 * 输入:
 * words = ["Science","is","what","we","understand","well","enough","to","explain",
 *          "to","a","computer.","Art","is","everything","else","we","do"]
 * maxWidth = 20
 * 输出:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/text-justification
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in  2020-1-31 18:17
 * @Modified By:
 */
public class TextJustification68 {
    public static void main(String[] args) throws Exception {
        String[] words = new String[]{"What","must","be","acknowledgment","shall","be"};
        int maxWidth = 16;
        System.out.println(fullJustify(words, maxWidth));
    }

    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int wordStart = 0;
        int wordEnd = -1;
        int wordWidth = 0;
        int blankCount;
        for (int i = 0; i < words.length; i++) {
            String nextWord = words[i];
            if (wordWidth + (wordEnd - wordStart + 1) + nextWord.length() <= maxWidth) {
                wordEnd = i;
                wordWidth += nextWord.length();
            } else {
                blankCount = maxWidth - wordWidth;
                int avg = wordEnd == wordStart ? 0 : blankCount / (wordEnd - wordStart);
                int remain = wordEnd == wordStart ? blankCount : blankCount % (wordEnd - wordStart);
                stringBuilder.append(words[wordStart]);
                StringBuilder blankBuilder = new StringBuilder();
                for (int j = 0; j < avg; j++) {
                    blankBuilder.append(" ");
                }
                String blanks = blankBuilder.toString();
                int alreadyAddedRemain = 0;
                for (int j = wordStart + 1; j <= wordEnd; j++) {
                    stringBuilder.append(blanks);
                    if (alreadyAddedRemain++ < remain) {
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(words[j]);
                }
                if (alreadyAddedRemain < remain) {
                    for (int k = 0; k < remain - alreadyAddedRemain; k++) {
                        stringBuilder.append(" ");
                    }
                }
                lines.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                wordStart = wordEnd + 1;
                wordEnd = wordStart;
                wordWidth = nextWord.length();
            }
        }
        if (wordStart < words.length) {
            for (int j = wordStart; j < words.length; j++) {
                stringBuilder.append(words[j]);
                stringBuilder.append(" ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            int remain = maxWidth - stringBuilder.length();
            for (int i = 0; i < remain; i++) {
                stringBuilder.append(" ");
            }
            lines.add(stringBuilder.toString());
        }
        return lines;
    }
}
