package com.jape.elasticsearchdemo.dao;

import com.jape.elasticsearchdemo.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * doc的curd操作dao
 */
@Repository
public interface UserDao extends ElasticsearchRepository<User, Long> {
}
