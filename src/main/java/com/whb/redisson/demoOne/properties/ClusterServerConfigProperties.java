package com.whb.redisson.demoOne.properties;

import org.redisson.config.ClusterServersConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author whb
 * @date 2018年2月2日 下午3:14:54 
 * @Description: redis的集群配置（还有其他主从配置BaseMasterSlaveServersConfig等）
 */
@Configuration
@ConfigurationProperties(prefix = "dislock.redisson.clusterServersConfig")
public class ClusterServerConfigProperties extends ClusterServersConfig {
}
