package com.jape.LeetCode;

/**
 * 01背包  动态规划
 * <p>
 * 用背包装物品，背包容量10，
 * 物品  价值  大小
 * 1     2     2
 * 2     4     3
 * 3     3     5
 * 4     7     5
 * 求能拿到的最多价值的物品
 */
public class Package01 {

    static int[] price = {0, 2, 4, 3, 7};
    static int[] size = {0, 2, 3, 5, 5};
    static int[][] dp = new int[5][11];//存价格

    public static void main(String[] args) {
        int goodsNum = 4;
        int capacity = 10;
        int i = maxPrice(goodsNum, capacity);
        System.err.println(i);
    }

    /**
     * capacity 0 1 2 3 4 5 6 7 8  9  10
     * goods
     * 0        0 0 0 0 0 0 0 0 0  0  0
     * 1        0 0 2 2 2 2 2 2 2  2  2
     * 2        0 0 2 4 4 6 6 6 6  6  6
     * 3        0 0 2 4 4 6 6 6 7  7  9
     * 4        0 0 2 4 4 7 7 7 11 11 13
     * <p>
     * ................不够装了，就是前一个结果
     * max(Num,cap) = {  max(Num-1,cap),max(Num-1,cap-size[Num])+price[Num]  }
     * ................够装，用上一个最晚符合这种容量的填充结果 填充当前
     */
    public static int maxPrice(int goodsNum, int capacity) {

        // 初始化
        for (int i = 0; i < goodsNum; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < capacity; i++) {
            dp[0][i] = 0;
        }

        // 开始填表
        for (int goodsNo = 1; goodsNo <= goodsNum; goodsNo++) {
            for (int surplus = 1; surplus <= capacity; surplus++) {
                if (size[goodsNo] > surplus) {
                    // 当前余量装不进去
                    dp[goodsNo][surplus] = dp[goodsNo - 1][surplus];
                } else {
                    // 当前余量足够
                    int pre = dp[goodsNo - 1][surplus];
                    int currTake = dp[goodsNo - 1][surplus - size[goodsNo]] + price[goodsNo];
                    if (pre > currTake) {
                        // 不装该珠宝，最优价值更大
                        dp[goodsNo][surplus] = pre;
                    } else {
                        dp[goodsNo][surplus] = currTake;
                    }
                }
            }
        }
        return dp[goodsNum][capacity];
    }


}
