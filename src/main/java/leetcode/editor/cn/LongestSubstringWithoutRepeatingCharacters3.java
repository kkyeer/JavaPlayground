//给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 示例 4: 
//
// 
//输入: s = ""
//输出: 0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 5 * 104 
// s 由英文字母、数字、符号和空格组成 
// 
// Related Topics 哈希表 双指针 字符串 Sliding Window 
// 👍 5041 👎 0


package leetcode.editor.cn;

import utils.Assertions;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters3{
    public static void main(String[] args){
        Solution solution = new LongestSubstringWithoutRepeatingCharacters3().new Solution();
        Assertions.assertTrue(solution.lengthOfLongestSubstring("abcabcbb") == 3);
        Assertions.assertTrue(solution.lengthOfLongestSubstring("pwwkew") == 3);
        Assertions.assertTrue(solution.lengthOfLongestSubstring("pwwkeo") == 4);
        Assertions.assertTrue(solution.lengthOfLongestSubstring("bbbbb") == 1);
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lengthOfLongestSubstring2(String s) {
        char[] arr = s.toCharArray();
        int left = 0, right = 0;
        int maxLength = 0;
        Set<Character> characterSet = new HashSet<>();
        char repeatedChar;
        while (left <= right && right < arr.length) {
            if (!characterSet.contains(arr[right])) {
                characterSet.add(arr[right]);
                right++;
            }else {
                maxLength = Math.max(maxLength, right - left );
                repeatedChar = arr[right];
                while (arr[left]!=repeatedChar) {
                    characterSet.remove(arr[left]);
                    left++;
                }
                left++;
                right++;
            }
        }
        return Math.max(maxLength, right - left);
    }

        /**
         * 移动左指针一次，再移动右指针直到结束
         * @param s
         * @return
         */
    public int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        int length = arr.length;
        int right = 0;
        int maxLength = 0;
        Set<Character> characterSet = new HashSet<>();
        for (int i = 0; i < length; i++) {
            while (characterSet.add(arr[right]) && ++right < length) {
            }
            maxLength = Math.max(maxLength, right - i);
            if (right == length) {
                break;
            }
            characterSet.remove(arr[i]);
        }
        return maxLength;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}