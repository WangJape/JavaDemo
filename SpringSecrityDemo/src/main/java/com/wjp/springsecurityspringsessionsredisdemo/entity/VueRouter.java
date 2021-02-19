package com.wjp.springsecurityspringsessionsredisdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("vuerouter")
public class VueRouter implements Serializable {

    @TableId(value = "vid", type = IdType.AUTO)
    private Integer vid;

    @TableField("vuerouter_path")
    private String vuerouterPath;

    @TableField("`vuerouter_path_name`")
    private String vuerouterPathName;

    @TableField("vuerouter_component")
    private String vuerouterComponent;

    @TableField("elementui_menuicon")
    private String elementuiMenuicon;

    @TableField("vuerouter_orderscore")
    private Long vuerouterOrderscore;

    @TableField("vuerouter_createtime")
    private Date vuerouterCreatetime;

}
