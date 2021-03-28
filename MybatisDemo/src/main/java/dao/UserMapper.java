package dao;

import entity.User;

import java.util.List;

public interface UserMapper {

    User getById(Integer id);

    List<User> getList();

    int insertOne(User user);

    int batchInsert(List<User> list);
}
