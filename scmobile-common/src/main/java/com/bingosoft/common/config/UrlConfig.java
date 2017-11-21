package com.bingosoft.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import lombok.Data;

@Data
@Component
public class UrlConfig {
   @Value("${http.server.deploy}")
   private String deploy;
   
   @Value("${http.server.local}")
   private String local;
   
   @Value("${http.server.processId}")
   private int processId;
   
   @Value("${wechat.appid}")
   private String appId="";
   
   @Value("${wechat.accountId}")
   private String accountId="";
   
   @Value("${wechat.cmcc}")
   private String cmccUrl="";
}
