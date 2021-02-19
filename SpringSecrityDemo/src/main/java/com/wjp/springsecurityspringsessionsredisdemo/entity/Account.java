package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("account")
public class Account implements Serializable {

    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    @TableField("user_account")
    private String userAccount;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_nickname")
    private String userNickname;

    @TableField("user_createtime")
    private Date userCreatetime;

}
