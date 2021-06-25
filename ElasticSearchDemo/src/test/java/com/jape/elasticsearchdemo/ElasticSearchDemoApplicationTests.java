package com.jape.elasticsearchdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ElasticSearchDemoApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     */
    @Test
    void esCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("jape");
        CreateIndexResponse response =
                restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.err.println(objectMapper.writeValueAsString(response));
    }

    /**
     * 查询索引信息
     */
    @Test
    void esGetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("jape");
        //RequestOptions options = new RequestOptions();
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        System.err.println(response);
    }

    /**
     * 插入一条文档
     */
    @Test
    void esAddDoc() throws IOException {
        IndexRequest request = new IndexRequest("jape");
        request.id("1");
        request.source("{\"name\": \"王建鹏\", \"age\": 23}");

        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.err.println(response.status());

    }

    /**
     * 批量插入
     */


    /**
     * 删除文档
     */

    /**
     * 修改文档
     */

    /**
     * 查询文档
     */

    /**
     * 条件查询文档
     */

    /**
     * 范围查询
     */

    /**
     * 分页查询
     */

    /**
     * 排序查询
     */

    /**
     * 聚合查询
     */
}
