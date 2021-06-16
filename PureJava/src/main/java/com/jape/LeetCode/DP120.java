package com.jape.LeetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 120. 三角形最小路径和 难度中等
 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
 * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
 * <p>
 * 示例 1：
 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * 输出：11
 * 解释：如下面简图所示：
 * 2
 * 3 4
 * 6 5 7
 * 4 1 8 3
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 * <p>
 * 示例 2：
 * 输入：triangle = [[-10]]
 * 输出：-10
 * <p>
 * 提示：<br>
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == i+1
 * -10<sup>4</sup> <= triangle[i][j] <= 10<sup>4</sup>
 */
public class DP120 {

    public static void main(String[] args) {

        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(List.of(2));
        triangle.add(List.of(3, 4));
        triangle.add(List.of(6, 5, 7));
        triangle.add(List.of(4, 1, 8, 3));
        long start = System.nanoTime();
        System.err.println(minimumTotal2(triangle));
        long end = System.nanoTime();
        System.err.printf("耗时：%dns", end - start);

    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        var row = triangle.size();
        if (row == 0) {
            return 0;
        }
        //从下往上算，依次计算各个位置的最小和，不用考虑边界（每行第一个和最后一个）
        List<List<Integer>> dp = new ArrayList<>();
        //初始化最后一行
        dp.add(triangle.get(row - 1));

        for (int i = 1; i < row; i++) {
            var scanRowIndex = row - i - 1;
            var scanRowNum = scanRowIndex + 1;//扫描行数字个数
            var scanRow = triangle.get(scanRowIndex);
            var dpPreRow = dp.get(i - 1);
            List<Integer> dpCurRow = new ArrayList<>();
            for (int j = 0; j < scanRowNum; j++) {
                var curNum = scanRow.get(j);
                var dpPreMin = Math.min(dpPreRow.get(j), dpPreRow.get(j + 1));
                dpCurRow.add(curNum + dpPreMin);
            }
            dp.add(dpCurRow);
        }

        return dp.get(row - 1).get(0);
    }

    public static int minimumTotal2(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[n][n];
        f[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; ++i) {
            f[i][0] = f[i - 1][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; ++j) {
                f[i][j] = Math.min(f[i - 1][j - 1], f[i - 1][j]) + triangle.get(i).get(j);
            }
            f[i][i] = f[i - 1][i - 1] + triangle.get(i).get(i);
        }
        int minTotal = f[n - 1][0];
        for (int i = 1; i < n; ++i) {
            minTotal = Math.min(minTotal, f[n - 1][i]);
        }
        return minTotal;
    }


}
