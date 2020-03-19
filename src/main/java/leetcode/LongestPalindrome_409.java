package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kkyeer
 * @Description: 最长回文串 409
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 *
 * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
 *
 * 注意:
 * 假设字符串的长度不会超过 1010。
 *
 * 示例 1:
 *
 * 输入:
 * "abccccdd"
 *
 * 输出:
 * 7
 *
 * 解释:
 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 * @Date:Created in  2020-3-19 14:59
 * @Modified By:
 */
public class LongestPalindrome_409 {
    public static void main(String[] args) {
        System.out.println(new LongestPalindrome_409().longestPalindrome("dccaccd"));
    }

    public int longestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>(26);
        for(char c :charArray){
            Integer oriValue = map.get(c);
            int next = oriValue == null ? 1 : ++oriValue;
            map.put(c, next);
        }
        boolean gotOdd = false;
        int result = 0;
        for (Map.Entry<Character, Integer> countEntry : map.entrySet()) {
            int x = countEntry.getValue();
            if (!gotOdd && x % 2 != 0) {
                gotOdd = true;
            }
            result += countEntry.getValue() >> 1 << 1;
        }
        return result + (gotOdd ? 1 : 0);
    }
}