package com.jape.multipleds.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TblBeAccInfoCopy {
    /**
     * 银行账户标识
     */
    private String bankAccId;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 客户编号
     */
    private String custId;

    /**
     * 银行账号
     */
    private String bankAcc;

    /**
     * 账户名称
     */
    private String accName;

    /**
     * 内部账户
     */
    private String innerAcc;

    /**
     * 账户类型
     */
    private String accType;

    /**
     * 币种代码
     */
    private String currCode;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 省份代码
     */
    private String provinceCode;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 开户行号
     */
    private String openBranchNo;

    /**
     * 开户行名
     */
    private String openBranchName;

    /**
     * 开户日期
     */
    private Date openBranchDate;

    /**
     * 账户用途
     */
    private String accPurpose;

    /**
     * 账户状态
     */
    private String accStatus;

    /**
     * 管理方式
     */
    private String mgnMode;

    /**
     * 留存额度
     */
    private BigDecimal holdLimit;

    /**
     * 最后业务日
     */
    private Date lastBizDt;

    /**
     * 最后业务时间
     */
    private Date lastBizTm;

    /**
     * 客户名称
     */
    private String custName;

}