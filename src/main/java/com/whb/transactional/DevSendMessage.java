package com.whb.transactional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author whb
 * @date 2018年1月30日 下午5:17:34 
 * @Description: 如果Spring容器中存在多个 PlatformTransactionManager 实例，
 * 并且没有实现接口 TransactionManagementConfigurer 指定默认值，
 * 在我们在方法上使用注解 @Transactional 的时候，就必须要用value指定，
 * 如果不指定，则会抛出异常。
 */
@Component
public class DevSendMessage {

    // 使用value具体指定使用哪个事务管理器
    @Transactional(value="txManager1")
    public void send() {
        System.out.println(">>>>>>>>Dev Send()<<<<<<<<");
        send2();
    }

    // 在存在多个事务管理器的情况下，如果使用value具体指定
    // 则默认使用方法 annotationDrivenTransactionManager() 返回的事务管理器
    @Transactional
    public void send2() {
        System.out.println(">>>>>>>>Dev Send2()<<<<<<<<");
    }

}
