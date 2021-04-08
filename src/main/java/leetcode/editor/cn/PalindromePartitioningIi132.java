//给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。 
//
// 返回符合要求的 最少分割次数 。 
//
// 
// 
// 
//
// 示例 1： 
//
// 
//输入：s = "aab"
//输出：1
//解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
// 
//
// 示例 2： 
//
// 
//输入：s = "a"
//输出：0
// 
//
// 示例 3： 
//
// 
//输入：s = "ab"
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 2000 
// s 仅由小写英文字母组成 
// 
// 
// 
// Related Topics 动态规划 
// 👍 287 👎 0


package leetcode.editor.cn;

import utils.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromePartitioningIi132{
    public static void main(String[] args){
        Solution solution = new PalindromePartitioningIi132().new Solution();
        Assertions.equal(1,solution.minCut("apjesgpsxoeiokmqmfgvjslcjukbqxpsobyhjpbgdfruqdkeiszrlmtwgfxyfostpqczidfljwfbbrflkgdvtytbgqalguewnhvvmcgxboycffopmtmhtfizxkmeftcucxpobxmelmjtuzigsxnncxpaibgpuijwhankxbplpyejxmrrjgeoevqozwdtgospohznkoyzocjlracchjqnggbfeebmuvbicbvmpuleywrpzwsihivnrwtxcukwplgtobhgxukwrdlszfaiqxwjvrgxnsveedxseeyeykarqnjrtlaliyudpacctzizcftjlunlgnfwcqqxcqikocqffsjyurzwysfjmswvhbrmshjuzsgpwyubtfbnwajuvrfhlccvfwhxfqthkcwhatktymgxostjlztwdxritygbrbibdgkezvzajizxasjnrcjwzdfvdnwwqeyumkamhzoqhnqjfzwzbixclcxqrtniznemxeahfozp"));

        Assertions.equal(1,solution.minCut("ababababababababababababcbabababababababababababa"));

        Assertions.equal(1,solution.minCut("aab"));
        Assertions.equal(0,solution.minCut("a"));
        Assertions.equal(1,solution.minCut("abaabac"));
        Assertions.equal(1,solution.minCut("aaabaa"));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private boolean[][] flag;
        private int l;
        private char[] chars;
        private List<Integer> cuts = new ArrayList<>();
        private int min;

        public int minCut(String s) {
            l = s.length();
            flag = new boolean[l][l];
            for (int i = 0; i < l; i++) {
                Arrays.fill(flag[i], true);
            }
            chars = s.toCharArray();
            min = s.length();
            for (int i = l-1; i >=0 ; i--) {
                for (int j = i + 1; j < l; j++) {
                    flag[i][j] = chars[i] == chars[j] && flag[i + 1][j - 1];
                }
            }
            for (int i = 0; i < l; i++) {
                for (int j = i; j < l; j++) {

                }
            }
            return min;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)
//    慢
    class Solution2 {
        private boolean[][] flag;
        private int l;
        private char[] chars;
        private List<Integer> cuts = new ArrayList<>();
        private int min;

        public int minCut(String s) {
            l = s.length();
            flag = new boolean[l][l];
            for (int i = 0; i < l; i++) {
                Arrays.fill(flag[i], true);
            }
            chars = s.toCharArray();
            min = s.length();
            for (int i = l-1; i >=0 ; i--) {
                for (int j = i + 1; j < l; j++) {
                    flag[i][j] = chars[i] == chars[j] && flag[i + 1][j - 1];
                }
            }
            dfs(0);
            return min;
        }

        private void dfs(int i) {
            if (cuts.size() > min) {
                return;
            }
            if (i == l) {
                min = Math.min(min, cuts.size()-1);
            }
            for (int j = l - 1; j >= i; j--) {
                if (flag[i][j]) {
                    cuts.add(j);
                    dfs(j + 1);
                    cuts.remove(cuts.size() - 1);
                }
            }
        }
    }
}