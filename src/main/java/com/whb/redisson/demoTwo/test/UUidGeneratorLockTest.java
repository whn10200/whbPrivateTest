package com.whb.redisson.demoTwo.test;

import com.whb.redisson.demoTwo.util.ThreadUtil;
import com.whb.redisson.demoTwo.uuid.UUidGenerator;

/**
 * @author whb
 * @date 2018年2月2日 下午4:28:55 
 * @Description: 基于低版本的测试类
 */
public class UUidGeneratorLockTest {

	public static void main(String[] args) {
		UUidGenerator uuid = new UUidGenerator();
		String perKey = "1788dufy";
		int num = 100;

		for (int i = 1; i <= 10; i++) {
			ThreadUtil tu = new ThreadUtil(uuid, perKey, num);
			tu.start();
		}

	}
}

/*<!-- https://mvnrepository.com/artifact/org.redisson/redisson -->
    <dependency>
        <groupId>org.redisson</groupId>
        <artifactId>redisson</artifactId>
        <version>2.8.1</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
    <!--redisson 需要用到netty的jar 包-->
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-all</artifactId>
        <version>4.1.7.Final</version>
    </dependency>

    <!--2.8.1 需要jackson 2.5+ 版本-->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.8.1</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.8.1</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.8.1</version>
        <exclusions>
            <exclusion>
                <artifactId>jackson-annotations</artifactId>
                <groupId>com.fasterxml.jackson.core</groupId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- redis相关jar包 -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.4.2</version>
        <type>jar</type>
        <scope>compile</scope>
    </dependency>*/
