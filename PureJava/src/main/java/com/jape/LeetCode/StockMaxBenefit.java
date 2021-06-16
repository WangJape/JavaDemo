package com.jape.LeetCode;

/**
 * 股票最大收益
 * 如果一个人在知道了股票每天的股价以后，对该股票进行投资，问什么时候买入和卖出能取得最大收益。
 * 数学模型，给定一个整数数组a[0],a[1],a[2]...a[n]，每一个元素a[i]可以和它左边元素做差，求这个数组中最大的差值。
 */
public class StockMaxBenefit {

    public static void main(String[] args) {

        //22
        int[] stockPrices = new int[]{10, 15, 23, 20, 16, 29, 32};
        //9
        //stockPrices = new int[]{10, 6, 15, 7, 11, 3};
        System.err.println(getMaxBenefit(stockPrices));
    }


    /**
     * 一直找最小值，一直计算最小值之后的当前收益，比较并暂存最大收益
     */
    private static int getMaxBenefit(int[] stockPrices) {
        if (stockPrices == null || stockPrices.length == 1) {
            return 0;
        }

        int len = stockPrices.length;
        int min = stockPrices[0];
        int maxBft = 0;
        for (int i = 1; i < len; i++) {
            int curr = stockPrices[i];
            if (min > curr) {
                min = curr;
            } else {
                int currBft = curr - min;
                if (currBft > maxBft) {
                    maxBft = currBft;
                }
            }
        }
        return maxBft;
    }


}
