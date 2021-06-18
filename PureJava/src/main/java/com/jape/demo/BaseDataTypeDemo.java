package com.jape.demo;

public class BaseDataTypeDemo {

    public static void main(String[] args) {
        Integer a = 128;
        Integer b = 128;
        System.err.println(a == b);//Integer缓存（常量池）
        System.err.println(a.equals(b));

        int c = 128;
        int d = 128;
        System.err.println(c == a);//自动拆包
        System.err.println(c == d);

        int e = 1;
        long f = 1;
        //e = e + f;
        System.err.println(e);

        //1422206881与1000均为int型，计算不会转为long，溢出，所以只要在任意一个数值上加上L，计算时便会自动转为long
        long a3 = 1422206881 * 1000;
        long a4 = 1422206881L * 1000;
        System.err.println(a3);
        System.err.println(a4);

        int shiftI = -2;
        float shiftF = 45.0f;
        long shiftL = 45;
        char shiftC = 'u';
        System.out.println(Integer.toBinaryString(shiftI));
        System.out.println(shiftI = shiftI>>>3);
        System.out.println(Integer.toBinaryString(shiftI));



    }

}
