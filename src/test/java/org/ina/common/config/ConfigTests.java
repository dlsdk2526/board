package org.ina.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class ConfigTests {
	
	@Autowired
	private RootConfig rootConfig;
	
	@Autowired
	private HikariDataSource hikariDataSource;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void config() {
		log.info("root config load.........");
		log.info(rootConfig);
	}
	
	@Test 
	public void hikari() {
		log.info("hikari config load...........");
		log.info(hikariDataSource);
	}
	
	@Test
	public void sesssion(){
		log.info("sqlsessionFactory load.............");
		log.info(sqlSessionFactory);
	}
	
}






