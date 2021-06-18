package com.jape.Interview;

import java.util.Arrays;

/**
 * 3、随机给定两个超大整数，实现计算两数之乘积。
 * 如“111111111111111”和“222222222222222”，输出乘积“24691358024691308641975308642”。
 * 要求不使用Java中的BigInteger等类
 */
public class BigNumMultiply {

    public static void main(String[] args) {
        BigNumMultiply multiply = new BigNumMultiply();

        String num1 = "111111111111111";
        String num2 = "222222222222222";
        /*String num1 = "99";
        String num2 = "99";*/

        System.err.println(multiply.multiply(num1, num2));

    }

    private String multiply(String num1, String num2) {

        int len1 = num1.length();
        int len2 = num2.length();
        int resLen = len1 + len2;

        int[] result = new int[resLen];
        int[][] interRes = new int[len2][resLen];

        //生成中间结果
        for (int i = 0; i < len2; i++) {
            int beMulSub = num2.charAt(i) - 48;//得到num2（乘数）要乘的一位数
            int carryFlagSub = 0; //子运算进位
            int j = 0;
            for (; j < len1; j++) {
                int mulSub = num1.charAt(j) - 48;//得到num1（被乘数）要乘的一位数
                int interResSub = mulSub * beMulSub + carryFlagSub;
                carryFlagSub = interResSub / 10;
                interRes[i][i + j] = interResSub % 10;
            }
            interRes[i][i + j] = carryFlagSub;
            System.err.println(Arrays.toString(interRes[i]));
        }

        //计算
        int carryFlag = 0; //运算进位
        for (int i = 0; i < resLen; i++) {
            int resSub = 0;
            int j = 0;
            if (i > len2) { //去掉上三角计算
                j = i - len2;
            }
            for (; j < len2; j++) {
                if (j > i) { //去掉下三角计算
                    break;
                }
                resSub += interRes[j][i];
            }
            resSub += carryFlag;
            carryFlag = resSub / 10;
            resSub %= 10;
            result[i] = resSub;
        }
        System.err.println(Arrays.toString(result));

        return arrToStr(result);
    }

    /**
     * 数组转换成字符串（逆序）
     */
    private String arrToStr(int[] result) {
        StringBuffer str = new StringBuffer();
        int i = result.length - 1;
        while (result[i] == 0) {
            i--;
        }
        for (; i >= 0; i--) {
            str.append(result[i]);
            if (i != 0 && i % 3 == 0) {
                str.append(",");
            }
        }
        return str.toString();
    }

}
