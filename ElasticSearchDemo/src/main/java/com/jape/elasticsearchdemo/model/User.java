package com.jape.elasticsearchdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * spring-date系统初始化会自动创建索引“index_test”
 */
@Data
@Document(indexName = "index_test")
public class User {

    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Date)
    private String birthday;
    @Field(type = FieldType.Short)
    private String sex;

}
