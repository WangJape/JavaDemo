package com.jape.LeetCode;

/**
 * 动态规划 53 最大子序和 简单
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 给定一个数组 array[1, 4, -5, 9, 8, 3, -6]，在这个数字中有多个子数组，子数组和最大的应该是：[9, 8, 3]，输出21，
 * 再比如数组为[1, -2, 3, 10, -4, 7, 2, -5]，和最大的子数组为[3, 10, -4, 7, 2]，输出18。
 * <p>
 * 示例:
 * <p>
 * 输入: [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 */
public class DP53 {

    public static void main(String[] args) {
        int[] array = {3, 3, 3, -10, 4, 4, 4};
        System.err.println(getMaxSumOfArray(array));
    }

    public static int getMaxSumOfArray(int[] array) {
        int tempSum = array[0];
        int currMax = array[0];

        for (int i = 1; i < array.length; i++) {
            int currNum = array[i];
            tempSum = Math.max(tempSum + currNum, currNum);
            currMax = Math.max(tempSum, currMax);
        }

        return currMax;
    }

}


