package com.jape.demo;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[]{31, 5, 7, 15, 64, 36, 42, 51, 6, 7, 32, 4};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, 0, arr.length - 1);
        System.err.println(Arrays.toString(arr));

    }

    private void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotLoc = partition(arr, low, high);
            sort(arr, low, pivotLoc - 1);
            sort(arr, pivotLoc + 1, high);
        }
    }

    /**
     * 一次排序-分区
     *
     * @param arr  数组
     * @param low  低位指针
     * @param high 高位指针
     * @return 中间值位置
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

}
