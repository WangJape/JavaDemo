package com.jape.Interview;

/**
 * n = 5x + 2y + z
 * n已知，不使用循环求解xyz的值有多少个
 * 例如 n = 5;
 * 有4组解：(0, 0, 5)、(0, 1, 3)、(0, 2, 1)、(1, 0, 0).
 */
public class UnrollLoop {

    public static void main(String[] args) {

        int n = 5;

        int x = n / 5; //x最大值
        int y = n / 2;
        int z = n;
        int count = 0;

        /**
         * 三层for
         */
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                for (int k = 0; k <= z; k++) {
                    if (n == 5*i + 2*j + k) count++;
                }
            }
        }
        System.err.println(count);

        /**
         * 两层for
         */
        count = 0;
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= (n - 5*i) / 2; j++) {
                count++;
            }
        }
        System.err.println(count);

        /**
         * 一层for
         */
        count = 0;
        for(int i = 0; i <= x; i++){
            count += (n - 5 * i) / 2 + 1;
        }
        System.err.println(count);

        /**
         * 不用for
         * 使用等差数列求和，转换上边for()
         * n/5
         * ∑((n-5*i) / 2 + 1)
         * i=0
         * 等差数列求和公式
         * n(a1+an)
         * --------
         *    2
         *
         *   n            n
         * (--- + 1 + 1)(--- + 1)
         *   2            5
         * ------------------------
         *           2
         */
        count = (n/2+1+1) *(n/5+1) / 2;
        System.err.println(count);
    }
}
