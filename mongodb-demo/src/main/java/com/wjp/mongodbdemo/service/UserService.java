package com.wjp.mongodbdemo.service;

import com.wjp.mongodbdemo.dao.UserRepository;
import com.wjp.mongodbdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void updatePraiseNumByName(String name){
        //精确查询条件
        Query query = Query.query(Criteria.where("name").is(name));
                     //.addCriteria(Criteria.where("sex").is("女"));
        //更新条件
        Update update = new Update();
        update.inc("praise");

        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void updatePraiseNumByNameIsLike(String name){
        //模糊查询条件（正则表达式）
        Query query = Query.query(Criteria.where("name").regex(".*?"+name+".*"));
        //更新条件
        Update update = new Update();
        update.inc("praise");

        mongoTemplate.updateMulti(query, update, User.class);
    }



}
