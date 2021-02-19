package com.jape.redisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 账号昵称
     */
    private String nickname;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 账户头像URL路径
     */
    private String avatar;

    /**
     * 用户账户创建时间
     */
    private LocalDateTime createDateTime;


}
