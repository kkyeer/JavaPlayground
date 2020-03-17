package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 1160 拼写单词
 * 给你一份『词汇表』（字符串数组） words 和一张『字母表』（字符串） chars。
 *
 * 假如你可以用 chars 中的『字母』（字符）拼写出 words 中的某个『单词』（字符串），那么我们就认为你掌握了这个单词。
 *
 * 注意：每次拼写时，chars 中的每个字母都只能用一次。
 *
 * 返回词汇表 words 中你掌握的所有单词的 长度之和。
 *
 *
 *
 * 示例 1：
 *
 * 输入：words = ["cat","bt","hat","tree"], chars = "atach"
 * 输出：6
 * 解释：
 * 可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。
 *
 * 示例 2：
 *
 * 输入：words = ["hello","world","leetcode"], chars = "welldonehoneyr"
 * 输出：10
 * 解释：
 * 可以形成字符串 "hello" 和 "world"，所以答案是 5 + 5 = 10。
 *
 *
 *
 * 提示：
 *
 *     1 <= words.length <= 1000
 *     1 <= words[i].length, chars.length <= 100
 *     所有字符串中都仅包含小写英文字母
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-words-that-can-be-formed-by-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Date:Created in 20:56 3-17
 * @Modified By:
 */
public class CountCharacters_1160 {
    public static void main(String[] args) {
        String[] words = new String[]{"hello","world","leetcode"};
        String chars = "welldonehoneyr";
        System.out.println(new CountCharacters_1160().countCharacters(words, chars));
    }

    public int countCharacters(String[] words, String chars) {
        // Character的hashcode为char的int值
        Map<Character, Integer> alphabet = new HashMap<>(26);
        // 存储当前word每个char的剩余数量，如果为负数表示没有剩余
        Map<Character, Integer> record = new HashMap<>(26);
        for (char c : chars.toCharArray()) {
            alphabet.compute(c,(k,i)-> i==null?1:++i);
        }
        int result = 0;
        for (String word : words) {
            record.clear();
            char[] wChars = word.toCharArray();
            boolean contains = true;
            for (int i = 0; i < wChars.length; i++) {
                char currentC = wChars[i];
                Integer supportNumber = record.get(currentC);
                Integer alphabetNumber = alphabet.get(currentC);
                // 下述情况下无法组成单词：1. 字母表里不包含；2. 字母表里包含，但record里已经是0了；
                if (alphabetNumber == null || (supportNumber != null && supportNumber == 0)) {
                    contains = false;
                    break;
                }else {
                    record.put(currentC, supportNumber == null ? --alphabetNumber : --supportNumber);
                }
            }
            if (contains) {
                result += wChars.length;
            }
        }
        return result;
    }
}
