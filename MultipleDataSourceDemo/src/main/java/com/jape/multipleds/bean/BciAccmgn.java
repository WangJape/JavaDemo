package com.jape.multipleds.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BciAccmgn {
    /**
     * BANKACCID  银行账户标识
     */
    private Integer bankaccid;

    /**
     * BANKCODE  银行代码
     */

    private String bankcode;


    private String custid;

    /**
     * BANKACC  银行账号
     */

    private String bankacc;

    /**
     * ACCNAME  账户名称
     */

    private String accname;

    /**
     * INNERACC  内部账号
     */

    private String inneracc;

    /**
     * ACCTYPE  账户类型
     */

    private Integer acctype;

    /**
     * CURRCODE  货币代码
     */

    private Integer currcode;

    /**
     * BALANCE  账户余额
     */

    private BigDecimal balance;

    /**
     * USABLEBAL  可用余额
     */

    private BigDecimal usablebal;

    /**
     * AREACODE  账户区域
     */

    private Integer areacode;

    /**
     * OPENBRC  开户行号
     */

    private String openbrc;

    /**
     * BRCNAME  开户行名
     */

    private String brcname;

    /**
     * OPENDATE  开户日期
     */

    private Date opendate;

    /**
     * PURPOSE  账户用途
     */

    private Integer purpose;

    /**
     * RECSTATUS  账户状态
     */

    private Integer recstatus;

    /**
     * MGNMODE  管理方式
     */

    private Integer mgnmode;

    /**
     * MGNCYCLE  刷新周期
     */

    private Integer mgncycle;

    /**
     * HOLDLIMIT  留存额度
     */

    private BigDecimal holdlimit;

    /**
     * QUERYAUTH  查询授权
     */

    private Integer queryauth;

    /**
     * DAYTIMES  每日次数
     */

    private Integer daytimes;

    /**
     * TMWINDOW  时间窗口
     */

    private Integer tmwindow;

    /**
     * LASTBIZDT  最后业务日
     */

    private String lastbizdt;

    /**
     * LASTBIZTM  最后业务时间
     */

    private String lastbiztm;

    /**
     * MGNDATE
     */

    private Date mgndate;

}
