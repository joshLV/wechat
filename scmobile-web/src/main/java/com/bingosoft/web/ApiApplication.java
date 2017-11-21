package com.bingosoft.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@ComponentScan(basePackageClasses=)

@ComponentScan(basePackages={"com.bingosoft","com.bingosoft.common.rocketmq","com.bingosoft.persist.mongodb.dao"})
//@ComponentScan("com.bingosoft.web.controller")//包名
@MapperScan({"com.bingosoft.persist.mysql.dao","com.bingosoft.persist.mycat.dao"})
@EnableMongoRepositories("com.bingosoft.persist.mongodb.dao")
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
