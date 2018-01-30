package com.whb.db.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author whb
 * @date 2018年1月30日 下午7:43:11 
 * @Description: Mybatis集成配置
 */
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private static Log logger = LogFactory.getLog(MybatisConfiguration.class);


    @Resource(name = DataSourceContextHolder.MASTER_DATABASE_NAME)
    private DataSource masterDataSource;

    @Resource(name = DataSourceContextHolder.SLAVE1_DATABASE_NAME)
    private DataSource slave1DataSource;

    @Resource(name = DataSourceContextHolder.SLAVE2_DATABASE_NAME)
    private DataSource slave2DataSource;

    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        logger.info("-------------------- 重载父类 sqlSessionFactory init ---------------------");
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }

    @SuppressWarnings("unchecked")
	private AbstractRoutingDataSource roundRobinDataSouceProxy() {
        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
        Map<Object, Object> targetDataResources = new ClassLoaderRepository.SoftHashMap();
        targetDataResources.put(DataSourceContextHolder.DbType.MASTER, masterDataSource);
        targetDataResources.put(DataSourceContextHolder.DbType.SLAVE1, slave1DataSource);
        targetDataResources.put(DataSourceContextHolder.DbType.SLAVE2, slave2DataSource);
        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataResources);
        return proxy;
    }
}
