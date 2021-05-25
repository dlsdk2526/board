package org.ina.security;

import org.ina.board.config.BoardConfig;
import org.ina.common.config.RootConfig;
import org.ina.common.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,
								 BoardConfig.class,
								 SecurityConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class PasswordEncoderTests {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void testEncode() {
		
		String str = "member";
		
		//$2a$10$z4TX7Fqrhk2.7CYjh0.MYO64pBanu07u2yJWGsZsujCoSJhtRZSpu
		String enStr = passwordEncoder.encode(str);
		
		log.info(enStr);
	}
}





