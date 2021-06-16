package com.jape.LeetCode;

/**
 * 14. 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 * <p>
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * <p>
 * <p>
 * 提示：
 * <p>
 * 0 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
public class Algorithm14 {

    public static void main(String[] args) {
        String[] strs = new String[]{"flower", "flow", "flight"};
        String s = longestCommonPrefix(strs);
        System.err.println(s);
    }

    /**
     * 分治，类似快速排序
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length <= 0) {
            return "";
        }
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    /**
     * 递归
     */
    public static String longestCommonPrefix(String[] strs, int start, int end) {
        if (start == end) {
            return strs[start];
        }
        int middle = (start + end) / 2;
        String leftStr = longestCommonPrefix(strs, start, middle);
        String rightStr = longestCommonPrefix(strs, middle + 1, end);
        String commPrefix = getCommPrefix(leftStr, rightStr);
        return commPrefix;
    }

    /**
     * 找出两个字符串的公共
     */
    public static String getCommPrefix(String str1, String str2) {
        StringBuilder commPre = new StringBuilder();
        int str1Len = str1.length();
        int str2Len = str2.length();
        int minLen = Math.min(str1Len, str2Len);
        for (int i = 0; i < minLen; i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (c1 == c2) {
                commPre.append(c1);
            } else {
                break;
            }
        }
        return commPre.toString();
    }
}
