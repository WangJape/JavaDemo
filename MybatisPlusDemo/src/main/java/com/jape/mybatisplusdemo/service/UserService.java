package com.jape.mybatisplusdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jape.mybatisplusdemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Jape
 * @since 2021-06-25
 */
public interface UserService extends IService<User> {

    /**
     * 自定义sql的分页
     */
    List<User> selectByPage(Page<User> page);

}
