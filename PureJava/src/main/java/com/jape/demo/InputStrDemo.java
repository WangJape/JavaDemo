package com.jape.demo;

import java.util.Scanner;

/**
 * @Description:
 * @Auther: 王建朋
 * @Date: 2020/9/11 16:00
 */
public class InputStrDemo {

    public static void main(String[] args) {
        //创建输入对象

        Scanner sc = new Scanner(System.in);    //获取用户输入的字符串

        String str = null;

        System.out.println("输入字符串:");

        str = sc.nextLine();

        //输出输入的内容
        System.out.println("输入的字符为:"+str);
    }

}
