package com.thinvent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 启动类
 * @author xf
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class}, scanBasePackages = "com.thinvent") //去掉springboot 默认的数据源配置
@MapperScan("com.thinvent.mapper") //扫描mapper的包，或者读者可以在对应的mapper上加上@Mapper的注解
public class MybatisplusSpringbootApplication {
    /**
     * 配合ConfigurationProperties使用，用于springboot1.5以后
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    public static void main(String[] args) {
        SpringApplication.run(MybatisplusSpringbootApplication.class, args);
    }

}
