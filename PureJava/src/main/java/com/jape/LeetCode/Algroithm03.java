package com.jape.LeetCode;

import java.util.HashMap;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Algroithm03 {

    public static void main(String[] args) {

        Algroithm03 algroithm = new Algroithm03();

        String s1 = "abcabcbb";
        String s2 = "bbbbb";
        String s3 = "";

        System.err.println(algroithm.lengthOfLongestSubstring1(s1));
    }

    public int lengthOfLongestSubstring(String s) {
        StringBuilder window = new StringBuilder();
        int longest = 0;

        for (int i = 0; i < s.length(); i++) {
            //System.err.println(window.length() + "->" + window.toString());
            for (int j = 0; j < window.length(); j++) {
                if(window.charAt(j) == s.charAt(i)){
                    window = window.delete(0, j+1);
                    break;
                }
            }
            window.append(s.charAt(i));
            if(window.length() > longest){
                longest = window.length();
            }
        }

        return longest;
    }

    /**
     * 进一步优化为仅需要 n 个步骤。我们可以定义字符到索引的映射，而不是使用集合来判断一个字符是否存在。 当我们找到重复的字符时，我们可以立即跳过该窗口。
     *
     * 也就是说，如果 s[j]s[j] 在 [i, j)[i,j) 范围内有与 j'j
     * ′
     *   重复的字符，我们不需要逐渐增加 ii 。 我们可以直接跳过 [i，j'][i，j
     * ′
     *  ] 范围内的所有元素，并将 ii 变为 j' + 1j
     * ′
     *  +1。
     *
     * 作者：LeetCode
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/solution/wu-zhong-fu-zi-fu-de-zui-chang-zi-chuan-by-leetcod/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        HashMap<Character, Integer> window = new HashMap();
        int longest = 0;

        for (int i = 0, j = 0; j < s.length(); j++) {
            if(window.containsKey(s.charAt(j))){//如i果存在这个字符，就取出它在字符串的位置
                i = Math.max(window.get(s.charAt(j)), i);
            }
            if(j-i+1 > longest){
                longest = j-i+1;
            }
            window.put(s.charAt(j), j+1);
        }

        return longest;
    }



}



























