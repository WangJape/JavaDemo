package com.jape.redisdemo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {

    /**
     * 定义RedisTemplate的bean交给spring管理，这里为了能将对象直接存取到redis中，进行了一些序列化的操作
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(this.valueSerializer());
        redisTemplate.setHashValueSerializer(this.valueSerializer());

        //初始化参数和初始化工作
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

    /**
     * value采用序列化策略
     */
    private RedisSerializer<Object> valueSerializer() {

        //序列化所有类包括jdk提供的
        ObjectMapper om = new ObjectMapper();
        //设置序列化的域(属性,方法etc)以及修饰范围,Any包括private,public 默认是public的 (ALL所有方位,ANY所有修饰符)
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //指定输入的类型 (enableDefaultTyping 原来的方法存在漏洞,2.0后改用如下配置)
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        //如果java.time包下Json报错,添加如下两行代码
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(om);
        return serializer;
    }
}
