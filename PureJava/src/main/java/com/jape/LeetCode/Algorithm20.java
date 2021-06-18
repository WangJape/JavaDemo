package com.jape.LeetCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ÓÐÐ§µÄÀ¨ºÅ
 *
 * @author Jape
 * @since 2020/12/9 16:48
 */
public class Algorithm20 {


    public static void main(String[] args) {
        String s = "{([[({{}})]])}";
        System.err.println(isValid2(s));

    }

    public boolean isValid(String s) {
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};

        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char brackets : chars) {
            if (stack.empty()) {
                stack.push(brackets);
            } else {
                Character peek = stack.peek();
                if (peek.equals(pairs.get(brackets))) {
                    stack.pop();
                } else {
                    stack.push(brackets);
                }
            }
        }
        return stack.empty();
    }

    public static boolean isValid2(String s) {
        System.err.println(s);
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replaceAll("\\(\\)", "");
            s = s.replaceAll("\\[]", "");
            s = s.replaceAll("\\{}", "");
            System.err.println(s);
        }
        return s.isEmpty();
    }

}
