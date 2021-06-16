package com.jape.LeetCode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 56. 合并区间
 * 给出一个区间的集合，请合并所有重叠的区间。
 * <p>
 * 示例 1:
 * 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 示例 2:
 * 输入: intervals = [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * 注意：输入类型已于2019年4月15日更改。 请重置默认代码定义以获取新方法签名。
 * <p>
 * 提示：
 * intervals[i][0] <= intervals[i][1]
 */
public class Merge {
    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1, 3}, {0, 6}, {8, 10}, {15, 18}};
        int[][] merge = merge(intervals);
        System.err.println(Arrays.deepToString(merge));
    }

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        int[][] merge = new int[intervals.length][2];
        int mergePoint = 0;
        boolean initFlag = true;

        for (int i = 0; i < intervals.length; i++) {
            int[] interval = intervals[i];
            int[] mergeInterval = merge[mergePoint];
            if (initFlag) {
                mergeInterval[0] = interval[0];
                mergeInterval[1] = interval[1];
                initFlag = false;
                continue;
            }
            if (mergeInterval[0] == interval[0] || mergeInterval[1] >= interval[0]) {
                if (mergeInterval[1] < interval[1]) {
                    merge[mergePoint][1] = interval[1];
                }
            } else {
                mergePoint++;
                initFlag = true;
                i--;
            }
        }
        return Arrays.copyOfRange(merge, 0, mergePoint + 1);
    }

}
























