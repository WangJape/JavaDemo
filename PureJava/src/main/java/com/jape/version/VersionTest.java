package com.jape.version;

public class VersionTest {
    public static void main(String[] args) {
        //jdk10 局部变量类型推断
        var a = 666.63;
        System.err.println(++a);

        //jdk15-文本块
        /*String template = """
                尊敬的客户，感谢您使用民生信用卡。
                我行现邀请您将尾号{0}卡片所属账户额度临时调整至{1}元，{2}月{3}日失效。
                如您接受此邀请，请于24小时内回复“LTY”申请，卡片账户状态需正常。祝您用卡愉快！
                """;*/

    }
}
