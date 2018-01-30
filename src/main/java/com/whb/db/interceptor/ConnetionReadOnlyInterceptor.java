package com.whb.db.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.whb.db.datasource.DataSourceContextHolder;

/**
 * @author whb
 * @date 2018年1月30日 下午8:23:51 
 * @Description: 只读连接拦截器
 */
@Aspect
@Component
@Order(0)
public class ConnetionReadOnlyInterceptor {

    static final Logger logger = LoggerFactory.getLogger(ConnetionReadOnlyInterceptor.class);

    @Pointcut("execution(* com.tuodao.bp..*Mapper.count*(..))")
    private void countOpt() {
    }

    @Pointcut("execution(* com.tuodao.bp..*Mapper.select*(..))")
    private void selectOpt() {
    }
    @Pointcut("execution(* com.tuodao.bp..*Mapper.get*(..))")
    private void getOpt() {
    }

    /**
     * 只读操作，读数据时随机分配数据源
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("countOpt() || selectOpt() || getOpt()")
    public Object readOnlyProceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            logger.info("set database connection to read only");
            DataSourceContextHolder.DbType dbType = DataSourceContextHolder.getSlaveRandom();
            logger.info(dbType.name());
            DataSourceContextHolder.setDbType(dbType);
            return proceedingJoinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDbType();
            logger.info("restore database connection");
        }
    }
}
