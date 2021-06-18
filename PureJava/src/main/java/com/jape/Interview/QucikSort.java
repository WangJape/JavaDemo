package com.jape.Interview;

import java.util.Arrays;
import java.util.Random;

/**
 * @Description: 手写快排
 * @Auther: 王建朋
 * @Date: 2020/9/29 18:11
 * 需设置jvm参数: -Xms4096m -Xmx4096m
 */
public class QucikSort {

    public static void main(String[] args) {

        QucikSort sort = new QucikSort();
        int[] nums = sort.generateRandomNums(100000000);

        long startTime = System.currentTimeMillis();
        int[] numsCopy1 = Arrays.copyOf(nums, nums.length);
        long endTime = System.currentTimeMillis();
        System.err.println("复制数组用时【"+ (endTime-startTime) +"】");

        startTime = System.currentTimeMillis();
        sort.doSort(numsCopy1, 0, nums.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println("排序用时[" + (endTime - startTime) + "]ms");//100000000-用时[17056]ms


        startTime = System.currentTimeMillis();
        int[] numsCopy2 = new int[nums.length];
        System.arraycopy(nums, 0, numsCopy2, 0, nums.length);
        endTime = System.currentTimeMillis();
        System.err.println("复制数组用时【"+ (endTime-startTime) +"】");

        startTime = System.currentTimeMillis();
        sort.doSort2(numsCopy2, 0, nums.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println("排序用时[" + (endTime - startTime) + "]ms");//100000000-用时[17187]ms

    }


    /**
     * 递归排序的一次排序
     *
     * @param nums 排序数组，子数组
     * @param head 头指针，头索引
     * @param tail 尾指针，尾索引
     */
    private void doSort(int[] nums, int head, int tail) {

        if (head >= tail) {
            return;
        }
        int copyHead = head;
        int copyTail = tail;

        //中间值，基准值，哨兵，枢轴值
        int sentinelValue = nums[head];
        int flag = 1;//0-head右移，1-tail左移

        while (head != tail) {
            if (flag == 1) {
                if (nums[tail] < sentinelValue) {
                    nums[head] = nums[tail];
                    head++;//找到比中间值小的，以后头指针要开始动了
                    flag = 0;
                } else {
                    tail--;//没找到，继续向左找
                }
            } else {
                if (nums[head] > sentinelValue) {
                    nums[tail] = nums[head];
                    tail--;//找到比中间值大的，以后尾指针要开始动了
                    flag = 1;
                } else {
                    head++;//没找到，继续向右找
                }
            }
        }
        nums[head] = sentinelValue;
        //System.err.println(Arrays.toString(nums));

        doSort(nums, copyHead, head - 1);
        doSort(nums, tail + 1, copyTail);

    }

    /**
     * 另一种快排方式，交换式
     *
     * @param nums
     * @param head
     * @param tail
     */
    private void doSort2(int[] nums, int head, int tail) {

        //如果head等于tail，即数组只有一个元素，直接返回
        if (head >= tail) {
            return;
        }
        int copyHead = head;
        int copyTail = tail;
        //数组中比中间值小的放在左边，比中间值大的放在右边
        int sentinelValue = nums[head];

        while (head < tail) {
            //tail向左移，直到遇到比key小的值
            while (nums[tail] >= sentinelValue && head < tail) {
                tail--;
            }
            //head向右移，直到遇到比key大的值
            while (nums[head] <= sentinelValue && head < tail) {
                head++;
            }
            //head和tail指向的元素交换
            if (head < tail) {
                int temp = nums[head];
                nums[head] = nums[tail];
                nums[tail] = temp;
            }
        }
        nums[copyHead] = nums[head];
        nums[head] = sentinelValue;

        //System.err.println(Arrays.toString(nums));

        doSort2(nums, copyHead, head - 1);
        doSort2(nums, tail + 1, copyTail);


    }

    private int[] generateRandomNums(int amount) {
        long startTime = System.currentTimeMillis();

        Random random = new Random(amount);
        int[] nums = new int[amount];
        for (int i = 0; i < amount; i++) {
            nums[i] = random.nextInt(amount);
        }
        //System.out.println(Arrays.toString(nums));
        long endTime = System.currentTimeMillis();
        System.err.println("生成用时【"+ (endTime-startTime) +"】");
        return nums;
    }

}
