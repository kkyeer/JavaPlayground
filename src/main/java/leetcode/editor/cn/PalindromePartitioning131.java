//给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。 
//
// 返回 s 所有可能的分割方案。 
//
// 示例: 
//
// 输入: "aab"
//输出:
//[
//  ["aa","b"],
//  ["a","a","b"]
//] 
// Related Topics 深度优先搜索 动态规划 回溯算法 
// 👍 558 👎 0


package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromePartitioning131{
    public static void main(String[] args){
        Solution solution = new PalindromePartitioning131().new Solution();
        System.out.println(solution.partition("efe"));
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
        /**
         * 1. 预处理，使用DP来判断[i-j]是否为回文串
         * 2. 回溯，使用双指针来指向当前已经处理到的字符串
         * 3. 关键点，每次向回追溯，把上次的字符串remove掉
         * @param s
         * @return
         */
        int l = 0;
        boolean[][] flag;
        char[] chars;
        List<List<String>> result = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        public List<List<String>> partition(String s) {
            l = s.length();
            chars = s.toCharArray();
//            DP,flag[i][j]=s[i-j]是否为回文子串
//            转移公式 ，F[i][j] = char[i]==char[j] && F[i+1][j-1]
//            由于F[i][j]依赖F[i+1][j-1],因此i从l-1到0逆向，j从i+1到l,默认是true来解决i==j的问题
            flag = new boolean[l][l];
            for (int i = 0; i < l; i++) {
                Arrays.fill(flag[i], true);
            }
            for (int i = l-1; i >= 0; i--) {
                for (int j = i + 1; j < l; j++) {
                    flag[i][j] = (chars[j] == chars[i]) && (flag[i + 1][j - 1]);
                }
            }
            dfs(s, 0);
            return result;
        }

        /**
         * 深度优先，可以理解为多叉树深度遍历，只将符合条件的输出到结果
         * @param s
         * @param i
         */
        public void dfs(String s, int i) {
            if (i == l) {
                result.add(new ArrayList<>(temp));
                return;
            }
            for (int j = i; j < l; j++) {
                if (flag[i][j]) {
                    temp.add(s.substring(i, j + 1));
                    dfs(s, j + 1);
                    temp.remove(temp.size() - 1);
                }
            }
        }

}
//leetcode submit region end(Prohibit modification and deletion)

}