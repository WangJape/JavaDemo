package com.jape.esjobdemo.mapper;

import com.jape.esjobdemo.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(User record);

    int batchInsert(List<User> userList);

    int insertSelective(User record);

    @Select("select uuid, name, age from t_user")
    List<User> selectAll();

    @Select("select uuid, name, age from t_user limit #{offset},#{size}")
    List<User> selectByPage(int offset, int size);

    List<User> selectPageByPreId(String preId, int pageSize);

    User selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}