package me.abel.fluentMybatis.config;

import cn.org.atool.fluent.mybatis.spring.MapperFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ComponentScan(basePackages = "me.abel.fluentMybatis")
@MapperScan("me.abel.fluentMybatis.mapper")
@Configuration
public class MybatisConfig {
    /**
     * 设置dataSource属性
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    /**
     * 定义mybatis的SqlSessionFactoryBean
     *
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean;
    }

   @Bean
   public MapperFactory mapperFactory() {
      return new MapperFactory();
   }
}