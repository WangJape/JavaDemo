package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("account_role")
public class AccountRole implements Serializable {

    @TableId(value = "arid", type = IdType.AUTO)
    private Integer arid;

    @TableField("user_account")
    private String userAccount;

    @TableField("role_code")
    private String roleCode;

}
