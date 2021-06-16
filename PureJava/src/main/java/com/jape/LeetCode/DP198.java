package com.jape.LeetCode;

/**
 * 动态规划，打家劫舍
 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1,2,3,1]
 * 输出：4
 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 示例 2：
 * <p>
 * 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 *      偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 *  
 * <p>
 * 提示：
 * <p>
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。必须间隔一个，且拿取之和东西最多
 *
 * @author Jape
 * @since 2020/12/11 17:44
 */
public class DP198 {
    public static void main(String[] args) {
        int[] house = new int[]{6, 3, 5, 1, 2, 8};//19
        System.err.println(optimalSolution(house));
    }

    private static int optimalSolution(int[] house) {
        if (house == null || house.length == 0) {
            return 0;
        }
        if (house.length == 1) {
            return house[0];
        }
        int pre1Sum = house[0];
        int curr = house[1];
        if (house.length == 2) {
            return Math.max(pre1Sum, curr);
        }
        int temp = pre1Sum;
        pre1Sum = Math.max(pre1Sum, curr);

        for (int i = 2; i < house.length; i++) {
            curr = house[i];
            int pre2Sum = temp;
            temp = pre1Sum;
            pre1Sum = Math.max(pre1Sum, pre2Sum + curr);
        }
        return pre1Sum;
    }
}
