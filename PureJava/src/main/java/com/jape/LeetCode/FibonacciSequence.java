package com.jape.LeetCode;

/**
 * 斐波那契数列
 * Demo{1,2,3,5,8}
 *
 * @author Jape
 * @since 2020/12/9 15:03
 */
public class FibonacciSequence {

    public static void main(String[] args) {
        System.err.println(nthValue(5));
    }

    private static int nthValue(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int currPre2 = 1;
        int currPre1 = 2;
        int curr = 0;
        for (int i = 3; i <= n; i++) {
            curr = currPre2 + currPre1;
            currPre2 = currPre1;
            currPre1 = curr;
        }
        return curr;

    }
}
