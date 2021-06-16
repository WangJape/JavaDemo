package com.jape.LeetCode;

import java.util.Stack;

/**
 * 最大有效括号长度
 */
public class Algorithm32 {

    private static final char LEFT = '(';
    private static final char RIGHT = ')';

    public static void main(String[] args) {
        String bracketsStr = ")()())";

        System.err.println(getLongestValid(bracketsStr));

    }

    public static int getLongestValid(String brStr) {
        if (brStr == null || brStr.length() < 2) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        //栈底保存 当前最后一个没有被匹配到的')'的下标
        //其之上保存未匹配到的'('下标
        stack.push(-1);

        int maxLen = 0;
        int strLen = brStr.length();
        for (int i = 0; i < strLen; i++) {
            char curr = brStr.charAt(i);
            if (LEFT == curr) {
                stack.push(i);
            } else {
                //当前为')'
                stack.pop();
                if (stack.isEmpty()) {
                    //到达栈底也没匹配到')'，中断
                    stack.push(i);
                } else {
                    //除栈底外其他保存的都是'('的坐标，所以是匹配到了'('
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        int maxLen = 0;
        int strLen = s.length();
        for (int i = 0; i < strLen; i++) {
            char curr = s.charAt(i);
            if (LEFT == curr) {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }


}
