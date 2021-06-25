package com.jape.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jape.mybatisplusdemo.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Jape
 * @since 2021-06-25
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义sql的分页
     */
    @Select("select * from t_user ")
    List<User> selectByPage(Page<User> page);

}
