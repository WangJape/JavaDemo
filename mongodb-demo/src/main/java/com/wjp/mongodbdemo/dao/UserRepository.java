package com.wjp.mongodbdemo.dao;

import com.wjp.mongodbdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String name);

    List<User> findByNameIsLike(String name);

}
