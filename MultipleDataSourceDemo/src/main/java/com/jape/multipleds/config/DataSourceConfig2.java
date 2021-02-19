package com.jape.multipleds.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;


/**
 * @Description: 多数据源配置
 * @Auther: 王建朋
 * @Date: 2020/8/19 13:57
 */
@Configuration
@MapperScan(basePackages = "com.jape.multipleds.dao.ds2", sqlSessionTemplateRef = "ds2SqlSessionTemplate")
public class DataSourceConfig2 {

    //数据源 ds2数据源
    @Bean(name = "ds2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.bedb")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "ds2SqlSessionFactory")
    public SqlSessionFactory ds2SqlSessionFactory(@Qualifier("ds2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:dao/ds2/*.xml"));
        return bean.getObject();
    }


    @Bean("ds2SqlSessionTemplate")
    public SqlSessionTemplate test1sqlsessiontemplate(@Qualifier("ds2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
