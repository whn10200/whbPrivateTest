package com.whb.redisson.demoOne;

import java.net.URI;
import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.whb.redisson.demoOne.properties.ClusterServerConfigProperties;

/**
 * @author whb
 * @date 2018年2月2日 下午3:16:25 
 * @Description: 分布式锁，解决资源竞争问题
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(ClusterServerConfigProperties.class)
public class EnableRedissonAutoConfiguration {

    /** 集群的配置 */
    @Autowired
    private ClusterServerConfigProperties clusterServerConfigProperties;


    /**
     * 集群模式自动装配
     * @return
     */
    @Bean
    @Primary
    //@ConditionalOnExpression("'${dislock.redisson.clusterServersConfig.idleConnectionTimeout}'=='10000'")
    @ConditionalOnProperty(prefix = "dislock.redisson",name = "startup",matchIfMissing = false)
    public RedissonClient redissonCluster() {
        Config config = new Config();
        ClusterServersConfig serverConfig = config.useClusterServers();
        BeanUtils.copyProperties(clusterServerConfigProperties,serverConfig);
        List<URI> nodeAddresses = clusterServerConfigProperties.getNodeAddresses();

        int size = nodeAddresses.size();
        String[] address = new String[size];
        for(int i=0;i<size;i++){
            address[i] = nodeAddresses.get(i).toString();
        }

        serverConfig.addNodeAddress(address);

        return Redisson.create(config);
    }
}
