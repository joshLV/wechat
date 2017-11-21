package com.bingosoft.common.datasource.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.bingosoft.persist.mycat.dao", sqlSessionTemplateRef  = "mycatSqlSessionTemplate")
public class MyBatisMycatConfig {
	@Bean(name = "mycatDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.mycat")
	public DataSource mycatDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mycatSqlSessionFactory")
	public SqlSessionFactory mycatSqlSessionFactory(@Qualifier("mycatDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
//		bean.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/mycat/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "mycatTransactionManager")
	public DataSourceTransactionManager mycatTransactionManager(@Qualifier("mycatDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "mycatSqlSessionTemplate")
	public SqlSessionTemplate mycatSqlSessionTemplate(
			@Qualifier("mycatSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
