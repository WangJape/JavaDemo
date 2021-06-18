package com.jape.LeetCode;

/**
 * 动态规划5
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * <p>
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 * <p>
 * 示例 3：
 * 输入：s = "a"
 * 输出："a"
 * <p>
 * 示例 4：
 * 输入：s = "ac"
 * 输出："a"
 * <p>
 * 提示：
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母（大写和/或小写）组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jape
 * @since 2021/3/29 12:32
 */
public class DP5 {

    public static void main(String[] args) {
        String s = "cbaba";
        String longest = longestPalindrome(s);
        System.err.println(longest);
    }

    /**
     * 动态规划,依次扫描每个字母与后续字母组合是否是回文，如果后续字母的（i-1,j-1）也是回文，并且两边字母形同，就是回文
     * 时间复杂度O(n^2)
     *
     * @param s 字符串
     * @return
     */
    public static String longestPalindrome(String s) {

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int l = 0; l < n; ++l) {
            for (int i = 0; i + l < n; ++i) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        printArray(dp);
        return ans;
    }

    private static void printArray(boolean[][] array) {
        for (boolean[] row : array) {
            for (boolean col : row)
                System.err.print(col + "\t");
            System.err.print("\n");
        }
    }

    /**
     * 中心扩散算法，扫描每个字母，同时从此字母向外扩散，看是否回文（两边字母是否相同）
     * 时间复杂度O(n^2)
     */


}
