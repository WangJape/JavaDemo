package com.jape.LeetCode;

/**
 * 300. 最长递增子序列 难度中等
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 示例 1：
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * <p>
 * 示例 2：
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 示例 3：
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * <p>
 * 提示：
 * 1 <= nums.length <= 2500
 * -10<sup>4</sup> <= nums[i] <= 10<sup>4</sup>
 */
public class DP300 {
    public static void main(String[] args) {
        long start = System.nanoTime();
        System.err.println(lengthOfLIS2(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        long end = System.nanoTime();
        System.err.printf("耗时：%dns", end - start);
    }

    /**
     * 动态规划 时间复杂度：O(n^2)
     * 每个数值的最长序列长度，是1 加上 之前比它小的数值的长度
     */
    public static int lengthOfLIS(int[] nums) {
        var length = nums.length;
        int[] dp = new int[length];
        var curMax = 0;
        for (int i = 0; i < length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            curMax = Math.max(dp[i], curMax);
        }
        return curMax;
    }

    /**
     * 贪心算法 + 二分查找
     */
    public static int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] d = new int[n + 1];
        d[1] = nums[0];
        int len = 1;
        for (int i = 1; i < n; ++i) {
            if (nums[i] > d[len]) {
                d[++len] = nums[i];
            } else {
                int l = 1, r = len, pos = 0; // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (d[mid] < nums[i]) {
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[pos + 1] = nums[i];
            }
        }
        return len;

        /*作者：LeetCode-Solution
        链接：https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
        来源：力扣（LeetCode）
        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
    }
}
