package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("role")
public class Role implements Serializable {

    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    @TableField("role_code")
    private String roleCode;

    @TableField("role_name")
    private String roleName;

    @TableField("role_createtime")
    private Date roleCreatetime;

}
