package com.jape.beanpostdemo.enums;

import com.jape.beanpostdemo.dtos.*;

/**
 * 业务枚举
 */
public enum BusinessEnum {

    LOGIN("0001", LoginReqBody.class, "登录"),
    LOGOUT("0002", LogoutReqBody.class, "注销");


    private final String code;
    private final Class<? extends ReqBody> reqBodyType;
    private final String desc;

    BusinessEnum(String code, Class<? extends ReqBody> reqBodyType, String desc) {
        this.code = code;
        this.reqBodyType = reqBodyType;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public Class<? extends ReqBody> getReqBodyType() {
        return reqBodyType;
    }

    public String getDesc() {
        return desc;
    }
}