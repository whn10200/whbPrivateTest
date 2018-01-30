package com.whb.db.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author whb
 * @date 2018年1月30日 下午2:54:51 
 * @Description: 多个数据源配置
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.whb.db.interceptor")
public class DataSourceConfiguration {

    @Value("${hikari.type}")
    private Class<? extends DataSource> dataSourceType;

    /**
     * Master
     *
     * @return
     */
    @Bean(name = DataSourceContextHolder.MASTER_DATABASE_NAME)
    @Primary
    @ConfigurationProperties(prefix = "hikari.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * Slave1
     *
     * @return
     */
    @Bean(name = DataSourceContextHolder.SLAVE1_DATABASE_NAME)
    @ConfigurationProperties(prefix = "hikari.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    /**
     * slave2
     *
     * @return
     */
    @Bean(name = DataSourceContextHolder.SLAVE2_DATABASE_NAME)
    @ConfigurationProperties(prefix = "hikari.slave2")
    public DataSource slave2DataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

}
