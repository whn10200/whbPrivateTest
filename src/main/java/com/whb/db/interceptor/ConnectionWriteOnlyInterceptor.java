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
 * @date 2018年1月30日 下午8:23:40 
 * @Description: 只写操作拦截器
 */
@Aspect
@Component
@Order(0)
public class ConnectionWriteOnlyInterceptor {

    static final Logger logger = LoggerFactory.getLogger(ConnectionWriteOnlyInterceptor.class);

    @Pointcut("execution(* com.tuodao.bp..*Mapper.update*(..))")
    private void updateOpt() {
    }

    @Pointcut("execution(* com.tuodao.bp..*Mapper.insert*(..))")
    private void insertOpt() {
    }

    @Pointcut("execution(* com.tuodao.bp..*Mapper.delete*(..))")
    private void deleteOpt() {
    }

    /**
     * 只写操作
     *
     *
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("updateOpt() or insertOpt() or deleteOpt()")
    public Object writeOnlyProceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            logger.info("set database connection to write only");
            DataSourceContextHolder.setDbType(DataSourceContextHolder.DbType.MASTER);
            return proceedingJoinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDbType();
            logger.info("restore database connection");
        }
    }
}
