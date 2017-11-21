package com.bingosoft.subscribe;

import org.apache.rocketmq.client.exception.MQClientException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.bingosoft.subscribe.rocketmq.Customer;


@ComponentScan(basePackages={"com.bingosoft","com.bingosoft.common.rocketmq"})
//@ComponentScan("com.bingosoft.web.controller")//包名
@MapperScan("com.bingosoft.persist.mysql.dao")
@SpringBootApplication
public class ApiApplication {

	 @Autowired  
	 //@Qualifier("customerService")  
	 Customer customer;
	 
	 public void run(String... strings) throws Exception {  
//	        System.out.println(this.customer.);  
//	        if(strings.length > 0 && strings[0].equals("exitcode")){  
//	            throw new ExitException();  
//	        }  
	    }  
	
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		
	}
}
