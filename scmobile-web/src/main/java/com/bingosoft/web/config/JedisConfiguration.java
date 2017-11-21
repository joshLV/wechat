package com.bingosoft.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
@ComponentScan({"com.bingosoft.web.config"}) // 解决 Configuration 注解中使用 Autowired 注解 IDE 报错
public class JedisConfiguration {
    @Autowired
    JedisConfig jedisconfig;

    @Bean
    public ShardedJedisPool convertJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisconfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(jedisconfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisconfig.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(jedisconfig.getTestOnBorrow());
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        jedisShardInfoList.add(new JedisShardInfo(jedisconfig.getUrl()));
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
    }
}
