package com.whb.transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
public class ProfiledemoApplication implements TransactionManagementConfigurer {

	@Resource(name="txManager2")
    private PlatformTransactionManager txManager2;

    // 创建事务管理器1
    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        //return new DataSourceTransactionManager(dataSource);
    	return null;
    }

    // 创建事务管理器2
    @Bean(name = "txManager2")
    /*public PlatformTransactionManager txManager2(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    	return null;
    }*/
    public PlatformTransactionManager txManager2(DataSource dataSource) {
        //return new DataSourceTransactionManager(dataSource);
    	return null;
    }
    
    
 // 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager2;
	}

}
