package com.jape.Interview;

public class StringTest {

    public static void main(String[] args) throws InterruptedException {


        String s1 = "AB";
        String s2 = new String("AB");
        System.out.println(s1 == s2);

        String s3 = "A";
        String s4 = "B";
        String s5 = "A" + "B";
        System.out.println(s1 == s5);

        String s6 = s3 + s4;
        System.out.println(s1 == s6);

        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());

        Thread.sleep(60 * 60 * 1000);

    }
}
