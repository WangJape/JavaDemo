package com.jape.demo;

import java.text.MessageFormat;

public class StringDemo {
    public static void main(String[] args) {

        int num = 10;
        String format = String.format("%010d", num);
        System.err.println(format);

        String template = "尊敬的客户，感谢您使用民生信用卡。" +
                "我行现邀请您将尾号{0}卡片所属账户额度临时调整至{1}元，{2}月{3}日失效。" +
                "如您接受此邀请，请于24小时内回复“LTY”申请，卡片账户状态需正常。祝您用卡愉快！";

        //方法一：jdk自带MessageFormat
        Object[] varargs = new String[]{"123456789", "1000000", "12", "11", "3663"};
        String message = MessageFormat.format(template, varargs);
        System.err.println(message);

        //方法二
        template = " 尊敬的客户，您尾号%s卡片所属账户额度已临时调整至%s元，%s月%s日失效(有效期不含失效当日)。祝您用卡愉快！";
        message = String.format(template, varargs);
        System.err.println(message);

        //正则
        String girl = "girl24";
        System.err.println(girl.matches("^girl(1[8-9]|2[0-4])$"));



    }
}
