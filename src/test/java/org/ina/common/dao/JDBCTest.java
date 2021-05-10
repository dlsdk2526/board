package org.ina.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTest {
	//JDBC테스트를 위해서 spring-test, JDBC, MySQL라이브러리 필요
	
	@Test
	public void testConnection() throws Exception{
		
		//드라이버로드(드라이버네임)
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		log.info("------연결확인--------");
		
		String url = "jdbc:mysql://localhost:3306/ina?serverTimezone=UTC";
		String username = "ina";
		String password = "ina";
		
		Connection con = DriverManager.getConnection(url,username,password);
		
		log.info(con);
		con.close();
		
	}

}
