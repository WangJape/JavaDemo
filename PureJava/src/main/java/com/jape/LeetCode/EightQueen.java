package com.jape.LeetCode;

import java.util.Arrays;

public class EightQueen {
    public static void main(String[] args) {
        EightQueen demo = new EightQueen();
        int[] arr = new int[8];

        demo.check(arr, 0);

    }

    private void check(int[] arr, int n){
        if(n == 8){
            System.err.println(Arrays.toString(arr));
            return;
        }

        for (int i = 0; i < 8; i++) {
            arr[n] = i;//把位置i赋给对应皇后
            if(judge(arr, n)){
                check(arr, n+1);
            }
        }

    }

    private boolean judge(int[] arr,int n){
        //判断位置是否与前面冲突
        for (int i = 0; i < n; i++) {

            if (arr[i] == arr[n] || Math.abs(n-i) == Math.abs(arr[n]-arr[i])){
                return false;
            }
        }
        return true;

    }



}
