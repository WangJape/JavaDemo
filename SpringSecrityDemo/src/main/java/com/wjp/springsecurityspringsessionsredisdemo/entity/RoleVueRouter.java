package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("role_vuerouter")
public class RoleVueRouter implements Serializable {

    @TableId(value = "rvid", type = IdType.AUTO)
    private Integer rvid;

    @TableField("role_code")
    private String roleCode;

    @TableField("vuerouter_path")
    private String vuerouterPath;

}
