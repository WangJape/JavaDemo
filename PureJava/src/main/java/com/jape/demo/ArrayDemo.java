package com.jape.demo;

import java.util.Arrays;

/**
 * 数组Demo
 *
 * @author Jape
 * @since 2020/12/8 9:54
 */
public class ArrayDemo {

    public static void main(String[] args) {
        Integer[] is = new Integer[]{1, 5, 6, 8, 9};
        Integer[] is2 = new Integer[5];

        //浅拷贝，引用拷贝
        System.arraycopy(is, 0, is2, 0, 3);
        System.out.println(Arrays.toString(is2));

        //内部也为 System.arraycopy
        Integer[] is3 = Arrays.copyOf(is, 3);
        System.out.println(Arrays.toString(is3));
        Integer[] is4 = Arrays.copyOfRange(is, 1, 2);
        System.out.println(Arrays.toString(is4));
    }
}
