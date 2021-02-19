package com.jape.springlab.enmu;

/**
 * values() 返回枚举类中所有的值。
 * ordinal()方法可以找到每个枚举常量的索引，就像数组索引一样。
 * valueOf()方法返回指定字符串值的枚举常量。
 */
public enum EnumStatus {

    SUCCESS("0", "成功"),
    FAIL("1", "失败");




    private final String code;
    private final String name;

    private EnumStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String findNameByCode(String code) {
        for (EnumStatus myEnum : EnumStatus.values()) {
            if (myEnum.code.equals(code)) {
                return myEnum.name;
            }
        }
        return null;
    }

}
