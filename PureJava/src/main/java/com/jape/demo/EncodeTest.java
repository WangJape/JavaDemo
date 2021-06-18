package com.jape.demo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class EncodeTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String sysCS = System.getProperty("file.encoding");
        System.out.println(sysCS);

        String s = "王建鹏123";

        String us = new String(s.getBytes(), StandardCharsets.UTF_8);
        String gs = new String(s.getBytes(), "gbk");
        System.out.println(us);
        System.out.println(gs);


        String u_gs = new String(us.getBytes(), "gbk");
        String g_us = new String(gs.getBytes("gbk"), StandardCharsets.UTF_8);
        System.out.println(u_gs);
        System.out.println(g_us);


    }
}
