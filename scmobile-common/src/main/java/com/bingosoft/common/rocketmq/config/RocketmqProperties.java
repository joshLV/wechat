package com.bingosoft.common.rocketmq.config;

import static com.bingosoft.common.rocketmq.config.RocketmqProperties.PREFIX;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/** 
 * @author  
 */  
@Data  
@ConfigurationProperties(PREFIX)  
public class RocketmqProperties {  
  
    public static final String PREFIX = "spring.extend.rocketmq";  
  
    private String namesrvAddr;  
    private String instanceName;  
    private String clientIP;  
    private ProducerConfig producer;  
    private ConsumerConfig consumer;  
      
}  
