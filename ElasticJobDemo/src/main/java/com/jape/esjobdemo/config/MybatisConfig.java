package com.jape.esjobdemo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 *
 * @author Jape
 * @since 2020/11/13 23:11
 */
@Configuration
@MapperScan("com.jape.esjobdemo.mapper")
public class MybatisConfig {

}
