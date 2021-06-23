package com.jape.elasticsearchdemo.service;

import com.jape.elasticsearchdemo.dao.UserDao;
import com.jape.elasticsearchdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private UserDao userDao;

    public Iterable<User> getList() {
        return userDao.findAll();
    }

    public Page<User> getPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userDao.findAll(pageRequest);
    }

}
