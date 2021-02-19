package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("role_permission")
public class RolePermission implements Serializable {

    @TableId(value = "rpid", type = IdType.AUTO)
    private Integer rpid;

    @TableField("role_code")
    private String roleCode;

    @TableField("permission_code")
    private String permissionCode;
}
